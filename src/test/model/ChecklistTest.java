package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ChecklistTest {
    private Checklist testChecklist1;
    private Checklist testChecklist2;
    private Checklist testChecklist3;
    private Checklist testChecklist4;

    @BeforeEach
    void runBefore() {
        testChecklist1 = new Checklist();
        testChecklist2 = new Checklist();
        testChecklist3 = new Checklist();
        testChecklist4 = new Checklist();
    }

    @Test
    void testConstructor() {
        ArrayList<String> l1 = testChecklist1.getList();
        assertEquals(0, l1.size());
        ArrayList<String> l2 = testChecklist1.getList();
        assertEquals(0, l2.size());
        ArrayList<String> l3 = testChecklist1.getList();
        assertEquals(0, l3.size());
        ArrayList<String> l4 = testChecklist1.getList();
        assertEquals(0, l4.size());
    }

    @Test
    void testGetList() {
        testChecklist1.addCustomItems("a");
        ArrayList<String> test = testChecklist1.getList();
        assertEquals("a", test.get(0));
    }

    @Test
    void testAddItemsForTransport() {
        testChecklist1.addItemsForTransport(Trip.TravelMode.FLIGHT);
        ArrayList<String> l1 = testChecklist1.getList();
        assertEquals("Boarding Pass", l1.get(0));
        assertEquals("Passport", l1.get(1));
        assertEquals("Visa", l1.get(2));
        assertEquals("Check-in Baggage", l1.get(3));
        testChecklist2.addItemsForTransport(Trip.TravelMode.TRAIN);
        ArrayList<String> l2 = testChecklist2.getList();
        assertEquals("Train Ticket", l2.get(0));
        assertEquals("ID Card", l2.get(1));
        assertEquals("Luggage", l2.get(2));
        testChecklist3.addItemsForTransport(Trip.TravelMode.CAR);
        ArrayList<String> l3 = testChecklist3.getList();
        assertEquals("Set up navigation", l3.get(0));
        assertEquals("Toll Tickets", l3.get(1));
        testChecklist4.addItemsForTransport(Trip.TravelMode.NULL);
        ArrayList<String> l4 = testChecklist4.getList();
        assertEquals(0, l4.size());
    }

    @Test
    void testAddCustomItems() {
        testChecklist1.addCustomItems("A");
        ArrayList<String> l1 = testChecklist1.getList();
        assertEquals("A",l1.get(0));
    }
}
