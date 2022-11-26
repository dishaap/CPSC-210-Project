package persistence;

import model.Trip;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTrip(String name, Trip trip) {
        assertEquals(name, trip.getName());
    }
}
