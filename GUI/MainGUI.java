package GUI;


import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private MonthView monthView = new MonthView();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        monthView.display();
    }
}