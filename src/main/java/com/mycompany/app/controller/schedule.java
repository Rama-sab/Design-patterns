package com.mycompany.app.controller;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.app.model.system_for_events.Event;

public class schedule implements Runnable {
    private final String message;

    public schedule(String message) {
        this.message = message;
    }

    @Override
    public void run() {
    System.out.println(" run() method started");

        Event event = Event.create(message);
        Main.eventList.add(event);
    System.out.println("New event: " + message + " at " + event.getTime());
      
    String filePath = "src/main/java/com/mycompany/app/model/system_for_events/event.json";

    try {
      
        List<Event> existingEvents = new ArrayList<>();

        java.io.File file = new java.io.File(filePath);
        if (file.exists()) {
            try (java.io.FileReader reader = new java.io.FileReader(file)) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(java.time.LocalDateTime.class, new LocalDateTimeAdapter())
                        .create();
                java.lang.reflect.Type eventListType = new com.google.gson.reflect.TypeToken<List<Event>>() {}.getType();
                existingEvents = gson.fromJson(reader, eventListType);
                if (existingEvents == null) {
                    existingEvents = new ArrayList<>();
                }
                }
            }

      
        existingEvents.add(event);

        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(java.time.LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .create();
            gson.toJson(existingEvents, writer);
            System.out.println(" Appended and saved to: " + filePath);
            }

        } catch (IOException e) {
        System.err.println(" Failed to read/write JSON:");
            e.printStackTrace();
        }
    }


}

