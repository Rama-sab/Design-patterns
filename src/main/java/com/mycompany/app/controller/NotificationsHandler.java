package com.mycompany.app.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NotificationsHandler implements HttpHandler {
    private static final String FILE_PATH = "/src/main/java/com/mycompany/app/model/system_for_events/addevent.json";
    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        List<Object> events;
        try (Reader reader = new FileReader(FILE_PATH)) {
            events = gson.fromJson(reader, new TypeToken<List<Object>>() {}.getType());
        }
        String response = gson.toJson(events);
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
