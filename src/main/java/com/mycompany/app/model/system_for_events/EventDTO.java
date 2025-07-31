package com.mycompany.app.model.system_for_events;

public class EventDTO {
    public String name;
    public int categoryId;
    
   
    public EventDTO() {}
    
    
    public EventDTO(String name, int categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }
    
    @Override
    public String toString() {
        return "EventDTO{" +
                "name='" + name + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}