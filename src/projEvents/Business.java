package projEvents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Business extends Events{
    private String Location;
    private double duration;//duration

    public Business(String name, String details, LocalDate due, String location, double duration) {
        setName(name);
        setDetails(details);
        setDue(due);
        setLocation(location);
        setDuration(duration);
    }

    /*
    Bunch of get methods to return attributes of homework specific events
    */
    public Business() {
    }

    public String getLocation() {
        return Location;
    }

    public double getDuration() {
        return duration;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public void setDuration(double duration) {
        Errors e = Errors.getInstance();
        if (duration > 24)
            e.setError("Business events cannot last longer than 24 hours");
        else if (duration <= 0)
            e.setError("Duration of an event cannot be zero");
        else
            this.duration = duration;
    }

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
