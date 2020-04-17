
package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import projEvents.EventStorage;

import java.time.LocalDate;

/**************************************************************************************************
 * @author FredO
 * @version 1.0
 * MonthView is a display which will act as the home screen for the user. It displays the
 * individual days of the month and will have an indicator for each day that has an event saved.
 *************************************************************************************************/
public class MonthView {
    /**Holds the most current date*/
    private LocalDate date;

    /**The css code used to style the visuals of an idle button*/
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";

    /**The css code used to style the visuals of a button that is being hovered over*/
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: " +
            "-fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";

    /**The array that holds the number of days in the corresponding month. [0] == leap year*/
    private final int[] DAYS_IN_MONTH = {29, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**The array that holds the string values for each day of the week*/
    private final String [] DAYS_OF_THE_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};


    /**********************************************************************************************
     * MonthView's default constructor will set the date to the actual current date.
     *********************************************************************************************/
    public MonthView(){
        date = LocalDate.now();
    }

    /**********************************************************************************************
     * Created an overload of display to allow for the date to be customized.
     * @param date A LocalDate object representation of the desired date.
     *********************************************************************************************/
    public void display(LocalDate date){
        this.date = date;
        display();
    }

    /**********************************************************************************************
     * Display will display the current month to the user. It will also highlight the days with
     * events currently saved to the user.
     * Buttons include:
     *      "Current month" --> yearView
     *      "+" --> EventCreatorView
     *      ">" --> MonthView++
     *      "<" --> MonthView--
     *      Button for each day --> DayView
     *********************************************************************************************/
    public void display(){
        YearView yearView = new YearView();
        DayView dayView = new DayView();
        EventCreatorView eventCreatorView = new EventCreatorView();
        Stage monthView = new Stage();
        BorderPane layout = new BorderPane();
        GridPane dayBtnBox = new GridPane();
        dayBtnBox.setVgap(1);
        dayBtnBox.setHgap(1);

        HBox topBtns = new HBox();
        topBtns.setPadding(new Insets(5));

        HBox daysOfWeek = new HBox();

        //Creating the labels for the days of the week
        for(int i = 0; i < 7; i++){
            Label day = new Label();
            day.setText(DAYS_OF_THE_WEEK[i]);
            day.setAlignment(Pos.CENTER);
            day.setPrefSize(75, 25);
            daysOfWeek.getChildren().add(day);
        }

        //Month button  will display current month and year view when clicked

        Button monthBtn = new Button(date.getMonth().name());
        monthBtn.setPrefSize(160, 50);
        prettyButton(monthBtn);
        monthBtn.setStyle(IDLE_BUTTON_STYLE + "-fx-font-size:24");

        monthBtn.setOnMouseEntered(e ->
                monthBtn.setStyle(HOVERED_BUTTON_STYLE + "-fx-font-size:24"));

        monthBtn.setOnMouseExited(e ->
                monthBtn.setStyle(IDLE_BUTTON_STYLE + "-fx-font-size:24"));

        monthBtn.setOnAction(e -> {
            yearView.display(date);
            monthView.close();
        });

        //Add button will take user to event creator view
        Button addEventBtn = new Button(" + ");
        prettyButton(addEventBtn);
        addEventBtn.setOnAction(e -> eventCreatorView.display());

        //Next button will take user to the next month
        Button nextMonthBtn = new Button(" > ");
        prettyButton(nextMonthBtn);
        nextMonthBtn.setOnAction(e -> {
            monthView.close();
            if(date.getMonthValue() == 12){
                this.display(date.withYear(date.getYear() + 1).withMonth(1));
            } else {
                this.display(date.withMonth(date.getMonthValue() + 1));
            }
        });

        //Previous button will take user to the previous month
        Button prevMonthBtn = new Button(" < ");
        prettyButton(prevMonthBtn);
        prevMonthBtn.setOnAction(e -> {
            monthView.close();
            if(date.getMonthValue() == 1){
                this.display(date.withYear(date.getYear() - 1).withMonth(12));
            } else {
                this.display(date.withMonth(date.getMonthValue() - 1));
            }
        });

        topBtns.getChildren().addAll(monthBtn, prevMonthBtn, nextMonthBtn, addEventBtn);

        VBox top = new VBox(5, topBtns, daysOfWeek);
        layout.setTop(top);

        //An array to hold the buttons for each day in the month
        Button[][] dayButtons = new Button[6][7];


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
                    dayBtnBox.getChildren().add(dayButtons[i][j]);
                } else {
                    EventStorage events = EventStorage.getInstance();
                    dayButtons[i][j] = new Button("" + dayCnt);
                    Button days = dayButtons[i][j];
                    days.setBackground(Background.EMPTY);
                    if (events.GetListOfDay(date.withDayOfMonth(dayCnt)) != null){
                        days.setStyle("-fx-border-color: #ff0000;" +
                                "-fx-border-widty: 3px;");
                    }
                    days.setOnMouseEntered(e -> {
                        if(events.GetListOfDay(date.withDayOfMonth
                                (Integer.parseInt((days.getText())))) != null) {
                            days.setStyle(HOVERED_BUTTON_STYLE + "-fx-border-color: #ff0000;" +
                                    " -fx-border-widty: 5px;");
                        } else {
                            days.setStyle(HOVERED_BUTTON_STYLE);
                        }
                    });
                    days.setOnMouseExited(e -> {
                        if(events.GetListOfDay(date.withDayOfMonth
                                (Integer.parseInt((days.getText())))) != null) {
                            days.setStyle(IDLE_BUTTON_STYLE + "-fx-border-color: #ff0000; " +
                                    "-fx-border-widty: 5px;");
                        } else {
                            days.setStyle(IDLE_BUTTON_STYLE);
                        }
                    });

                    days.setOnAction(e -> dayView.display(date.withDayOfMonth(Integer.parseInt((days.getText())))));

                    days.setPrefSize(75, 60);
                    GridPane.setConstraints(days, j, i);
                    dayBtnBox.getChildren().add(days);
                    dayCnt++;
                }
            }
        }

        layout.setCenter(dayBtnBox);

        Scene scene = new Scene(layout, 530, 440);


        monthView.setTitle("" + date.getYear());
        monthView.setScene(scene);
        monthView.show();
    }

    /**********************************************************************************************
     * This is used to find the day of the week that a month begins on. I didn't create this
     * formula, it was found online and I reworked it to fit my needs.
     * @param month The month that you want the starting day of
     * @param year The year of the month that you want the starting day of
     * @return Returns an int that represents the day of the week a month starts
     *********************************************************************************************/
    private int dayMonthBegins(int month, int year) {
        int y = year - (14 - month) / 12;
        int x = y + y / 4 - y / 100 + y / 400;
        int m = month + 12 * ((14 - month) / 12) - 2;
        return (1 + x + (31*m) / 12) % 7;
    }

    /**********************************************************************************************
     * I just made this helper method to save some lines of code. Just sets the styles for the
     * buttons.
     * @param uglyButton The button you want to make pretty.
     *********************************************************************************************/
    private void prettyButton(Button uglyButton){
        uglyButton.setStyle(uglyButton.getStyle() + IDLE_BUTTON_STYLE);
        uglyButton.setOnMouseEntered(e -> uglyButton.setStyle(HOVERED_BUTTON_STYLE));
        uglyButton.setOnMouseExited(e -> uglyButton.setStyle(uglyButton.getStyle() + IDLE_BUTTON_STYLE));
    }
}
