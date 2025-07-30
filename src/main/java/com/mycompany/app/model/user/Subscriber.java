package com.mycompany.app.model.user;

import com.mycompany.app.model.system_for_events.Event;

//observer pattern 
public interface Subscriber {
   
    void update(Event event); 
}
