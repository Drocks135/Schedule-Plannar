package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import projEvents.EventStorage;
import projEvents.Events;

import javax.swing.text.Style;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.LinkedList;

/**************************************************************************************************
 * DayView will show the user the events corresponding to the day they select.
 * Will also give the user the option of creating a new event or edit saved
 * events.
 */
public class DayView {

    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: " +
            "-fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    private static int index = 0;

    public DayView(){
    }
    /**
     * Display will show the current events and allow for user to edit them.
     * Buttons include:
     *      ***********
     *      ***********
     *      ***********
     */
    public void display(LocalDate date) {
        EventCreatorView eventCreatorView = new EventCreatorView();

        LinkedList<Events> events = EventStorage.getInstance().GetListOfDay(date);

        Stage dayView = new Stage();

        Label prettyDay = new Label(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        prettyDay.setPadding(new Insets(5));
        Font dayFont = new Font(15);
        prettyDay.setFont(dayFont);
        VBox lilBox = new VBox();

        HBox topStuff = new HBox();

        Button nextEvent = new Button(">");
        nextEvent.setStyle("-fx-background-color: transparent;");
        nextEvent.setOnMouseEntered(e -> nextEvent.setStyle(HOVERED_BUTTON_STYLE));
        nextEvent.setOnMouseExited(e -> nextEvent.setStyle("-fx-background-color: transparent;"));

        Button prevEvent = new Button("<");
        prevEvent.setStyle("-fx-background-color: transparent;");
        prevEvent.setOnMouseEntered(e -> prevEvent.setStyle(HOVERED_BUTTON_STYLE));
        prevEvent.setOnMouseExited(e -> prevEvent.setStyle("-fx-background-color: transparent;"));

        if(events == null) {
            lilBox.getChildren().add(new Label("No Events Today"));
        } else {
            nextEvent.setOnAction(e -> {
                if (index + 1 <= events.size() - 1) {
                    index++;
                    showEvent(events, index, lilBox);
                } else {
                    index = 0;
                    showEvent(events, index, lilBox);
                }
            });
            prevEvent.setOnAction(e -> {
                if (index - 1 >= 0) {
                    index--;
                    showEvent(events, (index), lilBox);
                } else {
                    index = events.size() - 1;
                    showEvent(events, index, lilBox);
                }
            });
            showEvent(events, index, lilBox);
        }
        topStuff.getChildren().addAll(prettyDay, prevEvent, nextEvent);

        HBox bottomBtns = new HBox();
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> dayView.close());
        Button addBtn = new Button("Add Event");
        addBtn.setOnAction(e -> {
            dayView.close();
            eventCreatorView.display();
        });
        bottomBtns.getChildren().addAll(closeBtn, addBtn);

        VBox bigBox = new VBox();

        bigBox.getChildren().addAll(topStuff, lilBox, bottomBtns);
        dayView.setScene(new Scene(bigBox, 200, 200));
        dayView.show();
    }

    private void showEvent(LinkedList<Events> eventList, int index, VBox eventDisplay){
        eventDisplay.getChildren().clear();
        Events event = eventList.get(index);
        Label eName = new Label(event.getName());
        Label eDetail = new Label(event.getDetails());
        eDetail.setWrapText(true);

        eventDisplay.getChildren().addAll(eName, eDetail);
    }

}

