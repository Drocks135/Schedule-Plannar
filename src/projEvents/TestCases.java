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
    NO SAVE FILE: GUI opens succesfully on launch
    WITH EMPTY SAVE FILE: GUI opens succesfully on launch
    WITH NON-EMPTY SAVE FILE: GUI Launches with red box around events that were in the save file and loaded
 */

/*
Testing NULL NAMES AND DESCRIPTION Errors

    Created a homework event and tried to put in nothing for the name and and class

    When trying to save event, says there is an invalid input and that class can't be null.

    If class is put in validly and tried to save again says the name is not valid

    if name is put in correctly then all events have valid inputs then it saves properly

 */


/*
Testing Names, Classes and Descriptions that are too long Errors


    Created a homework event and tried to put in a long string of over 40 characters for the name
    When trying to save event, says there is an invalid input and that class can't be over x amount of characters
    If class name is put in validly after error box is shown, and everything else is valid then it can be saved succesfully


    Repeated with valid inputs for everything but Class, where class was over character limit
    Error box shows up "Enter valid class name"
    tried saving again with valid class name of "a"
    Saved succesfully

    Repeated with valid inputs for everything but Details, where details was over character limit of 250
    Error box shows up "Event details can not be over 250 characters long"
    tried saving again with valid class details of "a"
    Saved succesfully


    TESTED ALL THREE AGAIN AFTER SAVING BY USING EDIT: Same results as above
 */

/*
Testing business class Durations

    Created a new business class and attempted to put in 0 for duration time
    Got an error box "Duration of an event cannot be 0"

    Tried to put duration as -1
    Got same error box

    Tried to put duration as -2,147,483,648
    Got same error box

    Tried to put duration as 2,147,483,647
    Got error "Duration of event cannot exceed 24 hours"

    Tried to put duration as 24
    worked

    Tried to put duration as .0
    got error "Duration of event cannot be 0"

    Tried to put duration as 24.0
    Worked



 */


/*
Testing event creations in past

    Tried creating an event in 2014
    Saved it, showed up as red for correct date
    Closed program
    Rechecked for event
    Events were in correct spot in 2014 and nowhere else
 */

/*
Testing event creations in future

    Tried creating two events in 2023
    Saved it, showed up as red for correct date
    Closed program
    Rechecked for event
    Events were in correct spot in 2023 and nowhere else
 */

/*
MULTIPLE EVENTS ON SAME DAY

made 5 valid homework/business events on April 17th 2020
All event fields are a
saves and loads correctly
Can arrow through all 5 events correctly
Deletes all 5 events because they share name - unintended


Tried again with having each a unique name
Saves and loads correctly
And arrow through all 5 events, cycles through
On delete, deletes them 1 at a time like intended
Can delete all 5 correctly ans save and load after correctly
 */


/*
BUGS encountered in human testing
-javaFX refresh frames reload eachtime this makes it possible to click on applications behind the program when it runs.
-names can not be made up of ;'s or ,'s and spaces/whitespace counts as a character
-names can be made with nothing but spaces and still be valid
-Deletes all events that share the same name if they are on the same day - unintended


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
