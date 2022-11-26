package model;

import java.util.ArrayList;

// the Checklist classes produces a new checklist based on the user's mode of travel, and allows the user to add more
// items
public class Checklist {
    private ArrayList<String> list;

    public Checklist() {
        list = new ArrayList<>();
    }

    public ArrayList<String> getList() {
        return list;
    }

    // MODIFIES: this
    // EFFECTS: produces a list of items that are required on the checklist according to the mode of travel
    public void addItemsForTransport(Trip.TravelMode travelType) {
        switch (travelType) {
            case FLIGHT:
                list.add("Boarding Pass");
                list.add("Passport");
                list.add("Visa");
                list.add("Check-in Baggage");
                break;
            case TRAIN:
                list.add("Train Ticket");
                list.add("ID Card");
                list.add("Luggage");
                break;
            case CAR:
                list.add("Set up navigation");
                list.add("Toll Tickets");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds item to the checklist
    public void addCustomItems(String item) {
        list.add(item);
    }
}
