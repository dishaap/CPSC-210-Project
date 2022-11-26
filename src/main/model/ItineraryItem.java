package model;

// the ItineraryItem class produces an object with details of one place on the itinerary
public class ItineraryItem {
    private String location;
    private int hours;
    private String notes;

    public ItineraryItem(String place, int hour, String note) {
        location = place;
        hours = hour;
        notes = note;
    }

    public String getLocation() {
        return location;
    }

    public int getHours() {
        return hours;
    }

    public String getNotes() {
        return notes;
    }
}
