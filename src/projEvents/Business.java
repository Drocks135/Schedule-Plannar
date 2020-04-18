package projEvents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/******************************************************************************
 @author: Justin Winn
 @Version: 1.0
  * Business is an extension of the Events class, and thus has all of the Event
  * class features By default, each business event has a Name,Detail,and Date.
  * It also has unique variables to the business type event. These are location
  * and duration. 
 ******************************************************************************/
public class Business extends Events{
    
    //* this variable holds a string to a location of a business event *//
    private String Location;
    
    //* this variable holds a double to hold a time duration of an event *//
    private double duration;//duration
    
/******************************************************************************
* This is the constructor for the business class. It sets all necessary variables
* to the parameters.
* @param name: The name of the business event
* @param details: Any extra details to the meeting
* @param due: This holds the date of the business meeting
* @param location: The location of the meeting
* @param duration: The duration of the meeting
 ******************************************************************************/
    public Business(String name, String details, LocalDate due, String location, double duration) {
        setName(name);
        setDetails(details);
        setDue(due);
        setLocation(location);
        setDuration(duration);
    }
/******************************************************************************
 * This is the basic constructor for the business class
 *****************************************************************************/
    public Business() {
    }

 /******************************************************************************
 * This function returns the location of a business meeting
 * @return Location: the location of the event
 *****************************************************************************/
    public String getLocation() {
        return Location;
    }

 /******************************************************************************
 * This function returns the duration of a business meeting
 * @return duration: the duration of the event
 *****************************************************************************/
    public double getDuration() {
        return duration;
    }

/******************************************************************************
 * This function sets the location of a business meeting
 * @param location: the location to be set of the event
 *****************************************************************************/
    public void setLocation(String location) {
        this.Location = location;
    }


/******************************************************************************
 * This function sets the duration of a business meeting. It makes sure it does
 * not exceed 24 hours or fall below 0 hours.
 * @param duration: the duration to be set of the event
 *****************************************************************************/    
    public void setDuration(double duration) {
        Errors e = Errors.getInstance();
        if (duration > 24)
            e.setError("Business events cannot last longer than 24 hours");
        else if (duration <= 0)
            e.setError("Duration of an event cannot be zero");
        else
            this.duration = duration;
    }

    
/******************************************************************************
 * This function prints the information of a business event
 *****************************************************************************/
    public String toString() {
        String eventString = "";
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("ddMMyyyy");
        String date = this.getDue().format(ft);
        eventString += ("b," + this.getName() + "," + this.getDetails() + "," +
                date + "," + this.getLocation() + "," + this.getDuration() + ";");
        return eventString;
    }

    @Override
    /**********************************************************************************************
     * Compares an Object to Business objects and returns if their members are equal
     * @param o: Any object to compare to a Business
     * @return returns true if every field in the objects are equal returns false else
     **********************************************************************************************/
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Events)) {
            return false;
        }

        Business c = (Business) o;

        return this.getName().equals(c.getName())
                && this.getDue().equals(c.getDue())
                && this.getDetails().equals(c.getDetails())
                && this.duration == c.getDuration()
                && this.Location.equals(c.Location);
    }
}
