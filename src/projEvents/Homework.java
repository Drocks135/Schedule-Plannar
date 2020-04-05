package projEvents;
import java.time.LocalDate;
import java.util.Date;

/**
 @author: Ayden Martin
 @Version: 1.0
  * Homework is an extension of the Events class, and thus has all of the Event
  * class features. This means Homework by default has a Name, Detail, and Date
  * variable. Homework also has object specific variables that no other event
  * should have besides the Homework object such as: TurnInPlace, ClassFor,
  * GradeGot, GradeOut. These variables are to make the homework object be
  * able to contain all the information a student should wish to know about
  * their assignment.
 */
public class Homework extends Events {

    /** Homework objects turn in place. */
    String turnInPlace;

    /** What class this homework Object was made for. */
    String classFor;

    /** How many points user got on homework Object. */
    int gradeGot;

    /** What was the possible points homework object is out of. */
    int gradeOut;

    /**
     @param
     name: The name of the homework assignment, extended from Events
     details: What is the homework assignment about, extended from Events
     due: When should this homework assignment be turned in, extended from
     Events.
     turnInPlace: Where should this homework assignment be turned in
     classFor: What class is this homework assignment for
     */
    public Homework(String name, String details, LocalDate due, String turnInPlace,
                    String classFor) {
        setName(name);
        setDetails(details);
        setDue(due);
        setturnInPlace(turnInPlace);
        setclassFor(classFor);
    }

    /** Default constructor of Homework object. */
    public Homework() { }

    /**
     @param
     name: The name of the homework assignment, extended from Events
     details: What is the homework assignment about, extended from Events
     due: When should this homework assignment be turned in, extended from
     Events turnInPlace: Where should this homework assignment be turned in
     classFor: What class is this homework assignment for
     gradeOut: How many points are possible to get in this homework assignment
     */
    public Homework(String name, String details, LocalDate due, String turnInPlace,
                    String classFor, int gradeOut) {
        setName(name);
        setDetails(details);
        setDue(due);
        setturnInPlace(turnInPlace);
        setclassFor(classFor);
        setgradeOut(gradeOut);
    }


    /**
     @return turnInPlace: Where should this homework assignment be turned in.
     */
    public String getturnInPlace() {
        return turnInPlace;
    }
    /**
     @return classFor: What class is this homework assignment for
     */
    public String getclassFor() {
        return classFor;
    }
    /**
     @return gradeGot: How many points the user got in this homework assignment
     */
    public int gradeGot() {
        return gradeGot;
    }
    /**
     @return gradeOut: How many points are possible to get in this homework
     assignment.
     */
    public int gradeOut() {
        return gradeOut;
    }

    /**
     @param turnInPlace: sets Where should this homework assignment be turned
     in.
     */
    public void setturnInPlace(String turnInPlace) {
        this.turnInPlace = turnInPlace;
    }
    /**
     @param classFor: sets What class is this homework assignment for.
     */
    public void setclassFor(String classFor) {
        this.classFor = classFor;
    }
    /**
     @param gradeGot: Sets How many points the user got in this homework
     assignment.
     */
    public void setgradeGot(int gradeGot) {
        this.gradeGot = gradeGot;
    }
    /**
     @param gradeOut: Sets How many points are possible to get in this
     homework assignment.
     @return none
     */
    public void setgradeOut(int gradeOut) {
        this.gradeOut = gradeOut;
    }
}
