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
import projEvents.Homework;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * EventCreatorView will diplay a window based on the user's selected event type.
 * There is still a bunch of work to be done here, but the idea is to have a user
 * enter in the required info, then relay that info to main, which will in turn create
 * an event to be stored for later use.
 */
public class EventCreatorView {
    private static EventPackage dummy;
    TextField eName = new TextField();
    TextField eContact = new TextField();
    DatePicker eDate = new DatePicker();
    DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
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

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> addEventStage.close());

//        layout.getChildren().add(cancelBtn);

        addEventStage.setScene(new Scene(layout, 250, 250));
        addEventStage.showAndWait();
    }

    private void createHomework(Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));

        TextField turnInPlace = new TextField();
        turnInPlace.setPromptText("Where to turn in Assignment");

        TextField classFor = new TextField();
        classFor.setPromptText("Name of class");

        TextField eDetails = new TextField();
        eDetails.setPromptText("Details of the Assignment");

        eName.setPromptText("Name of Assignment");
        eContact.setPromptText("Name of Professor");
        eDate.setPromptText("Assignment due date");
        eDate.requestFocus();


        Button saveBtn = new Button("Save Event");
        saveBtn.setOnAction(e -> {
            if(eDate.getEditor().getText().equals("")) {
                Errors.setError("Please select a date");
                AlertView.display();
            } else {
                Homework newEvent = new Homework(eName.getText(), eDetails.getText(),
                        LocalDate.parse(eDate.getEditor().getText(), builder.toFormatter()), turnInPlace.getText(), classFor.getText());
            }
            if(!Errors.getBool()){
                //save the new event, however it is one would do that
            }else{
                AlertView.display();
            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());

        layout.getChildren().addAll(eName, classFor, eContact, eDate, turnInPlace, saveBtn, cancelBtn);

        stage.setScene(new Scene(layout, 250, 250));
    }

    private void inputChecks(){

    }



}
