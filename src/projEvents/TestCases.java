package projEvents;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;






/***************************************************************************************************
 HUMAN TESTING

 ***************************************************************************************************/
/*
Testing Opening
GUI opens succesfully on launch with red box around current date.
 */

/*
Testing Error, invalid names with homework and event
Created a homework event and tried to put in nothing for the name and and class
When trying to save event, says there is an invalid input and that class can't be null.
If class is put in validly and tried to save again says the name is not valid
if name is put in correctly then all events have valid inputs then it saves properly
 */



/***************************************************************************************************
 JUNIT TEST CASES
 ***************************************************************************************************/

public class TestCases {
    @BeforeEach
    @AfterEach
    public void DeleteSave() {
        File file = new File("Save.txt");
        file.delete();
    }

    @Test
    public void TestOneEventCreation() {

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
    public void TestMultipleEventsOnOneDay() {

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

        storage.Clear();
    }

    @Test
    public void TestMultipleEventsOn30DifferentDays() {
        EventStorage storage = EventStorage.getInstance();

        LocalDate date = LocalDate.of(2020, 4, 20);

        LinkedList<Events> trueList = new LinkedList<>();

        String name = "";

        for (int i = 0; i < 31; i++) {
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
        for (int i = 0; i < 31; i++) {
            date = date.plusDays(1);

            Events assertEvent = trueList.remove();
            Events retrievedEvent = storage.GetListOfDay(date).remove();

            assertEquals(retrievedEvent.getName(), assertEvent.getName());
        }
        storage.Clear();
    }

    @Test
    public void TestSaveLoad() {
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
    public void testDelete() {
        LocalDate date = LocalDate.of(2020, 4, 20);
        Events e = new Events("Test Event", "details", date);
        EventStorage storage = EventStorage.getInstance();
        storage.addEvent(e);

        assertEquals(storage.GetListOfDay(date).peek(), e);

        storage.DeleteEvent(date, "Test Event");
        assertNull(storage.GetListOfDay(date));

        storage.Clear();
    }

    @Test
    public void TestErrors() {
        Errors e = Errors.getInstance();
        assertNull(e.getError());
        e.setError("test error");
        assertEquals("test error", e.getError());
        assertFalse(e.getBool());
    }

    @Test
    public void TestBusiness() {
        LocalDate date = LocalDate.of(1234, 1, 5);
        LocalDate datw = LocalDate.of(3, 5, 7);
        Business a = new Business("sdf", "deets", date, "loc", 2.7);
        Business b = new Business("sdf", "deets", date, "loc", 2.7);
        Business c = new Business("f", "dets", datw, "lc", 2.1);
        Business d = new Business();

        assertTrue(a.equals(b));
        assertFalse(a.equals(c));

        Errors e = Errors.getInstance();
        a.setDuration(34);
        assertEquals("Business events cannot last longer than 24 hours", e.getError());
        a.setDuration(-2);
        assertEquals("Duration of an event cannot be zero", e.getError());

        assertEquals(2.7, a.getDuration());
        assertEquals("loc", a.getLocation());
    }

    @Test
    public void TestHomework() {
        LocalDate date = LocalDate.of(1234, 1, 5);
        LocalDate datw = LocalDate.of(3, 5, 7);
        Homework a = new Homework("sdf", "deets", date, "loc", "cheese", 69);
        Homework b = new Homework("sdf", "deets", date, "loc", "cheese", 69);
        Homework c = new Homework("f", "dets", datw, "lc", "lava", 42);
        Homework d = new Homework();

        assertTrue(a.equals(b));
        assertFalse(a.equals(c));

        Errors e = Errors.getInstance();

        a.setclassFor("");
        assertEquals("enter valid class name", e.getError());
        a.setclassFor("asdjfhlakjdsfhlkjadshflkjasdhflkjadshflhasdfcJAHRIULAHFDahsdlCGDKjbafkGWIDHcidyufgLWDNkwcgbiuHFKL" +
                "DGfjkdsklhgSKDLSHAGFYAGDFLhjfsagld");
        assertEquals("enter valid class name", e.getError());

        a.setgradeGot(-20);
        assertEquals("Did you really get less than 0 on this assignment", e.getError());
        a.setgradeGot(101);
        assertEquals("Dont tell me you got over 100% you scrub", e.getError());

        a.setgradeGot(57);
        a.setgradeOut(101);
        assertNull(e.getError());
        a.setgradeOut(39);
        assertEquals("Dont tell me you got over 100% you scrub", e.getError());


    }
}
