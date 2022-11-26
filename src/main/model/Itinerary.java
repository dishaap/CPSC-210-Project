package model;

import java.util.ArrayList;

// the itinerary class produces a list of the type ItineraryItem, with a list of all places with added by the user
public class Itinerary {
    private ItineraryItem itineraryItem;
    ArrayList<ItineraryItem> itineraryList = new ArrayList<>();

    // EFFECTS: generates a new itinerary
    public ArrayList<ItineraryItem> getNewItinerary() {
        return itineraryList;
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the list, which is an object of the class ItineraryItem
    public void addToItinerary(String place, int hours, String notes) {
        itineraryItem = new ItineraryItem(place, hours, notes);
        itineraryList.add(itineraryItem);
        EventLog.getInstance().logEvent(new Event("Item added to itinerary."));

    }

    public ItineraryItem getItineraryItem(int index) {
        return itineraryList.get(index);
    }
}
