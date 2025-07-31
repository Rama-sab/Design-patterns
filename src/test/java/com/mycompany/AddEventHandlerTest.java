package com.mycompany;

import com.mycompany.app.controller.AddEventHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddEventHandlerTest {

    @Test
    void testHandlerCreation() {
        AddEventHandler handler = new AddEventHandler();
        assertNotNull(handler);
    }
}
