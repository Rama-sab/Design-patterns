package com.a.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubscriberTest {

    @Test
    void testSubscriberCreation() {
        Subscriber sub = new Subscriber();
        assertNotNull(sub);
    }
}
