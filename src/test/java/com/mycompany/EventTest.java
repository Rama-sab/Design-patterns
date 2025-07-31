package com.mycompany;

import com.mycompany.app.model.system_for_events.Event;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEventCreate() {
        Event event = Event.create("myEvent", 2);
        assertNotNull(event);
        assertEquals("myEvent", event.getname());
        assertEquals(2, event.getCategoryId());
    }
}
