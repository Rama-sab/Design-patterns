package com.mycompany.app.controller;

import com.mycompany.app.model.system_for_events.Event;
import com.mycompany.app.model.system_for_events.EventManager;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {
    public static List<Event> eventList = Collections.synchronizedList(new ArrayList<Event>());

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Event Scheduler...");
        EventManager everyTenEvent = EventManager.getInstance();
        everyTenEvent.startGeneratingEvents();

        startHttpServer();
    }

    private static void startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/notifications", new NotificationHandler());
        server.setExecutor(Executors.newFixedThreadPool(2));
        server.start();
        System.out.println("HTTP server started at http://localhost:8080");
    }

    static class NotificationHandler implements HttpHandler {
   @Override
public void handle(HttpExchange exchange) throws IOException {
    String json = buildJson();

    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); 
    exchange.getResponseHeaders().set("Content-Type", "application/json");
    exchange.sendResponseHeaders(200, json.getBytes().length);

    try (OutputStream os = exchange.getResponseBody()) {
        os.write(json.getBytes());
    }
}

        private String buildJson() {
            List<String> jsonItems = Main.eventList.stream()
                .map((Event e) -> String.format(
                    "{\"message\":\"%s\",\"date\":\"%s\",\"time\":\"%s\"}",
                    escape(e.getname()),
                    escape(e.getData()),
                    escape(e.getTime())
                ))
                .collect(Collectors.toList());

            return "[" + String.join(",", jsonItems) + "]";
        }

        private String escape(String s) {
            return s.replace("\"", "\\\"");
        }
    }
}
