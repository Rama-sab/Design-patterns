package com.mycompany;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



import com.mycompany.app.model.user.User;


class UserTest {

    @Test
    void testUserCreation() {
        User user = new User("rama","admin");
            assertEquals("rama", user.getName());
        assertEquals("admin", user.getRole());
    }
}
