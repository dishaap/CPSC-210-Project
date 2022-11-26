package persistence;

// CITATION: adopted from project: JSonSerialisationDemo @
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Trip;
import model.WorkRoom;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads trip from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private WorkRoom parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WorkRoom wr = new WorkRoom(name);
        addTrips(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses trips from JSON object and adds them to workroom
    private void addTrips(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("trips");
        for (Object json : jsonArray) {
            JSONObject nextTrips = (JSONObject) json;
            addTrip(wr, nextTrips);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses trip from JSON object and adds it to workroom
    private void addTrip(WorkRoom wr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Trip trip = new Trip(name);
        wr.addTrip(trip);
    }
}
