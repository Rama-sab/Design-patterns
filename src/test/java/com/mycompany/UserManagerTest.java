package com.mycompany.app.model.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @Test
    void testManagerCreation() {
        UserManager manager = new UserManager();
        assertNotNull(manager);
    }
}
