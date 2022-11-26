package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ItineraryTest {
    private Itinerary testItinerary;
    private ItineraryItem testItineraryItem;

    @BeforeEach
    void runBefore() {
        testItinerary = new Itinerary();
    }

    @Test
    void testGetNewItinerary() {
        ArrayList<ItineraryItem> test = new ArrayList<>();
        assertEquals(test, testItinerary.getNewItinerary());
    }

    @Test
    void testAddToItinerary() {
        testItinerary.addToItinerary("A", 2, "n/a");
        testItineraryItem = testItinerary.getItineraryItem(0);
        ArrayList<ItineraryItem> arrayList = testItinerary.getNewItinerary();
        assertEquals(testItineraryItem, arrayList.get(0));
    }

    @Test
    void testGetItineraryItem() {
        testItinerary.addToItinerary("A", 2, "n/a");
        ItineraryItem testItineraryItem1 = testItinerary.getItineraryItem(0);
        assertEquals("A", testItineraryItem1.getLocation());
        assertEquals(2, testItineraryItem1.getHours());
        assertEquals("n/a", testItineraryItem1.getNotes());
    }
}
