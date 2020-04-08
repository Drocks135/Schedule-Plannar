package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projEvents.Errors;
import projEvents.EventStorage;
import projEvents.Homework;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;


/**
 * EventCreatorView will diplay a window based on the user's selected event type.
 * There is still a bunch of work to be done here, but the idea is to have a user
 * enter in the required info, then relay that info to main, which will in turn create
 * an event to be stored for later use.
 */
public class EventCreatorView {
    private TextField eName = new TextField();
    private TextField eContact = new TextField();
    private Button cancelBtn = new Button("Cancel");
    private DatePicker eDate = new DatePicker();
    private DateTimeFormatterBuilder dateBuilder = new DateTimeFormatterBuilder()
            .appendPattern("[M/d/yyyy]")
            .appendPattern("[MM/d/yyyy]")
            .appendPattern("[M/dd/yyyy]")
            .appendPattern("[MM/dd/yyyy]");
    /**
     * This method will be called when the user chooses to create a new event.
     * Based on what the user selects, this method will then call the window,
     * which is where the user will be prompted for info about the new event.
     */
    public void display () {

        Stage addEventStage = new Stage();
        addEventStage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));

        Text title = new Text("Create Event:");
        title.setFont(Font.font("", FontWeight.NORMAL, 20));

        ComboBox<String> eTypeDropBx = new ComboBox<>();
        eTypeDropBx.setPromptText("Event Type");
        eTypeDropBx.getItems().addAll("Homework", "Business", "Entertainment", "Custom");

        eTypeDropBx.setOnAction(e -> {
            switch(eTypeDropBx.getValue()) {
                case "Homework":
                    createHomework(addEventStage);
                    break;
                case "Business":
                    // code block
                    break;
                case "Entertainment":
                    // code block
                    break;
                case "Custom":
                    // code block
                    break;
                default:
                    // code block
            }
        });

        layout.getChildren().addAll(title, eTypeDropBx);

        cancelBtn.setOnAction(e -> addEventStage.close());

        layout.getChildren().add(cancelBtn);

        addEventStage.setScene(new Scene(layout));
        addEventStage.showAndWait();
    }

    private void createHomework(Stage stage){
        EventStorage events = new EventStorage();
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));

        TextField turnInPlace = new TextField();
        turnInPlace.setPromptText("Where to turn in Assignment");

        TextField classFor = new TextField();
        classFor.setPromptText("Name of class");

        TextField eDetails = new TextField();
        eDetails.minHeight(classFor.getHeight() * 2);
        eDetails.setPromptText("Details of the Assignment");

        eName.setPromptText("Name of Assignment");
        eContact.setPromptText("Name of Professor");
        eDate.setPromptText("Assignment due date");

        Button saveBtn = new Button("Save Event");
        saveBtn.setOnAction(e -> {

            if(eDate.getEditor().getText().equals("")) {
                Errors.setError("Please select a date");     //This should probably be handled in the homework class
                AlertView.display();
            } else {
                Homework newEvent = new Homework(eName.getText(), eDetails.getText(),
                        LocalDate.parse(eDate.getEditor().getText(), dateBuilder.toFormatter()),
                        turnInPlace.getText(), classFor.getText());
                if(Errors.getBool()){
                    AlertView.display();
                }else{
                    events.addEvent(newEvent.getDue(), newEvent);
                    System.out.println("Stuff did things");
                }
            }

        });


        cancelBtn.setOnAction(e -> stage.close());
        layout.getChildren().addAll(eName, classFor, eContact, eDate, turnInPlace, eDetails, saveBtn, cancelBtn);
        stage.setScene(new Scene(layout));
    }
}
