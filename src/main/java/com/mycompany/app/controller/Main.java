package com.mycompany.app.controller;

import com.mycompany.app.model.system_for_events.EventManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Event Scheduler...");
        EventManager everytenevent = EventManager.getInstance();
        everytenevent.startGeneratingEvents();
    }
}
