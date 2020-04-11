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
 * EventCreatorView will display a window based on the user's selected event type.
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

        eName.clear();
        eContact.clear();
        
        eTypeDropBx.setOnAction(e -> {
            switch(eTypeDropBx.getValue()) {
                case "Homework":
                    createHomework(addEventStage);
                    break;
                case "Business":
                    createBusiness(addEventStage);
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
                    EventStorage.getInstance().addEvent(newEvent);
                    stage.close();
                }
            }

        });


        cancelBtn.setOnAction(e -> stage.close());
        layout.getChildren().addAll(eName, classFor, eContact, eDate, turnInPlace, eDetails,
                saveBtn, cancelBtn);
        stage.setScene(new Scene(layout));
    }
    
    private void createBusiness(Stage stage){
        EventStorage events = EventStorage.getInstance();
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));

        TextField Location = new TextField();
        Location.setPromptText("Location of Meeting");

        TextField Duration = new TextField();
        Duration.setPromptText("Length of Meeting (Hours)");

        TextField eDetails = new TextField();
        eDetails.minHeight(Location.getHeight() * 2);
        eDetails.setPromptText("Details of the Meeting");

        eName.setPromptText("Name of Company");
        eContact.setPromptText("Name of Contact");
        eDate.setPromptText("Meeting Date");

        Button saveBtn = new Button("Save Event");
        saveBtn.setOnAction(e -> {
            double temp = 0.0;
            try {
                temp = Double.parseDouble(Duration.getText());
            }
            catch (Exception e1){
                Errors.setError("Please enter a number for the duration");
                AlertView.display();
            }
            Business newEvent = new Business(eName.getText(), eDetails.getText(),
                    LocalDate.parse(eDate.getEditor().getText(), dateBuilder.toFormatter()),
                    Location.getText(), temp);
            if(eDate.getEditor().getText().equals("")) {
                Errors.setError("Please select a date");     //This should probably be handled in the homework class
                AlertView.display();
            } else {

                if(Errors.getBool()){
                    AlertView.display();
                }else{
                    EventStorage.getInstance().addEvent(newEvent);
                    stage.close();
                }
            }

        });


        cancelBtn.setOnAction(e -> stage.close());
        layout.getChildren().addAll(eName, Location, eContact, eDate, Duration, eDetails, saveBtn, cancelBtn);
        stage.setScene(new Scene(layout));
    }
}
