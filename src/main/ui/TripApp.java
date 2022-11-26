package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TripApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private Trip trip;
    private Scanner input;
    ArrayList<Itinerary> itineraryList = new ArrayList<>();
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public TripApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        workRoom = new WorkRoom("Disha's workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTrip();
    }

    public void runTrip() {
        boolean keepGoing = true;
        String command;
        init();
        welcomePage();
        while (keepGoing) {
            command = sendInput(displayMenu(), true);
            if (command.equals("q")) {
                String optionString = sendInput("Would you like to save your trip?", true);
                char option = optionString.charAt(0);
                switch (option) {
                    case 'y':
                        saveTrip();
                    default:
                        keepGoing = false;
                }
                printLog(EventLog.getInstance());
            } else {
                processCommand(command);
            }
        }
        System.out.println("Thank you, and bon voyage!");
    }

    // MODIFIES: this
    // EFFECTS: initialises user
    public void init() {
        trip = new Trip("Disha");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: initialises variables in trip by getting user information
    @SuppressWarnings("methodlength")
    public void welcomePage() {
        Trip.TravelMode travelMode = Trip.TravelMode.NULL;
        System.out.println("Welcome to Bon Voyage, " + trip.getName() + "!");
        String optionA = sendInput("Press 'l' to load previous trip or 'n' for a new one.", true);
        char option = optionA.charAt(0);
        if (option == 'l') {
            loadTrip();
            displaySummary();
        } else if (option == 'n') {
            String location = sendInput("\nWhere are you travelling to?\n", false);
            int duration = Integer.parseInt(sendInput("\nHow long are you going to stay?\n", false));
            String s = sendInput("\nHow are you travelling to your destination?\n\nPlease enter:\n"
                    + "Flight -> f\n" + "Train -> t\n" + "Car -> c\n" + "Other -> o", true);
            char mode = s.charAt(0);
            switch (mode) {
                case 'f':
                    travelMode = Trip.TravelMode.FLIGHT;
                    break;
                case 't':
                    travelMode = Trip.TravelMode.TRAIN;
                    break;
                case 'c':
                    travelMode = Trip.TravelMode.CAR;
                    break;
            }
            String acc = sendInput("\nWhere are you staying?\n", false);
            trip.setLocation(location);
            trip.setDuration(duration);
            trip.setModeOfTravel(travelMode);
            trip.setAccommodation(acc);
        }
    }

    // EFFECTS: displays menu of options to user
    public String displayMenu() {
        String s = "\nWhat would you like to do?\n\n"
                + "Update duration of stay -> u\n"
                + "Create itinerary -> i\n"
                + "Create checklist -> c\n"
                + "Display summary of trip -> s\n"
                + "Quit -> q";
        return s;
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void processCommand(String command) {
        if (command.equals("u")) {
            updateStay();
        }
        if (command.equals("i")) {
            createItinerary();
        }
        if (command.equals("c")) {
            createChecklist();
        }
        if (command.equals("s")) {
            displaySummary();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates trip.duration according to user input, by adding or subtracting specified no of days
    public void updateStay() {
        String command = sendInput("If you want to add days to the duration of your stay, press -> a"
                                    + "\nIf you want to shorten the duration of your stay, press -> s",
                                    true);
        boolean addDays = false;
        if (command.equals("a")) {
            addDays = true;
        } else if (command.equals("s")) {
            addDays = false;
        }
        int days = Integer.parseInt(sendInput("Enter the number of days:", false));
        trip.updateDuration(days, addDays);
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: creates an itinerary based on user input
    public void createItinerary() {
        for (int i = 1; i <= trip.getDuration(); i++) {
            String command = sendInput("Do you want to add places to visit on Day " + i + "?"
                    + "\nPress 'y' for yes and 'n' for no.", true);
            if (command.equals("y")) {
                itineraryList.add(createItineraryDay());
            } else if (command.equals("n")) {
                itineraryList.add(createEmptyItinerary());
            }
        }
        System.out.println("Here is your itinerary:");
        displayItineraryList(itineraryList);
    }

    // EFFECTS: Creates an itinerary for one day
    public Itinerary createItineraryDay() {
        Itinerary itineraryTemp = new Itinerary();
        boolean keepGoing = true;
        while (keepGoing) {
            String place = sendInput("Which place do you want to visit?", false);
            int hours = Integer.parseInt(sendInput("How much time do you want to spend there?", false));
            String notes = sendInput("Anything you want to add?", false);
            itineraryTemp.addToItinerary(place, hours, notes);
            String command = sendInput("Do you want to add another place?"
                    + "\nPress 'y' for yes and 'n' for no.", true);
            if (command.equals("n")) {
                keepGoing = false;
            } else if (command.equals("y")) {
                keepGoing = true;
            }
        }
        return itineraryTemp;
    }

    // EFFECTS: creates an empty itinerary for the day
    public Itinerary createEmptyItinerary() {
        Itinerary itineraryTemp = new Itinerary();
        itineraryTemp.addToItinerary("n/a", 0, "n/a");
        return itineraryTemp;
    }

    // EFFECTS: Displays full itinerary
    public void displayItineraryList(ArrayList<Itinerary> itineraryArrayList) {
        System.out.println("Your Itinerary is:");
        for (int i = 0; i < trip.getDuration(); i++) {
            int j = i + 1;
            System.out.println("Day " + j + ":");
            displayItinerary(itineraryArrayList.get(i));
        }
    }

    // EFFECTS: Displays itinerary for one day
    public void displayItinerary(Itinerary itineraryItems) {
        ArrayList<ItineraryItem> itineraryItemArrayList;
        itineraryItemArrayList = itineraryItems.getNewItinerary();
        for (int i = 0; i < itineraryItemArrayList.size(); i++) {
            int j = i + 1;
            ItineraryItem itemItinerary = itineraryItemArrayList.get(i);
            System.out.println("[" + j + "]");
            System.out.println("Place to visit: " + itemItinerary.getLocation());
            System.out.println("Hours to spend there: " + itemItinerary.getHours());
            System.out.println("Notes: " + itemItinerary.getNotes());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a checklist based on user input
    public void createChecklist() {
        System.out.println("Here is a checklist, with essentials for your travel:");
        ArrayList<String> tripChecklist;
        tripChecklist = trip.getNewChecklist();
        showChecklist(tripChecklist);
        boolean keepGoing = true;
        while (keepGoing) {
            String command = sendInput("Would you like to add more items to your checklist? "
                    + "Press 'y' for yes and 'n' for no.", true);
            if (command.equals("n")) {
                keepGoing = false;
            } else {
                String item = sendInput("What do you want to add to your checklist?\n", false);
                trip.addToChecklist(item);
            }
        }
        System.out.println("Here is your updated checklist:\n");
        showChecklist(trip.getChecklist());
    }

    // EFFECTS: shows the current checklist
    public void showChecklist(ArrayList<String> checklist) {
        int n = checklist.size();
        for (int i = 0; i < n; i++) {
            String s = checklist.get(i);
            int j = i + 1;
            System.out.println(j + ". " + s);
        }
    }

    // EFFECTS: displays a summary of your trip
    public void displaySummary() {
        String s = trip.summary();
        System.out.println(s);
    }

    // EFFECTS: receives input string and
    //          - if isLowerCase is true, converts output string to
    //          all lowercase letters and returns the string
    //          - if isLowerCase is false, returns the output string as it is
    public String sendInput(String inputString, boolean isLowerCase) {
        System.out.println(inputString);
        String command = input.next();
        if (isLowerCase) {
            command = command.toLowerCase();
            return command;
        }
        return command;
    }

    // EFFECTS: saves the trip to file
    public void saveTrip() {
        try {
            workRoom.addTrip(trip);
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
            System.out.println("Saved " + trip.getLocation() + " trip to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads trip from file
    private void loadTrip() {
        try {
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
            List<Trip> tripList = workRoom.getTrips();
            System.out.println(tripList.size());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void printLog(EventLog eventLog) {
        for (Event next : eventLog) {
            System.out.println(next.toString());
        }
    }

    public static void main(String[] args) {
        try {
            new TripApp();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file.");
        }
    }
}
