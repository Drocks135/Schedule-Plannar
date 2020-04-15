package projEvents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/***************************************************************************************************
 @author: Ayden Martin
 @Version: 1.0
  * Homework is an extension of the Events class, and thus has all of the Event class features
  * This means Homework by default has a Name,Detail,and Date variable
  * Homework also has object specific variables that no other event should have besides the Homework object
  * such as: TurnInPlace, ClassFor, GradeGot, GradeOut
  * These variables are to make the homework object be able to contain all the information a student should wish to know
  * about their assignment
 ****************************************************************************************************/
public class Homework extends Events {

    /** Homework objects turn in place */
    private String turnInPlace;
    /** What class this homework Object was made for */
    private String classFor;
    /** How many points user got on homework Object */
    private int gradeGot;
    /** What was the possible points homework object is out of */
    private int gradeOut;


    /**********************************************************************************************
     @paramname:
     The name of the homework assignment, extended from Events
     details: What is the homework assignment about, extended from Events
     due: When should this homework assignment be turned in, extended from Events
     turnInPlace: Where should this homework assignment be turned in
     classFor: What class is this homework assignment for
     @return none
     ***********************************************************************************************/
    public Homework(String name, String details, LocalDate due, String turnInPlace, String classFor) {
        setName(name);
        setDetails(details);
        setDue(due);
        setturnInPlace(turnInPlace);
        setclassFor(classFor);
    }

    /** Default constructor of Homework object */
    public Homework() {
    }

    /**********************************************************************************************
     @param
     name: The name of the homework assignment, extended from Events
     details: What is the homework assignment about, extended from Events
     due: When should this homework assignment be turned in, extended from Events
     turnInPlace: Where should this homework assignment be turned in
     classFor: What class is this homework assignment for
     gradeOut: How many points are possible to get in this homework assignment
     @return none
     ***********************************************************************************************/
    public Homework(String name, String details, LocalDate due, String turnInPlace, String classFor, int gradeOut) {
        setName(name);
        setDetails(details);
        setDue(due);
        setturnInPlace(turnInPlace);
        setclassFor(classFor);
        setgradeOut(gradeOut);
    }


    /**********************************************************************************************
     @param none
     @return turnInPlace: Where should this homework assignment be turned in
     ***********************************************************************************************/
    public String getturnInPlace() {
        return turnInPlace;
    }

    /**********************************************************************************************
     @param none
     @return classFor: What class is this homework assignment for
     ***********************************************************************************************/
    public String getclassFor() {
        return classFor;
    }

    /**********************************************************************************************
     @param none
     @return gradeGot: How many points the user got in this homework assignment
     ***********************************************************************************************/
    public int gradeGot() {
        return gradeGot;
    }

    /**********************************************************************************************
     @param none
     @return gradeOut: How many points are possible to get in this homework assignment
     ***********************************************************************************************/
    public int gradeOut() {

        return gradeOut;
    }

    /**********************************************************************************************
     @param turnInPlace: sets Where should this homework assignment be turned in
     @return none
     ***********************************************************************************************/
    public void setturnInPlace(String turnInPlace) {
        this.turnInPlace = turnInPlace;
    }

    /**********************************************************************************************
     @param classFor: sets What class is this homework assignment for
     @return none
      * Follows some basic error checking, to make sure the class isn't left blank or too long
     ***********************************************************************************************/
    public void setclassFor(String classFor) {
        Errors e = Errors.getInstance();
        if(classFor.length() == 0 || classFor.length() > 50){
            e.setError("enter valid class name");
        }
        else
            this.classFor = classFor;
    }
    /**********************************************************************************************
     @param gradeGot: Sets How many points the user got in this homework assignment
     @return none
      * Follows some basic error checking, Make sure you don't get over a 100% or below a 0%
     ***********************************************************************************************/
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

    /**********************************************************************************************
     @param gradeOut: Sets How many points are possible to get in this homework assignment
     @return none
      * Follows some basic error checking, Make sure you don't get over a 100% or below a 0%
     ***********************************************************************************************/
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
    /**********************************************************************************************
     @param
     @return none
     ***********************************************************************************************/
    public String toString() {
        String eventString = "";
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("ddMMyyyy");
        String date = this.getDue().format(ft);
        eventString += ("h," + this.getName() + "," + this.getDetails() + "," +
                date + "," + this.getturnInPlace() + "," + this.getclassFor() +
                "," + this.gradeGot() + "," + this.gradeOut() + ";");
        return eventString;
    }
}
