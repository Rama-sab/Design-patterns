

package com.mycompany.app.user;

import java.util.concurrent.atomic.AtomicInteger;

import com.mycompany.app.system_for_events.Event;




// A Concrete Observer implementation
public class User implements Subscriber {
    static AtomicInteger nextId = new AtomicInteger();
    private int id ;
    private final String name;
   // private string[] fav
    public User(String name) {
        this.name = name;
        this.id = nextId.incrementAndGet();
    }

   
    public String getName() {
        return this.name;
    }

    @Override
    public void update(Event event) {
           System.out.println("------------------------------------");
        System.out.println("--- Notification form event: " + event.getname() + " ---");
        System.out.println("------- for user: " + name + " ---");
        System.out.println("-----Date: " + event.getData()+ " ---");
        System.out.println("at : " + event.getTime()+ " ---");
        System.out.println("------------------------------------");
    }
}