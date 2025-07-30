package com.mycompany.app.controller;

import java.io.FileWriter;
import java.io.IOException;

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
    Event event = Event.create(message);
    Main.eventList.add(event);           
    System.out.println("New event: " + message + " at " + event.getTime());

    // Path to the model folder (relative to project root)
    String folderPath = "Design-patterns\\src\\main\\java\\com\\mycompany\\app\\model\\system_for_events";
    String filePath = folderPath + "/event.json";

    try {
        // Ensure the folder exists (safe check, even if it should exist)
        java.nio.file.Path folder = java.nio.file.Paths.get(folderPath);
        if (!java.nio.file.Files.exists(folder)) {
            java.nio.file.Files.createDirectories(folder);
        }

        // Write event to JSON
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(event, writer);
            System.out.println("Saved event to " + filePath);
        }

    } catch (IOException e) {
        System.err.println("Error saving JSON:");
        e.printStackTrace();
    }
}

}
