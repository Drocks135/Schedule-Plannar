import GUI.GUI_Launcher;
import projEvents.EventStorage;

/******************************************************************************
 @author: Ayden Martin
 @Version: 1.0
  * Main is the class that runs all the code
 *****************************************************************************/
public class Main {

    /**************************************************************************
     * Instantiates the GUI and the list of events and runs the program.
     * @param args
     *************************************************************************/
    public static void main(String[] args) {
        GUI_Launcher gui = new GUI_Launcher();
        EventStorage eventStorage = EventStorage.getInstance();

        gui.launchGUI(args);


    }
}
