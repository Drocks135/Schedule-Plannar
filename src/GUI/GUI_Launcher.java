package GUI;


import javafx.application.Application;
import javafx.stage.Stage;

public class GUI_Launcher extends Application {

    private MonthView monthView = new MonthView();

    public static void launchGUI(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        monthView.display();
    }

}
