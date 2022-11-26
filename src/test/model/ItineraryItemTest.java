package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItineraryItemTest {
    private ItineraryItem testItineraryItem;

    @BeforeEach
    void runBefore() {
        testItineraryItem = new ItineraryItem("A", 2, "n/a");
    }

    @Test
    void testConstructor() {
        assertEquals("A", testItineraryItem.getLocation());
        assertEquals(2, testItineraryItem.getHours());
        assertEquals("n/a", testItineraryItem.getNotes());
    }

    @Test
    void testGetLocation() {
        assertEquals("A", testItineraryItem.getLocation());
    }

    @Test
    void testGetHours() {
        assertEquals(2, testItineraryItem.getHours());
    }

    @Test
    void testGetNotes() {
        assertEquals("n/a", testItineraryItem.getNotes());
    }
}
