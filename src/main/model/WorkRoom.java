package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// CITATION: adopted from project: JSonSerialisationDemo @
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a workroom having a collection of trips
public class WorkRoom implements Writable {
    private String name;
    private List<Trip> trips;

    // EFFECTS: constructs workroom with a name and empty list of thingies
    public WorkRoom(String name) {
        this.name = name;
        trips = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds trip to this workroom
    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    // EFFECTS: returns an unmodifiable list of trips in this workroom
    public List<Trip> getTrips() {
        return Collections.unmodifiableList(trips);
    }

    // EFFECTS: returns number of trips in this workroom
    public int numTrips() {
        return trips.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("trips", tripsToJson());
        return json;
    }

    // EFFECTS: returns trips in this workroom as a JSON array
    private JSONArray tripsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Trip t : trips) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
