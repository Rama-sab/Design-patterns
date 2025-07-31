package com.mycompany.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.app.model.system_for_events.Event;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AddEventHandler implements HttpHandler {
 
    private static final Gson gson = new GsonBuilder().create();

    static class EventData {
        String name;
        int category;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Handle CORS preflight requests for browser compatibility
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            // 1. Parse incoming request body (Java 8 compatible)
            InputStream is = exchange.getRequestBody();
            java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
            String body = s.hasNext() ? s.next() : "";
            s.close();

            EventData requestData = gson.fromJson(body, EventData.class);

            Event newEvent = Event.create(requestData.name, requestData.category);
            Main.eventList.add(newEvent);
            System.out.println("📥 Event received via API: " + newEvent.getname());

           
            Main.saveEventsToJson();

           
            String response = "✅ Event saved!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // Handle incorrect HTTP methods (e.g., GET, PUT)
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
        }
    }
}
