package projEvents;

import java.util.Date;

public class Homework extends Events {
    private String turnInPlace;
    private String classFor;
    private int gradeGot;
    private int gradeOut;

    public Homework(String name, String details, Date due, String turnInPlace, String classFor) {
        setName(name);
        setDetails(details);
        setDue(due);
        setturnInPlace(turnInPlace);
        setclassFor(classFor);
    }

    public Homework() {
    }

    public Homework(String name, String details, Date due, String turnInPlace, String classFor, int gradeOut) {
        setName(name);
        setDetails(details);
        setDue(due);
        setturnInPlace(turnInPlace);
        setclassFor(classFor);
        setgradeOut(gradeOut);
    }


    /*
    Bunch of get methods to return attributes of homework specific events
    */
    public String getturnInPlace() {
        return turnInPlace;
    }

    public String getclassFor() {
        return classFor;
    }

    public int gradeGot() {
        return gradeGot;
    }

    public int gradeOut() {

        return gradeOut;
    }

    /*
    Bunch of get methods to set attributes of homework specific events
    */
    public void setturnInPlace(String turnInPlace) {
        this.turnInPlace = turnInPlace;
    }

    public void setclassFor(String classFor) {
        Errors e = Errors.getInstance();
        if(classFor.length() == 0 || classFor.length() > 50){
            e.setError("enter valid class name");
        }
        else
            this.classFor = classFor;
    }

    public void setgradeGot(int gradeGot) {
        Errors e = Errors.getInstance();
        if(gradeGot > this.gradeOut){
            e.setError("Dont tell me you got over 100% you scrub");
        }
        else if(gradeGot < 0){
            e.setError("Did you really get less than 0 on this assignment");
        }
        else
            this.gradeGot = gradeGot;
    }

    public void setgradeOut(int gradeOut) {
        Errors e = Errors.getInstance();
        if(gradeOut < this.gradeGot){
            e.setError("Dont tell me you got over 100% you scrub");
        }
        else if(gradeOut < 0){
            e.setError("Assignment cant be worth negative points");
        }
        else
            this.gradeOut = gradeOut;
    }
}
