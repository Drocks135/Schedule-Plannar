package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

/******************************************************************************
 * @author FredO
 * @version 1.0
 * The View which displays all of the months for the user to select from. Will
 * also be able to display an expanded view which will display all of the
 * months in a year.
 *****************************************************************************/
public class YearView {

    /**Holds the string for each month.*/
    private final String[] months = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    /**************************************************************************
     * Display will handle the window that displays the months of the year.
     *      Buttons include:
     *          One for each button -> month view of that month
     * @param date Holds the current year of the Scheduler.
     *************************************************************************/
    public void display(LocalDate date) {

        Stage yearView = new Stage();
        MonthView monthView = new MonthView();
        GridPane layout = new GridPane();
        layout.setVgap(5);
        layout.setHgap(5);
        layout.setPadding(new Insets(5));

        for (int i = 0; i < 12; i++) {
            int month = i;
            Button monthBtn = new Button(months[i]);
            monthBtn.setPrefSize(150, 45);
            monthBtn.setOnAction(e -> {
                monthView.display(date.withMonth(month + 1));
                yearView.close();
            });
            GridPane.setConstraints(monthBtn, (i % 3), (i / 3));
            layout.getChildren().add(monthBtn);
        }

        yearView.setTitle("" + date.getYear());
        yearView.setScene(new Scene(layout));
        yearView.show();

    }

}
