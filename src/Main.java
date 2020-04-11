import GUI.GUI_Launcher;
import projEvents.EventStorage;
import projEvents.Events;
import projEvents.Homework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        GUI_Launcher gui = new GUI_Launcher();
        EventStorage eventStorage = EventStorage.getInstance();

        gui.launchGUI(args);



    }

    public static String readFileAsString(String fileName) throws IOException {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void save(Events[] events) throws IOException {
        File file = new File("save_load.txt");
        StringBuilder eventString = new StringBuilder();
        for (int i = 0; i < events.length; i++) {
            eventString.append(events[i].toString());
        }
        // overwrites in case file already exists.
        FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(eventString.toString().getBytes());
        fos.close();
        System.out.println("saved");
    }

    public Events stringTo(String event) throws ParseException {
        // Splits events into an array of Strings.
        String[] temp = event.split(",");
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("dMMyyyy");
        LocalDate d = LocalDate.parse(temp[2], ft);
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
        return new Events("something", "went_wrong", d);
    }

    public Events[] load() throws IOException, ParseException {
        // We could use any other way to load a file i just threw this one here for testing
        String data = readFileAsString("save_load.txt");
        String[] events = data.split(";"); // split file into different events
        Events[] eventBois = new Events[events.length];
        for (int i = 0; i < events.length; i++) {
            eventBois[i] = stringTo(events[i]);
        }
        return eventBois;
    }

}
