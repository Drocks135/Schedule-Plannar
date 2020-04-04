package GUI;


import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;

public class MainGUI extends Application {

    private MonthView monthView = new MonthView();

    public static void launchGUI(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        monthView.display();
    }

    public static String readFileAsString(String fileName) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void save(EventPackage[] event) {
        //TODO
        // create/overwrite file:
        // create string: check
        // loop to create a string of events: check
        // save string to file:
        String eventString = "";
        for (int i = 0; i < event.length; i++) {
            eventString += toString(event[i]);
        }
    }

    public String toString(EventPackage event) {
        String eventString = "";
        eventString += (event.getEventName() + ",");
        eventString += (event.getEventDate() + ",");
        eventString += (event.getEventContactName() + ";");
        return eventString;
    }

    public EventPackage stringToEvent(String event) {
        EventPackage evnt = new EventPackage();
        // Splits events into an array of Strings.
        String[] temp = event.split(",");
        evnt.setEventName(temp[0]);
        evnt.setEventDate(temp[1]);
        evnt.setEventContactName(temp[2]);
        return evnt;
    }

    public EventPackage[] load() throws IOException {
        // We could use any other way to load a file i just threw this one here for testing
        String data = readFileAsString("Schedule-Plannar-master\\save_load.txt");
        String[] events = data.split(";"); // split file into different events
        EventPackage[] eventPacs = new EventPackage[events.length];
        for (int i = 0; i < events.length; i++) {
            eventPacs[i] = stringToEvent(events[i]);
        }
        return eventPacs;
    }
}
