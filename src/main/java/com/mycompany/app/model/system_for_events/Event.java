package com.mycompany.app.model.system_for_events;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

  

public class Event {
    private final String name;
   private final int Categories;
  private  final LocalDateTime timestamp;

    private Event(String name_e ,int filter_e) {
        this.name =name_e;
        this.timestamp=LocalDateTime.now();
        this.Categories= filter_e;
       
    }

    /*public string getFilter() {
        return filter;
    }*/
   public int getCategoryId(){
return Categories ;
   }
public  LocalDateTime gettimestamp() {
        return timestamp;}

    public  String getname() {
        return name;}
public static Event create(String name_e) {
        int defaultCategory =0;
        
        return new Event(name_e, defaultCategory);
    }
     public static Event create(String name_e, int categories) {
        return new Event(name_e, categories);
    }
  public String getData(){

             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return timestamp.format(formatter);

  }      
  public String getTime(){

     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
  
    
}
@Override
public String toString() {
    return "Event{" +
            "name='" + name + '\'' +
            ", categories=" + Categories +
            ", timestamp=" + timestamp +
            '}';
}





}

