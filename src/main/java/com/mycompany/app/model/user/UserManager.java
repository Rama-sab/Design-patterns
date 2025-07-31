package com.mycompany.app.model.user;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    
    private static final List<User> users = new ArrayList<>();
   
    private static User currentUser = null;

    static {
      
        users.add(new User("admin", "admin"));
        users.add(new User("user", "user"));
    }

  
    public static User login(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                currentUser = u;
                return u;
            }
        }
        return null;
    }

    
    public static User getCurrentUser() {
        return currentUser;
    }

    
    public static void logout() {
        currentUser = null;
    }

    
    public static List<User> getUsers() {
        return users;
    }
}
