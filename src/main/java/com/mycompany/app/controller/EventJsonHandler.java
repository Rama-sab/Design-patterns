package com.mycompany.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EventJsonHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Enable CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        // JSON file path
        Path path = Paths.get("src\\main\\java\\com\\mycompany\\app\\model\\system_for_events\\addevent.json");
        byte[] jsonData = Files.readAllBytes(path);

        exchange.sendResponseHeaders(200, jsonData.length);
        exchange.getResponseBody().write(jsonData);
        exchange.getResponseBody().close();
    }
}
