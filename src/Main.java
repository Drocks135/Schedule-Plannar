
import GUI.MainGUI;
import projEvents.EventStorage;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        MainGUI gui = new MainGUI();
        gui.launchGUI(args);

        EventStorage eventStorage = new EventStorage();
    }

}
