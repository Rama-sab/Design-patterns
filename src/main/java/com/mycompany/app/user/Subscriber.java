package com.mycompany.app.user;

import com.mycompany.app.system_for_events.Event;

//observer pattern 
public interface Subscriber {
    String getName(); 
    void update(Event event); 
}
