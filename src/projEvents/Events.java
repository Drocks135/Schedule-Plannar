package projEvents;
import java.time.LocalDate;

/**
 @author: Ayden Martin
 @Version: 1.0
  * Events is a class that will be the default object that is created for the
  * schedule planner, All other objects that are created for the calander will
  * be an implementation of Events. Thus all calander objects will have a
  * Name, Details, and Date attached to them.
 */


public class Events {

    /** name: The name of the Event. */
    String name;
    /** details: Details of this Event. */
    String details;
    /** due: When will this Event occur. */
    LocalDate due;

    /**
     @param
     name: The name of the event
     details: What is the event about
     due: When is the vent happening
     */
    public Events(String name, String details, LocalDate due) {
        setDetails(details);
        setName(name);
        setDue(due);
    }

    /** Default constructor of Events. */
    public Events() { }

    /**
     @return due: This is when the Event is going to happen
     */
    public LocalDate getDue() {
        return this.due;
    }

    /**
     @return details: This desscribes what the Event is about
     */
    public String getDetails() {
        return this.details;
    }

    /**
     @return turnInPlace: Where should this homework assignment be turned in
     */
    public String getName() {
        return this.name;
    }

    /**
     @param details: Sets the details of this Event
     @return none
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     @param due: Sets when this variable will occur
     @return none
     */
    public void setDue(LocalDate due) {
        this.due = due;
    }

    /**
     @param name: Sets what this Event is called
     @return none
     */
    public void setName(String name) {
        this.name = name;
    }
}
