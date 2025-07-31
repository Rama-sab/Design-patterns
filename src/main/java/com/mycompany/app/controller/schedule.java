package com.mycompany.app.controller;

import java.io.*;
import java.util.*;
import java.lang.reflect.Type;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import com.mycompany.app.model.system_for_events.Event;

public class schedule implements Runnable {
    private final String message;

    public schedule(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        Event event = Event.create(message);
        Main.eventList.add(event);

      
        String folderPath = "src/main/java/com/mycompany/app/model/system_for_events";
        String filePath = folderPath + "/event.json";

        try {
           
            java.nio.file.Path folder = java.nio.file.Paths.get(folderPath);
            if (!java.nio.file.Files.exists(folder)) {
                java.nio.file.Files.createDirectories(folder);
                System.out.println("Created folder: " + folderPath);
            }

            
            List<Event> events = new ArrayList<>();
            File jsonFile = new File(filePath);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if (jsonFile.exists() && jsonFile.length() > 0) {
                try (FileReader reader = new FileReader(jsonFile)) {
                    Type eventListType = new TypeToken<List<Event>>(){}.getType();
                    events = gson.fromJson(reader, eventListType);
                    if (events == null) events = new ArrayList<>();
                } catch (Exception e) {
                    System.err.println("Error reading existing events: " + e);
                    events = new ArrayList<>();
                }
            }

            // Add new event
            events.add(event);

            // Write all events back to JSON file
            try (FileWriter writer = new FileWriter(jsonFile)) {
                gson.toJson(events, writer);
                System.out.println("Saved event to " + jsonFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Error saving JSON: " + e);
            e.printStackTrace();
        }
    }
}
