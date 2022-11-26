package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TripTest {
    private Trip testTrip;

    @BeforeEach
    void runBefore() {
        testTrip = new Trip("A");
    }

    @Test
    void testConstructor() {
        assertEquals("A", testTrip.getName());
        assertTrue(testTrip.getId()>0);
    }

    @Test
    void testSetLocation() {
        testTrip.setLocation("Vancouver");
        assertEquals("Vancouver",testTrip.getLocation());
    }

    @Test
    void testSetDuration() {
        testTrip.setDuration(2);
        assertEquals(2, testTrip.getDuration());
    }

    @Test
    void testSetModeOfTravel() {
        testTrip.setModeOfTravel(Trip.TravelMode.FLIGHT);
        assertEquals(Trip.TravelMode.FLIGHT, testTrip.getModeOfTravel());
    }

    @Test
    void testSetAccommodation() {
        testTrip.setAccommodation("UBC");
        assertEquals("UBC", testTrip.getAccommodation());
    }

    @Test
    void testGetName() {
        assertEquals("A", testTrip.getName());
    }

    @Test
    void testGetId() {
        assertEquals(5, testTrip.getId());
    }

    @Test
    void testGetLocation() {
        assertEquals("Vancouver", testTrip.getLocation());
    }

    @Test
    void testGetDuration() {
        assertEquals(2, testTrip.getDuration());
    }

    @Test
    void testGetModeOfTravel() {
        assertEquals(Trip.TravelMode.NULL, testTrip.getModeOfTravel());
    }

    @Test
    void testGetAccomodation() {
        assertEquals("UBC", testTrip.getAccommodation());
    }

    @Test
    void testUpdateDuration() {
        testTrip.setDuration(2);
        assertEquals(3, testTrip.updateDuration(1, true));
        assertEquals(2, testTrip.updateDuration(1, false));
    }

    @Test
    void testGetNewChecklist() {
        testTrip.setModeOfTravel(Trip.TravelMode.CAR);
        ArrayList<String> testChecklist = testTrip.getNewChecklist();
        assertEquals("Set up navigation", testChecklist.get(0));
        assertEquals("Toll Tickets", testChecklist.get(1));
        Trip testTrip1 = new Trip("A");
        testTrip1.setModeOfTravel(Trip.TravelMode.FLIGHT);
        testChecklist = testTrip1.getNewChecklist();
        assertEquals("Boarding Pass", testChecklist.get(0));
        assertEquals("Passport", testChecklist.get(1));
        assertEquals("Visa", testChecklist.get(2));
        assertEquals("Check-in Baggage", testChecklist.get(3));
        Trip testTrip2 = new Trip("B");
        testTrip2.setModeOfTravel(Trip.TravelMode.TRAIN);
        testChecklist = testTrip2.getNewChecklist();
        assertEquals("Train Ticket", testChecklist.get(0));
        assertEquals("ID Card", testChecklist.get(1));
        assertEquals("Luggage", testChecklist.get(2));
        Trip testTrip3 = new Trip("C");
        testTrip3.setModeOfTravel(Trip.TravelMode.NULL);
        testChecklist = testTrip3.getNewChecklist();
        assertEquals(0, testChecklist.size());
    }

    @Test
    void testAddToChecklist() {
        testTrip.addToChecklist("A");
        ArrayList<String> testChecklist = testTrip.getChecklist();
        assertEquals("A", testChecklist.get(0));
    }

    @Test
    void testSummary() {
        String summary = "ID: " + testTrip.getId() + "; Name: " + "A" + "; Travelling to: " + "Vancouver" + "; For: "
                + 2 + " days; " + "Travelling by: " + "Flight" + "; Staying at: " + "UBC.";
        assertEquals(summary, testTrip.summary());
    }
}
