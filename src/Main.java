
import GUI.MonthView;
import javafx.application.Application;
import javafx.stage.Stage;
import projEvents.Events;
import projEvents.Homework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main extends Application {

    private MonthView monthView = new MonthView();

    public static void main(String[] args) {
        Main gui = new Main();
        gui.launchGUI(args);

    }

    public static void launchGUI(String[] args){
        launch(args);
    }
    public void createEvent(){

    }

    @Override
    public void start(Stage stage) {
        monthView.display();
    }

    public static String readFileAsString(String fileName) throws IOException {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void save(Homework[] event) throws IOException {
        File file = new File("Schedule-Plannar-master\\save_load.txt");
        StringBuilder eventString = new StringBuilder();
        for (int i = 0; i < event.length; i++) {
            eventString.append(hmwkToString(event[i]));
        }
        if (file.exists()) {
            // overwrites in case file already exists.
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(eventString.toString().getBytes());
            fos.close();
        }
    }

    public String eventToString(Events event) {
        String eventString = "";
        eventString += (event.getName() + "," + event.getDetails() + "," +
                event.getDue() + ";");
        return eventString;
    }

    public String hmwkToString(Homework event) {
        String eventString = "";
        eventString += (eventToString(event) + ",");
        eventString += (event.getturnInPlace() + "," + event.getclassFor() + "," +
                event.gradeGot() + "," + event.gradeOut() + ";");
        return eventString;
    }

/*    public Events stringToEvent(String event) throws ParseException {
        // Splits events into an array of Strings.
        String[] temp = event.split(",");
        Date d;
        SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        d = ft.parse(temp[2]);
        // String date = String.format("%tD %tr", date);
        Events evnt = new Events(temp[0], temp[1], d);
        return evnt;
    }*/

    public Events stringToHmwk(String event) throws ParseException {
        // Splits events into an array of Strings.
        String[] temp = event.split(",");
        Date d;
        SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        // String date = String.format("%tD %tr", date);
        d = ft.parse(temp[2]);
        if(temp.length == 3) {
            return new Events(temp[0], temp[1], d);
        }
        else if(temp.length == 5) {
            return new Homework(temp[0], temp[1], d, temp[3], temp[4]);
        }
        else if(temp.length == 6) {
            return new Homework(temp[0], temp[1], d, temp[3], temp[4],
                    Integer.parseInt(temp[5]));
        }
        return new Homework();
    }

    public Events[] load() throws IOException, ParseException {
        // We could use any other way to load a file i just threw this one here for testing
        String data = readFileAsString("Schedule-Plannar-master\\save_load.txt");
        String[] events = data.split(";"); // split file into different events
        Events[] eventBois = new Events[events.length];
        for (int i = 0; i < events.length; i++) {
            eventBois[i] = stringToHmwk(events[i]);
        }
        return eventBois;
    }

}
