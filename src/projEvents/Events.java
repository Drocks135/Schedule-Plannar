package projEvents;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**************************************************************************************************
 @author: Ayden Martin
 @Version: 1.0
  * Events is a class that will be the default object that is created for the schedule planner,
  * All other objects that are created for the calander will be an implementation of Events
  * Thus all calander objects will have a
  * Name, Details, and Date attached to them
 *************************************************************************************************/

public class Events {

    private String name=null;
    private String details=null;
    private LocalDate due=null;

    public Events() { }

    /**********************************************************************************************
     * @param
     * name: The name of the event
     * details: What is the event about
     * due: When is the vent happening
     * @return none
     *********************************************************************************************/
    public Events(String name, String details, LocalDate due) {
        setDetails(details);
        setName(name);
        setDue(due);
    }

    /*********************************************************************************************
     * @return due: This is when the Event is going to happen
     */
    public LocalDate getDue() {
        return this.due;
    }

    /********************************************************
     * @return The details of an event
     ********************************************************/
    public String getDetails() {
        return this.details;
    }

    /*****************************************************
     * @return Name: The name of an event
     *****************************************************/
    public String getName() {
        return this.name;
    }


    /***************************************************************
     * Sets the details value of an Event
     * @param details: A string describing the details of an event
     ***************************************************************/
    public void setDetails(String details) {
        Errors e = Errors.getInstance();
        if(details.length() > 250){
            e.setError("Event details cannot be over 250 characters long");
        }
        else
            this.details = details;
    }

    /**
     * Sets the date that an event will be put on the calendar
     * @param due: A LocalDate of the day an event will take place on
     */
    public void setDue(LocalDate due) {
        Errors e = Errors.getInstance();
        if(due.compareTo(LocalDate.now()) < 0){
            e.setError("Cannot set date to less than current date");
        }
        else
            this.due = due;
    }

    /**
     * Sets the name of an event
     * @param name: A String that is the name of the event
     */
    public void setName(String name) {
        Errors e = Errors.getInstance();
        if(name.length() > 50){
            e.setError("Event name cannot be over 50 characters long");
        }
        else if(name.length() == 0){
            e.setError("Event name cannot be blank");
        }
        else
            this.name = name;
    }

    /**
     * Converts an event to a string format
     * @return String: An event in the format of the string
     */
    public String toString() {
        String eventString = "";
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("ddMMyyyy");
        String date = this.getDue().format(ft);
        eventString += ("e," + this.getName() + "," + this.getDetails() + "," +
                date);
        return eventString;
    }
}
