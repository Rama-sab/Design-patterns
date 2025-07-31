package com.mycompany.app.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.app.model.system_for_events.Event;
import com.mycompany.app.model.system_for_events.EventDTO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AddEventHandler implements HttpHandler {

    private static final String FILE_PATH = "src/main/java/com/mycompany/app/model/system_for_events/event.json";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        try {
            // Read and parse event from request
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("📥 Raw request body: " + body);
            
            Gson gson = new Gson();
            EventDTO dto = gson.fromJson(body, EventDTO.class);
            
            if (dto == null) {
                System.err.println("❌ Failed to parse JSON - dto is null");
                sendErrorResponse(exchange, "Invalid JSON format");
                return;
            }
            
            if (dto.name == null || dto.name.trim().isEmpty()) {
                System.err.println("❌ Event name is null or empty");
                sendErrorResponse(exchange, "Event name is required");
                return;
            }

            // Create event and add to memory
            Event newEvent = Event.create(dto.name, dto.categoryId);
            Main.eventList.add(newEvent);

            // Save to file
            saveEventsToFile(newEvent);

            System.out.println("📥 Received from frontend:");
            System.out.println("  name: " + dto.name);
            System.out.println("  categoryId: " + dto.categoryId);
            System.out.println("✅ Saving event to JSON: " + newEvent.toString());

            
            String response = "{\"status\":\"success\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error handling request: " + e.getMessage());
            e.printStackTrace();
            sendErrorResponse(exchange, "Internal server error: " + e.getMessage());
        }
    }

    private void sendErrorResponse(HttpExchange exchange, String message) throws IOException {
        String errorResponse = "{\"status\":\"error\",\"message\":\"" + message + "\"}";
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(400, errorResponse.getBytes().length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(errorResponse.getBytes());
        }
    }

    private void saveEventsToFile(Event newEvent) throws IOException {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        List<Event> events;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (file.exists()) {
            // Load existing events from file
            try (java.io.FileReader reader = new java.io.FileReader(file)) {
                java.lang.reflect.Type eventListType = new com.google.gson.reflect.TypeToken<List<Event>>(){}.getType();
                events = gson.fromJson(reader, eventListType);
                if (events == null) events = new java.util.ArrayList<>();
            } catch (Exception e) {
                System.err.println("❌ Error reading existing events file: " + e.getMessage());
                events = new java.util.ArrayList<>();
            }
        } else {
            events = new java.util.ArrayList<>();
        }

        // Add new event to the list
        events.add(newEvent);

        // Write back the updated list
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(events, writer);
            System.out.println("✅ Events saved to file: " + FILE_PATH);
        } catch (Exception e) {
            System.err.println("❌ Error writing events to file: " + e.getMessage());
            throw e;
        }
    }
}