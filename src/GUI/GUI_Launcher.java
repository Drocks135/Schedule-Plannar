package GUI;


import javafx.application.Application;
import javafx.stage.Stage;

/******************************************************************************
 * Launches the GUI application starts with the MonthView set to current month.
 *****************************************************************************/
public class GUI_Launcher extends Application {

    /**This is the used to start the application*/
    private MonthView monthView = new MonthView();

    /**************************************************************************
     * This is required for javafx, but I'm not sure of the finer details
     * involved.
     * @param args where be my treasures
     *************************************************************************/
    public static void launchGUI(String[] args){
        launch(args);
    }

    /**************************************************************************
     * Calls the display function of monthView to launch the application.
     * @param stage The stage that will hold the monthView
     *************************************************************************/
    @Override
    public void start(Stage stage) {
        monthView.display();
    }

}
