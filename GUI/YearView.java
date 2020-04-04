package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * The View which displays all of the months for the user to select from. Will also be
 * able to diplay an expanded view which will display a selection of past and furure
 * years.
 */
public class YearView{
    /**
     * Holds the string for each month
     */
    private final String[] MONTHS = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    /**
     * Default constructor is empty
     */
    public YearView(){ }

    /**
     * Display will handle the window that displays the months of the year.
     *      Buttons include:
     *          One for each button -> month view of that month
     * @param date Holds the current year of the Scheduler.
     */
    public void display(LocalDate date) {

        MonthView monthView = new MonthView();
        GridPane layout = new GridPane();
        Stage yearView = new Stage();
        for(int i = 0; i < 12; i++){
            int month = i;
            Button monthBtn = new Button(MONTHS[i]);
            monthBtn.setPrefSize(150, 50);
            monthBtn.setOnAction(e -> {
                monthView.display(date.withMonth(month + 1));
                yearView.close();
            });
            GridPane.setConstraints(monthBtn,(i % 3), (i / 3));
            layout.getChildren().add(monthBtn);
        }

        yearView.setTitle("" + date.getYear());
        yearView.setScene(new Scene(layout));
        yearView.show();

    }

}
