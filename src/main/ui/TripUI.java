package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// CITATION: SplashScreen tutorial from https://www.tutorialsfield.com/java-splash-screen-with-progress-bar/

// Represents the User Interface where the app is run
public class TripUI extends JFrame {
    private static final String JSON_STORE = "./data/workroom.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private Trip trip;
    private JLabel image = new JLabel(new ImageIcon("./data/airplane-resize.jpg"));
    private JLabel text;
    private JPanel textPanel;
    private JTextArea textArea;
    private JProgressBar progressBar = new JProgressBar();
    private JLabel message = new JLabel();
    private Scanner input;
    private WorkRoom workRoom;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Itinerary itineraryList;
    private ItineraryItem itineraryItem;
    private String item;
    private int hours;
    private String note;

    public TripUI() throws FileNotFoundException {
        super("Trip");
        trip = new Trip("Disha");
        workRoom = new WorkRoom("Disha's workroom");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        createGraphics();
    }

    // MODIFIES: this
    // EFFECTS: creates a new JFrame window for the trip
    public void createGraphics() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setLocationRelativeTo(null);
        createDialogBox();

    }

    // MODIFIES: this
    // EFFECTS: creates a dialog box to let the user decide whether they want to load previous data or create a new trip
    public void createDialogBox() {
        // createSplashScreen();
        int result = JOptionPane.showConfirmDialog(this, "Welcome to Bon Voyage!\n\nWould you"
                + " like to load your previous trip?\n\nPress Yes to load previous data, No to continue to making a new"
                + " trip\n\nPress Cancel to exit the application");
        switch (result) {
            case 0:
                loadPreviousTrip();
                break;
            case 1:
                makeNewTrip();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Press any button to exit.");
                System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the previous saved trip from the workroom for the user to view.
    public void loadPreviousTrip() {
        createSplashScreen();
        loadTrip();
        JOptionPane.showMessageDialog(this, "Trip successfully loaded. Proceeding to menu...");
        this.setVisible(false);
        displayMenu();
    }

    // MODIFIES: trip
    // EFFECTS: assigns saved trip from workroom to this trip.
    private void loadTrip() {
        try {
            workRoom = jsonReader.read();
            System.out.println("Loaded " + workRoom.getName() + " from " + JSON_STORE);
            List<Trip> tripList = workRoom.getTrips();
            trip = tripList.get(0);
            System.out.println(tripList.size());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to create a new trip
    public void makeNewTrip() {
        createSplashScreen();
        this.setVisible(false);
        enterInitInput();
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: trip
    // EFFECTS: allows the user to enter input for their new trip
    public void enterInitInput() {
        JFrame enterInput = new JFrame();
        enterInput.setLayout(new BorderLayout());
        enterInput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        enterInput.setSize(WIDTH, HEIGHT);
        enterInput.setLocationRelativeTo(null);
        Container c = enterInput.getContentPane();
        c.setLayout(null);

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nameLabel.setSize(200, 25);
        nameLabel.setLocation(100, 30);
        c.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setSize(200, 25);
        nameField.setLocation(300, 30);
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trip = new Trip(e.toString());
            }
        });
        c.add(nameField);

        JLabel locationLabel = new JLabel("Where are you going?");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        locationLabel.setSize(200, 25);
        locationLabel.setLocation(100, 60);
        c.add(locationLabel);

        JTextField locationField = new JTextField();
        locationField.setFont(new Font("Arial", Font.PLAIN, 15));
        locationField.setSize(200, 25);
        locationField.setLocation(300, 60);
        locationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trip.setLocation(e.toString());
            }
        });
        c.add(locationField);

        JLabel durationLabel = new JLabel("How long are you going to stay there?");
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        durationLabel.setSize(200, 25);
        durationLabel.setLocation(100, 90);
        c.add(durationLabel);

        JTextField durationField = new JTextField();
        durationField.setFont(new Font("Arial", Font.PLAIN, 15));
        durationField.setSize(200, 25);
        durationField.setLocation(300, 90);
        durationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trip.setDuration(Integer.parseInt(e.toString()));
            }
        });
        c.add(durationField);

        JLabel modeOfTravelLabel = new JLabel("Please select your mode of travel.");
        modeOfTravelLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        modeOfTravelLabel.setSize(200, 25);
        modeOfTravelLabel.setLocation(100, 120);
        c.add(modeOfTravelLabel);

        JRadioButton flight = new JRadioButton("Flight");
        flight.setFont(new Font("Arial", Font.PLAIN, 15));
        flight.setSelected(false);
        flight.setSize(80, 25);
        flight.setLocation(300, 120);
        c.add(flight);

        JRadioButton train = new JRadioButton("Train");
        train.setFont(new Font("Arial", Font.PLAIN, 15));
        train.setSelected(false);
        train.setSize(80, 25);
        train.setLocation(390, 120);
        c.add(train);

        JRadioButton car = new JRadioButton("Car");
        car.setFont(new Font("Arial", Font.PLAIN, 15));
        car.setSelected(false);
        car.setSize(80, 25);
        car.setLocation(480, 120);
        c.add(car);

        JRadioButton other = new JRadioButton("0ther");
        other.setFont(new Font("Arial", Font.PLAIN, 15));
        other.setSelected(false);
        other.setSize(80, 25);
        other.setLocation(570, 120);
        c.add(other);

        ButtonGroup modeOfTravel = new ButtonGroup();
        modeOfTravel.add(flight);
        modeOfTravel.add(train);
        modeOfTravel.add(car);
        modeOfTravel.add(other);

        JLabel accommodationLabel = new JLabel("Where are you staying?");
        accommodationLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        accommodationLabel.setSize(200, 25);
        accommodationLabel.setLocation(100, 150);
        c.add(accommodationLabel);

        JTextArea accommodationArea = new JTextArea();
        accommodationArea.setSize(300, 200);
        accommodationArea.setFont(new Font("Arial", Font.PLAIN, 15));
        accommodationArea.setLocation(300, 150);
        c.add(accommodationArea);

        JButton submit = new JButton("Submit");
        submit.setSize(100, 25);
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setLocation(WIDTH - 700, HEIGHT - 100);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e.getSource() == submit) {
                        enterInput.setVisible(false);
                        displayMenu();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        c.add(submit);

        JButton reset = new JButton("Reset");
        reset.setSize(100, 25);
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setLocation(WIDTH - 500, HEIGHT - 100);
        c.add(reset);

        enterInput.setVisible(true);
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: Displays a menu allowing the user to choose from options to change their trip
    public void displayMenu() {
        JFrame menu = new JFrame();
        menu.setLayout(new BorderLayout());
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setSize(WIDTH, HEIGHT);
        menu.setLocationRelativeTo(null);
        Container c = menu.getContentPane();
        c.setLayout(null);

        text = new JLabel("What would you like to do next?");
        text.setSize(500, 30);
        text.setLocation(250, 10);
        text.setFont(new Font("Arial", Font.PLAIN, 25));
        c.add(text);

        JButton itinerary = new JButton("Create Itinerary");
        itinerary.setSize(200, 25);
        itinerary.setFont(new Font("Arial", Font.PLAIN, 15));
        itinerary.setLocation(WIDTH - 700, 50);
        itinerary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == itinerary) {
                    menu.setVisible(false);
                    createItinerary();
                }
            }
        });
        c.add(itinerary);

        JButton checklist = new JButton("Create Checklist");
        checklist.setSize(200, 25);
        checklist.setFont(new Font("Arial", Font.PLAIN, 15));
        checklist.setLocation(WIDTH - 700, 100);
        c.add(checklist);

        JButton summary = new JButton("Display summary");
        summary.setSize(200, 25);
        summary.setFont(new Font("Arial", Font.PLAIN, 15));
        summary.setLocation(WIDTH - 700, 150);
        c.add(summary);

        JButton quit = new JButton("Quit Application");
        quit.setSize(200, 25);
        quit.setFont(new Font("Arial", Font.PLAIN, 15));
        quit.setLocation(WIDTH - 700, 200);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e.getSource() == quit) {
                        menu.setVisible(false);
                        quit();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        c.add(quit);

        menu.setVisible(true);
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: creates an itinerary for the user to add multiple items and displays the itinerary
    public void createItinerary() {

        JFrame itinerary = new JFrame();
        itinerary.setLayout(new BorderLayout());
        itinerary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itinerary.setSize(WIDTH, HEIGHT);
        itinerary.setLocationRelativeTo(null);
        Container c = itinerary.getContentPane();
        c.setLayout(null);

        text = new JLabel("Add items to your itinerary below.");
        text.setFont(new Font("arial", Font.BOLD, 20));
        text.setSize(300, 35);
        text.setLocation(200, 20);
        c.add(text);

        JLabel addItems = new JLabel("Add item:");
        addItems.setFont(new Font("arial", Font.BOLD, 15));
        addItems.setSize(100, 35);
        addItems.setLocation(200, 70);
        c.add(addItems);

        JTextField addField = new JTextField();
        addField.setFont(new Font("arial", Font.BOLD, 15));
        addField.setSize(100, 35);
        addField.setLocation(350, 70);
        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item = e.toString();
            }
        });
        c.add(addField);

        JLabel durationLabel = new JLabel("Add duration:");
        durationLabel.setFont(new Font("arial", Font.BOLD, 15));
        durationLabel.setSize(100, 35);
        durationLabel.setLocation(200, 100);
        c.add(durationLabel);

        JTextField addDuration = new JTextField();
        addDuration.setFont(new Font("arial", Font.BOLD, 15));
        addDuration.setSize(100, 35);
        addDuration.setLocation(350, 100);
        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hours = Integer.parseInt(e.toString());
            }
        });
        c.add(addDuration);

        JLabel notesLabel = new JLabel("Add duration:");
        notesLabel.setFont(new Font("arial", Font.BOLD, 15));
        notesLabel.setSize(100, 35);
        notesLabel.setLocation(200, 130);
        c.add(notesLabel);

        JTextArea notes = new JTextArea();
        notes.setSize(300, 200);
        notes.setFont(new Font("Arial", Font.PLAIN, 15));
        notes.setLocation(350, 130);
        c.add(notes);

        JButton submit = new JButton("Add more");
        submit.setSize(100, 25);
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setLocation(WIDTH - 700, HEIGHT - 100);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e.getSource() == submit) {
                        itineraryList.addToItinerary(item, hours, "");
                        itinerary.repaint();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        c.add(submit);

        JButton exit = new JButton("Exit");
        exit.setSize(100, 25);
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setLocation(WIDTH - 500, HEIGHT - 100);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e.getSource() == exit) {
                        itinerary.setVisible(false);
                        displayMenu();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        c.add(exit);

        itinerary.setVisible(true);
    }

    // EFFECTS: quits the application and gives the user an option to save the trip
    public void quit() {
        JFrame quitFrame = new JFrame();
        quitFrame.setLayout(new BorderLayout());
        quitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quitFrame.setSize(WIDTH, HEIGHT);
        quitFrame.setLocationRelativeTo(null);
        Container c = quitFrame.getContentPane();
        c.setLayout(null);

        int result = JOptionPane.showConfirmDialog(this, "Would you like to save the trip before"
                + " you quit?");
        switch (result) {
            case 0:
                saveTrip();
                JOptionPane.showMessageDialog(this, "Thank you! Your trip has been saved.");
                System.exit(0);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Thank you!");
                System.exit(0);
        }
        printLog(EventLog.getInstance());
        quitFrame.setVisible(true);
    }

    // MODIFIES: workRoom
    // EFFECTS: adds this trip to the workroom
    public void saveTrip() {
        try {
            workRoom.addTrip(trip);
            jsonWriter.open();
            jsonWriter.write(workRoom);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a GUI for the splash screen
    public void createSplashScreen() {
        getContentPane().setLayout(null);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);
        addImage();
        addText();
        addProgressBar();
        runningPBar();
    }

    // MODIFIES: this
    // EFFECTS: adds image to splash screen
    public void addImage() {
        image.setSize(213, 111);
        image.setBounds((WIDTH - 213) / 2, (HEIGHT - 300) / 2, 213, 111);
        add(image);
    }

    // MODIFIES: this
    // EFFECTS: adds text to splash screen
    public void addText() {
        text = new JLabel("LOADING");
        text.setFont(new Font("arial", Font.BOLD, 30));
        text.setBounds(440, 350, 300, 30);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS: adds a progress bar to show loading
    public void addProgressBar() {
        progressBar.setSize(200,30);
        progressBar.setBorderPainted(true);
        progressBar.setLocation(400, 450);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        add(progressBar);
    }

    // MODIFIES: this
    // EFFECTS: adds i to the progress bar at set intervals until 100% is reached
    public void runningPBar() {
        int i = 0;
        while (i <= 100) {
            try {
                Thread.sleep(50);
                progressBar.setValue(i);
                message.setText("LOADING " + Integer.toString(i) + "%");
                i++;
                if (i == 100) {
                    dispose();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void printLog(EventLog eventLog) {
        for (Event next : eventLog) {
            System.out.println(next.toString());
        }
    }

    public static void main(String[] args) {
        try {
            new TripUI();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
    }
}
