package com.mycompany.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.mycompany.app.model.system_for_events.Event;
import com.mycompany.app.model.system_for_events.EventManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {

 
    public static List<Event> eventList = Collections.synchronizedList(new ArrayList<>());
    
    
    private static final String FILE_PATH = "src/main/java/com/mycompany/app/model/system_for_events/addevent.json";

    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static void main(String[] args) throws IOException {
        System.out.println(" Starting Event Scheduler...");
        
        
        loadEventsFromJson();
        
        
        EventManager eventManager = EventManager.getInstance();
        eventManager.startGeneratingEvents();

   
        startHttpServer();
    } 

    private static void startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        
        server.createContext("/api/notifications", new NotificationHandler()); 
        server.createContext("/api/add-event", new AddEventHandler());      
            server.createContext("/api/login", new LoginHandler());

        System.out.println(" Server endpoints registered:");
        System.out.println("   - [GET]  /api/notifications");
        System.out.println("   - [POST] /api/add-event");

        server.setExecutor(Executors.newFixedThreadPool(4)); 
        server.start();

        System.out.println(" HTTP Server started at http://localhost:8080 with " + eventList.size() + " events loaded.");
    
    
    }

  
    private static void loadEventsFromJson() {
        File jsonFile = new File(FILE_PATH);
        if (jsonFile.exists() && jsonFile.length() > 0) {
            try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
              
                Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
                List<Event> loadedEvents = gson.fromJson(reader, listType);
                if (loadedEvents != null) {
                    
                    eventList.addAll(loadedEvents);
                }
            } catch (IOException e) {
                System.out.println(" Could not read events from file: " + e.getMessage());
            } catch (JsonParseException e) {
                System.out.println(" Could not parse JSON file, it might be corrupted: " + e.getMessage());
            }
        } else {
            System.out.println(" No existing event file found. Starting with an empty list.");
        }
    }

   
    static class NotificationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            
            String jsonResponse = gson.toJson(Main.eventList);

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }


    static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(formatter));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), formatter);
        }
    }
    public static void saveEventsToJson() {
        File jsonFile = new File(FILE_PATH);

       
        if (!jsonFile.getParentFile().exists()) {
            jsonFile.getParentFile().mkdirs();
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFile, false), StandardCharsets.UTF_8)) {
            gson.toJson(eventList, writer);
        } catch (IOException e) {
            System.out.println(" Could not save events to file: " + e.getMessage());
        }
    }
}
