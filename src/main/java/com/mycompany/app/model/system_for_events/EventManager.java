package com.mycompany.app.model.system_for_events;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.mycompany.app.controller.schedule;

// Singleton


public class EventManager {
    private static EventManager instance;
    private final ScheduledExecutorService scheduler;

    private EventManager() {
        scheduler =  Executors.newScheduledThreadPool(1) ; 
    }

    public static synchronized EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void startGeneratingEvents() {
        scheduler.scheduleAtFixedRate(() -> {
            String msg = "New update";
            Runnable runnables = new schedule(msg);
            Thread thread = new Thread(runnables);
            thread.start();
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}