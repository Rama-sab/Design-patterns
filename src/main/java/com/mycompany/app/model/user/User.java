

package com.mycompany.app.model.user;
import  com.mycompany.app.model.user.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import com.mycompany.app.model.system_for_events.Event;





// A Concrete Observer implementation
public class User implements Subscriber{
    
    private final String role;
    private final String name;
   private int[] fav;
    private int startHour;   
    private int endHour;
    public User(String name, String role) {
        this.name = name;
        this.role = role;
        this.fav = new int[0]; 
        this.startHour = 0;
        this.endHour = 24;
      
    }
      public String getName() { return name; }
    public String getRole() { return role; }
    public int[] getFav() { return fav; }
    public void setFav(int[] fav) { this.fav = fav; }
    public int getStartHour() { return startHour; }
    public void setStartHour(int h) { this.startHour = h; }
    public int getEndHour() { return endHour; }
    public void setEndHour(int h) { this.endHour = h; }
    

    @Override
    public void update(Event event) {
           System.out.println("------------------------------------");
        System.out.println("--- Notification form event: " + event.getname() + " ---");
        System.out.println("------- for user: " + name + " ---");
        System.out.println("-----Date: " + event.getData()+ " ---");
        System.out.println("at : " + event.getTime()+ " ---");
        System.out.println("------------------------------------");
    }
        @Override
    public String toString() {
        return "User{" +
            "name='" + name + '\'' +
            ", role='" + role + '\'' +
            ", fav=" + Arrays.toString(fav) +
            ", startHour=" + startHour +
            ", endHour=" + endHour +
            '}';
    }
}