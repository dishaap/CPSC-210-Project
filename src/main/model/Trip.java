package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// the Trip class stores information given by the user and allows the user to modify information
// and produce a checklist and a summary of the trip
public class Trip implements Writable {

    public enum TravelMode {
        FLIGHT, TRAIN, CAR, NULL
    }

    private static int nextTripId = 1;
    private static String name; // name of user
    private static int id; // id number of trip
    private static String location; // the location the user is travelling to
    private static int duration; // duration of stay
    private static TravelMode modeOfTravel; // mode of travel utilised by user
    private static String accommodation; // place at which user is staying
    Checklist checklist = new Checklist();

    public Trip(String userName) {
        name = userName;
        id = nextTripId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public int getDuration() {
        return duration;
    }

    public TravelMode getModeOfTravel() {
        return modeOfTravel;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setLocation(String loc) {
        location = loc;
        EventLog.getInstance().logEvent(new Event("Location set."));
    }

    public void setDuration(int timeOfStay) {
        duration = timeOfStay;
        EventLog.getInstance().logEvent(new Event("Duration of stay set."));
    }

    public void setModeOfTravel(TravelMode mode) {
        modeOfTravel = mode;
        EventLog.getInstance().logEvent(new Event("Mode of Travel set."));
    }

    public void setAccommodation(String acc) {
        accommodation = acc;
        EventLog.getInstance().logEvent(new Event("Accommodation set."));
    }

    // MODIFIES: this
    // EFFECTS: updates the duration of stay with the number of days that the user specifies
    public int updateDuration(int days, boolean addDays) {
        if (addDays) {
            duration = duration + days;
        } else {
            duration = duration - days;
        }
        EventLog.getInstance().logEvent(new Event("Duration updated."));
        return duration;
    }

    // MODIFIES: checklist
    // EFFECTS: produces a basic checklist based on mode of travel
    public ArrayList<String> getNewChecklist() {
        checklist.addItemsForTransport(modeOfTravel);
        EventLog.getInstance().logEvent(new Event("New checklist generated."));
        return checklist.getList();
    }

    // MODIFIES: checklist
    // EFFECTS: adds a custom item to checklist
    public void addToChecklist(String item) {
        checklist.addCustomItems(item);
        EventLog.getInstance().logEvent(new Event("Item added to checklist."));
    }

    // EFFECTS: returns checklist
    public ArrayList<String> getChecklist() {
        return checklist.getList();
    }

    // EFFECTS: provides summary of trip
    public String summary() {
        String transport;
        switch (modeOfTravel) {
            case FLIGHT:
                transport = "Flight";
                break;
            case TRAIN:
                transport = "Train";
                break;
            case CAR:
                transport = "Car";
                break;
            default:
                transport = "Unrecognised";
        }

        EventLog.getInstance().logEvent(new Event("Summary generated."));

        return String.format("ID: %d; Name: %s; Travelling to: %s; For: %d days; Travelling by: %s; Staying at: %s.",
            id, name, location, duration, transport, accommodation);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("location", location);
        json.put("duration", duration);
        json.put("mode of travel", modeOfTravel);
        json.put("accommodation", accommodation);
        json.put("checklist", checklist);
        return json;
    }

}
