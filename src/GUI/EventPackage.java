package GUI;

public class EventPackage {

    private String eventName;
    private String eventDate;
    private String eventContactName;


    public  EventPackage() {
        eventName = null;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String date) {
        this.eventDate = date;
    }

    public String getEventContactName() {
        return eventContactName;
    }

    public void setEventContactName(String contactName) {
        this.eventContactName = contactName;
    }
}
