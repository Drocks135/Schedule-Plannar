package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * DayView will show the user the events corresponding to the day they select.
 * Will also give the user the option of creating a new event or edit saved
 * events.
 */
public class DayView {

    /**
     * Display will show the current events and allow for user to edit them.
     * Buttons include:
     *      ***********
     *      ***********
     *      ***********
     */
    public static void display() {
        Stage dayView = new Stage();
        dayView.setTitle(LocalDate.now().toString());

        Label dayLabel = new Label("" + LocalDate.now().getDayOfMonth());
        GridPane gPane = new GridPane();
        gPane.getChildren().add(dayLabel);

        dayView.setScene(new Scene(gPane, 200, 200));
        dayView.show();
    }

}

