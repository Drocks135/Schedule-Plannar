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

    private String turnInPlace;
    private String classFor;
    private int gradeGot;
    private int gradeOut;


    /**********************************************************************************************
     * Constructor for the homework object that sets all initial values except gradeOut
     * @param name: The name of the homework assignment, extended from Events
     * @param details: What is the homework assignment about, extended from Events
     * @param due: When should this homework assignment be turned in, extended from Events
     * @param turnInPlace: Where should this homework assignment be turned in
     * @param classFor: What class is this homework assignment for
     ***********************************************************************************************/
    public Homework(String name, String details, LocalDate due, String turnInPlace, String classFor) {
        setName(name);
        setDetails(details);
        setDue(due);
        setturnInPlace(turnInPlace);
        setclassFor(classFor);
    }

    /**********************************************************************************************
     * Default Constructor of the Homework object
     ***********************************************************************************************/
    public Homework() {
    }

    /**********************************************************************************************
     * Constructor for the homework object that sets all initial values
     * @param name: The name of the homework assignment, extended from Events
     * @param details: What is the homework assignment about, extended from Events
     * @param due: When should this homework assignment be turned in, extended from Events
     * @param turnInPlace: Where should this homework assignment be turned in
     * @param classFor: What class is this homework assignment for
     * @param gradeOut: How many points are possible to get in this homework assignment
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
     * Gets the place where the homework object needs to be turned in
     * @return turnInPlace: A string representing where this homework should be turned in at
     ***********************************************************************************************/
    public String getturnInPlace() {
        return turnInPlace;
    }

    /**********************************************************************************************
     * Gets the class that the homework object was assigned for
     * @return classFor: A string representing what class is this homework assignment for
     ***********************************************************************************************/
    public String getclassFor() {
        return classFor;
    }

    /**********************************************************************************************
     * Gets the grade amount that was achieved on the homework assignment
     * @return gradeGot: An integer representing what grade the user got on a homework assignment
     ***********************************************************************************************/
    public int gradeGot() {
        return gradeGot;
    }

    /**********************************************************************************************
     * Gets the point amount the homework assignment is out of
     * @return gradeOut: An integer representing the total amount of points available on an
     * assignment
     ***********************************************************************************************/
    public int gradeOut() {
        return gradeOut;
    }

    /**********************************************************************************************
     * Sets the place this object should be turned in at
     @param turnInPlace: A String that sets here should this homework assignment be turned in
     ***********************************************************************************************/
    public void setturnInPlace(String turnInPlace) {
        this.turnInPlace = turnInPlace;
    }

    /**********************************************************************************************
      * Sets the value of classFor which represents which class this homework assignment is for
      * @param classFor: A string representing what class the assignment is for
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
      * Sets the grade you received on a homework assignment
      * @param gradeGot: An integer representing the grade achieved on the assignment
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
     *  Sets the how many points a homework assignment is out of
      * @param gradeOut: An integer representing the total amounts of points on the assignment
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
     * Returns a string format of all the fields of homework seperated by commas and with a
     * semicolon at the end
     * @return String: A string format of Homework
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

    @Override
    /**********************************************************************************************
     * Compares an Object to Business objects and returns if their members are equal
     * @param o: Any object to compare to a Homework
     * @return returns true if every field in the objects are equal returns false else
     **********************************************************************************************/
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Events)) {
            return false;
        }

        Homework c = (Homework) o;

        return this.getName().equals(c.getName())
                && this.getDue().equals(c.getDue())
                && this.getDetails().equals(c.getDetails())
                && this.getclassFor().equals(c.getclassFor())
                && this.getturnInPlace().equals(c.getturnInPlace())
                && this.gradeGot == c.gradeGot
                && this.gradeOut == c.gradeOut;
    }
}
