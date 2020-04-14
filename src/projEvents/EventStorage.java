package projEvents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

/**************************************************************************************************
 *  @author: Dalton Claybaugh
 *  @Version: 1.0
 *  EventStorage is a data structure designed to store Events. It's implementation is a hash map
 *  of linked list. A hash of a specific day will return the head to a linked list that contains
 *  an unsorted list of all events that will occur on a specific day.
 **************************************************************************************************/
public class EventStorage {
    private final HashMap<String, LinkedList<Events>> EventMap;

    private static EventStorage storage;
    private int size;

    private EventStorage(){
       EventMap = new HashMap<>();
       size = 0;

    }

    /**********************************************************************************************
     *  This is a singleton implementation of EventStorage, by calling getInstance the return will
     *  either be a new instance if one does not exist or the existing instance. On program start
     *  the program will check for an existing save file and if it exists, will load from that
     *  into EventStorage.
     * @return The EventStorage object that the program is currently using
     *********************************************************************************************/
    public static EventStorage getInstance(){
        if (storage == null) {
            storage = new EventStorage();
            try {
                File SaveLocation = new File("Save.txt");
                if (SaveLocation.exists())
                    storage.load();
            } catch (Exception e) {
                System.out.println("Invalid Load");
            }
        }
        return storage;
    }

    /**********************************************************************************************
     *  Adds an event into the EventStorage container. If the key exists it will append it to the
     *  end of the linked list of all events that exist with that key.
     * @param event: An Events object
     *********************************************************************************************/
    public void addEvent(Events event){
        LocalDate date = event.getDue();

        String key = Integer.toString(date.getDayOfMonth()) + date.getMonthValue() + date.getYear();
        LinkedList<Events> list;
        if(EventMap.containsKey(key)){
            list = EventMap.get(key);
            list.add(event);
            size++;
        } else {
            list = CreateList(event);
            EventMap.put(key, list);
            size++;
        }
        try {
            storage.save();
        } catch (Exception e){
            System.out.println("Program did not save");
        }
    }

    /**********************************************************************************************
     *  Retrieves the head of a linked list that contains all the events for a particular day
     * @param date: A LocalDate object for the day of events to be retrieved
     * @return The head of a linked list of Events that contains all the Events of a specific day
     *********************************************************************************************/
    public LinkedList<Events> GetListOfDay(LocalDate date){
        String key = Integer.toString(date.getDayOfMonth()) + date.getMonthValue() + date.getYear();
        return EventMap.get(key);
    }

    /**********************************************************************************************
     * Deletes an Event from the EventStorage object
     * @param date: A LocalDate that the event that you want to remove is on
     * @param name: The name of an event that you want to remove
     *********************************************************************************************/
    public void DeleteEvent(LocalDate date, String name){
        String key = Integer.toString(date.getDayOfMonth()) + date.getMonthValue() + date.getYear();
        if(EventMap.containsKey(key)){
            LinkedList<Events> temp = EventMap.get(key);
            ListIterator<Events> iter = temp.listIterator(0);
            while (iter.hasNext()){
                Events e = iter.next();
                if(e.getName() == name)
                    iter.remove();
            }
        }
    }

    /**********************************************************************************************
     * Creates an ArrayList of all events that exist in EventStorage
     * @return An ArrayList containing all events in EventStorage
     *********************************************************************************************/
    public ArrayList<Events> toArray(){
        ArrayList<Events> allEvents = new ArrayList<>(size);
        Set<String> keySet = EventMap.keySet();

        for (String s : keySet) {
            LinkedList<Events> temp = EventMap.get(s);
            allEvents.addAll(temp);
        }

        return allEvents;
    }

    /**********************************************************************************************
     *
     *********************************************************************************************/
    private LinkedList<Events> CreateList(Events event){
        LinkedList<Events> list = new LinkedList<>();
        list.add(event);
        return list;
    }

    /**********************************************************************************************
     *
     *********************************************************************************************/
    public static String readFileAsString(String fileName) throws IOException {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    /**********************************************************************************************
     *
     *********************************************************************************************/
    private void save() throws IOException {
        File file = new File("Save.txt");
        StringBuilder eventString = new StringBuilder();
        ArrayList<Events> events = EventStorage.getInstance().toArray();
        for (int i = 0; i < size; i++) {
            eventString.append(events.get(i).toString());
        }
        // overwrites in case file already exists.
        FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(eventString.toString().getBytes());
        fos.close();
        System.out.println("saved");
    }

    /**********************************************************************************************
     *
     *********************************************************************************************/
    public Events stringTo(String event) throws ParseException {
        // Splits events into an array of Strings.
        String[] temp = event.split(",");
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("dMMyyyy");
        LocalDate d = LocalDate.parse(temp[2], ft);
        // for a basic Events object.
        if(temp.length == 3) {
            return new Events(temp[0], temp[1], d);
        }
        // for a Business object
        else if(temp.length == 5) {
            return new Business(temp[0], temp[1], d, temp[3], Double.parseDouble(temp[4]));
        }
        // for a Homework object with a gradeOut.
        else if(temp.length == 6) {
            return new Homework(temp[0], temp[1], d, temp[3], temp[4], Integer.parseInt(temp[5]));
        }
        return new Events("something", "went_wrong", d);
    }

    /**********************************************************************************************
     *
     *********************************************************************************************/
    private void load() throws IOException, ParseException {
        String data = readFileAsString("Save.txt");
        String[] events = data.split(";"); // split file into different events
        for (int i = 0; i < events.length; i++) {
            EventStorage.getInstance().addEvent(stringTo(events[i]));
        }
    }
}
