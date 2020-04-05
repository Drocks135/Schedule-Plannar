package projEvents;


import java.time.LocalDate;


public class Events {

    private String name=null;
    private String details=null;
    private LocalDate due=null;

    public LocalDate getDue() {
        return this.due;
    }

    public String getDetails() {
        return this.details;
    }

    public String getName() {
        return this.name;
    }

    public void setDetails(String details) {
        Errors e = Errors.getInstance();
        if(details.length() > 250){
            e.setError("Event details cannot be over 250 characters long");
        }
        else
            this.details = details;
    }

    public void setDue(LocalDate due) {
        Errors e = Errors.getInstance();
        if(due.compareTo(LocalDate.now()) < 0){
            e.setError("Cannot set date to less than current date");
        }
        else
            this.due = due;
    }

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
}
