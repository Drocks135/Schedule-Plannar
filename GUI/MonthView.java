
package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * MonthView is a display which will act as the home screen for the user. It displays the individual
 * days of the month and will have an indicator for each event on any given day.
 */
public class MonthView {

    private LocalDate date;
    private Button[][] dayButtons;
    private Button monthBtn;
    private Button nextMonthBtn;
    private Button prevMonthBtn;
    private Button addEventBtn;
    private final int[] DAYS_IN_MONTH = {29, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final String [] DAYS_OF_THE_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};


    /**
     * MonthView will default to the current month.
     */
    public MonthView(){
        date = LocalDate.now();
    }

    /**
     * Created an overload of display to allow for the month to be changed.
     * @param date A LocalDate object representation of the desired month.
     */
    public void display(LocalDate date){
        this.date = date;
        display();
    }

    /**
     * Display will deplay the current month to the user.
     * Buttons include:
     *      "Current month" --> yearView
     *      "+" --> EventCreatorView
     *      ">" --> MonthView++
     *      "<" --> MonthView--
     *      Button for each day --> DayView
     */
    public void display(){
        YearView yearView = new YearView();
        EventCreatorView eventCreatorView = new EventCreatorView();

        Stage monthView = new Stage();
        BorderPane layout = new BorderPane();
        GridPane dayBtns = new GridPane();
        HBox topBtns = new HBox();
        HBox daysOfWeek = new HBox();

        //Creating the labels for the days of the week
        for(int i = 0; i < 7; i++){
            Label day = new Label();
            day.setText(DAYS_OF_THE_WEEK[i]);
            day.setTextAlignment(TextAlignment.CENTER);
            day.setPrefSize(65, 25);
            daysOfWeek.getChildren().add(day);
        }

        //Month button  will display current month and year view when clicked
        monthBtn = new Button(date.getMonth().toString());
        monthBtn.setOnAction(e -> {
            yearView.display(date);
            monthView.close();
        });

        //Add button will take user to event creator view
        addEventBtn = new Button(" + ");
        addEventBtn.setOnAction(e -> eventCreatorView.display());

        //Next button will take user to the next month
        nextMonthBtn = new Button(" > ");
        nextMonthBtn.setOnAction(e -> {
            monthView.close();
            if(date.getMonthValue() == 12){
                this.display(date.withYear(date.getYear() + 1).withMonth(1));
            } else {
                this.display(date.withMonth(date.getMonthValue() + 1));
            }
        });

        //Previous button will take user to the previous month
        prevMonthBtn = new Button(" < ");
        prevMonthBtn.setOnAction(e -> {
            monthView.close();
            if(date.getMonthValue() == 1){
                this.display(date.withYear(date.getYear() - 1).withMonth(12));
            } else {
                this.display(date.withMonth(date.getMonthValue() - 1));
            }
        });

        topBtns.getChildren().addAll(monthBtn, prevMonthBtn, nextMonthBtn, addEventBtn);

        VBox top = new VBox();
        top.getChildren().addAll(topBtns, daysOfWeek);
        layout.setTop(top);

        //An array to hold the buttons for each day in the month
        dayButtons = new Button[6][7];

        //Day will represent the day of the week the month starts on
        int day = dayMonthBegins(date.getMonthValue(), date.getYear());
        int dayCnt = 1;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if(dayCnt > DAYS_IN_MONTH[date.getMonthValue()]){
                    break;
                }
                if(i == 0 && j < day){
                    dayButtons[i][j] = new Button("");
                    dayButtons[i][j].setVisible(false);
                    GridPane.setConstraints(dayButtons[i][j], j, i);
                    dayBtns.getChildren().add(dayButtons[i][j]);
                } else {
                    dayButtons[i][j] = new Button("" + dayCnt);
                    Button days = dayButtons[i][j];
                    days.setOnAction(e -> DayView.display());
                    days.setPrefSize(65, 60);
                    GridPane.setConstraints(days, j, i);
                    dayBtns.getChildren().add(days);
                    dayCnt++;
                }
            }
        }
        layout.setCenter(dayBtns);

        Scene scene = new Scene(layout);

        monthView.setTitle("Scheduling App");
        monthView.setScene(scene);
        monthView.show();
    }

    private int dayMonthBegins(int month, int year) {
        int y = year - (14 - month) / 12;
        int x = y + y / 4 - y / 100 + y / 400;
        int m = month + 12 * ((14 - month) / 12) - 2;
        return (1 + x + (31*m) / 12) % 7;
    }
}
