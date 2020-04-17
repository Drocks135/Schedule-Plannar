package projEvents;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//import org.testng.annotations.Test;

public class EventStorageTest {

    @Test
    public void TestOneEventCreation(){
        File file = new File("Save.txt");
        file.delete();

        EventStorage storage = EventStorage.getInstance();

        LocalDate date = LocalDate.of(2020, 4, 16);
        Events event = new Events("Birthday", "Mom's Birthday", date);

        storage.addEvent(event);

        LinkedList<Events> list = storage.GetListOfDay(date);

        LinkedList<Events> list2 = new LinkedList<>();

        list2.add(event);

        assertEquals(list.getFirst().getName(), list2.getFirst().getName());
        storage.Clear();
    }

    @Test
    public void TestMultipleEventsOnOneDay(){
        File file = new File("Save.txt");
        file.delete();

        EventStorage storage = EventStorage.getInstance();

        LocalDate date = LocalDate.of(2020, 4, 16);
        Events event = new Events("Birthday", "Mom's Birthday", date);
        Events event2 = new Events("Go Shopping", "Mom needs flowers", date);
        Events event3 = new Events("Shower", "You smell, shower before mom's birthday", date);
        Events event4 = new Events("Rember deodernt", "I'm running out of ideas for details", date);

        storage.addEvent(event);
        storage.addEvent(event2);
        storage.addEvent(event3);
        storage.addEvent(event4);

        LinkedList<Events> list = storage.GetListOfDay(date);

        LinkedList<Events> list2 = new LinkedList<>();

        list2.add(event);
        list2.add(event2);
        list2.add(event3);
        list2.add(event4);

        assertEquals(list.remove().getName(), list2.remove().getName());
        assertEquals(list.remove().getName(), list2.remove().getName());
        assertEquals(list.remove().getName(), list2.remove().getName());
        assertEquals(list.remove().getName(), list2.remove().getName());
        assertEquals(list.size(), 0);
        assertEquals(list2.size(), 0);

        storage.Clear();
    }

    @Test
    public void TestMultipleEventsOn30DifferentDays(){
        File file = new File("Save.txt");
        file.delete();

        EventStorage storage = EventStorage.getInstance();

        LocalDate date = LocalDate.of(2020, 4, 20);

        LinkedList<Events> trueList = new LinkedList<>();

        String name = "";

        for(int i = 0; i < 31; i++){
            name = name + "a";
            date = date.plusDays(1);
            Events e = new Events(name, "", date);
            storage.addEvent(e);
            trueList.add(e);
        }

        //Reset variables to iterate through again
        date = LocalDate.of(2020, 4, 20);
        name = "";

        //Run through 30 days in a row checking each name as it comes out of event storage
        for(int i = 0; i < 31; i++){
            date = date.plusDays(1);

            Events assertEvent = trueList.remove();
            Events retrievedEvent = storage.GetListOfDay(date).remove();

            assertEquals(retrievedEvent.getName(), assertEvent.getName());
        }
        storage.Clear();
    }

    @Test
    public void TestSaveLoad(){
        File file = new File("Save.txt");
        file.delete();
        EventStorage storage = EventStorage.getInstance();

        LocalDate date = LocalDate.of(2020, 4, 20);
        LocalDate diffDate = LocalDate.of(2020, 8, 20);

        Events e = new Events("Test Event", "details", date);
        Homework h = new Homework("name", "details", date, "turnInPlace", "classFor");
        Business b = new Business("business", "details", diffDate, "location", 2.0);

        storage.addEvent(e);
        storage.addEvent(h);
        storage.addEvent(b);

        //Force reload of storage
        storage.Clear();
        storage = EventStorage.getInstance();

        assertEquals(storage.GetListOfDay(date).pop(), e);
        assertEquals(storage.GetListOfDay(date).pop(), h);
        assertEquals(storage.GetListOfDay(diffDate).pop(), b);

        storage.Clear();

    }

    @Test
    public void testDelete(){
        File file = new File("Save.txt");
        file.delete();
        LocalDate date = LocalDate.of(2020, 4, 20);
        Events e = new Events("Test Event", "details", date);
        EventStorage storage = EventStorage.getInstance();
        storage.addEvent(e);

        assertEquals(storage.GetListOfDay(date).peek(), e);

        storage.DeleteEvent(date, "Test Event");
        assertNull(storage.GetListOfDay(date).peekFirst());
    }

}