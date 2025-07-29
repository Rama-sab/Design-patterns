package com.mycompany.app.system_for_events;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

    // The Product interface in the Factory pattern
  

public class Event {
    private final String name;
  //  private final string filter;
  private  final LocalDateTime timestamp;

    private Event(String name_e
    //,String filter_e
    ) {
        this.name =name_e;
        this.timestamp=LocalDateTime.now();
       // this.filter =filter_e;
    }

    /*public string getFilter() {
        return filter;
    }*/
public  LocalDateTime gettimestamp() {
        return timestamp;}

    public  String getname() {
        return name;}

  public String getData(){

             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return timestamp.format(formatter);

  }      
  public String getTime(){

     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
  
    
}}

