package com.mycompany;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mycompany.app.model.user.*;

class UserManagerTest {

    @Test
    void testManagerCreation() {
        UserManager manager = new UserManager();
        assertNotNull(manager);
    }
}
