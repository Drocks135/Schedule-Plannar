package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projEvents.Business;
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
    private DatePicker eDate;
    private final DateTimeFormatterBuilder DATE_BUILDER = new DateTimeFormatterBuilder()
                .appendPattern("[M/d/yyyy]")
                .appendPattern("[MM/d/yyyy]")
                .appendPattern("[M/dd/yyyy]")
                .appendPattern("[MM/dd/yyyy]");

    public EventCreatorView(){
        eDate = new DatePicker(LocalDate.now());
    }

    public void display(LocalDate date){
        eDate = new DatePicker(date);
        display();
    }

    /**
     * This method will be called when the user chooses to create a new event.
     * Based on what the user selects, this method will then call the window,
     * which is where the user will be prompted for info about the new event.
     */
    public void display () {

        Stage addEventStage = new Stage();
        addEventStage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox();
        layout.setPadding(new Insets(5));
        layout.setSpacing(5);

        ComboBox<String> eTypeDropBx = new ComboBox<>();
        eTypeDropBx.setPromptText("Event Type");
        eTypeDropBx.getItems().addAll("Homework", "Business");
        
        eTypeDropBx.setOnAction(e -> {
            String s = eTypeDropBx.getValue();
            if ("Homework".equals(s)) {
                createHomework(addEventStage);
            } else if ("Business".equals(s)) {
                createBusiness(addEventStage);
            }
        });

        layout.getChildren().add(eTypeDropBx);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> addEventStage.close());

        layout.getChildren().add(cancelBtn);

        addEventStage.setScene(new Scene(layout));
        addEventStage.showAndWait();
    }


    private void createHomework(Stage stage){

        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        TextField turnInPlace = new TextField();
        turnInPlace.setPromptText("Where to turn in Assignment");

        TextField classFor = new TextField();
        classFor.setPromptText("Name of class");

        TextArea eDetails = new TextArea();
        eDetails.setPromptText("Details of the Assignment");
        eDetails.setWrapText(true);
        eDetails.setPrefColumnCount(1);

        TextField eName = new TextField();
        eName.setPromptText("Name of Assignment");

        TextField eContact = new TextField();
        eContact.setPromptText("Name of Professor");

        eDate.setPromptText("Assignment due date");

        layout.getChildren().addAll(eName, classFor, eContact, eDate, turnInPlace, eDetails);

        HBox bottomBtns = new HBox();
        bottomBtns.setSpacing(50);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());
        Button saveBtn = new Button("Save Event");
        saveBtn.setOnAction(e -> {
            if(eDate.getEditor().getText().equals("")) {
                Errors.setError("Please select a date");     //This should probably be handled in the homework class
                AlertView.display();
            } else {
                Homework newEvent = new Homework(eName.getText(), eDetails.getText(),
                        LocalDate.parse(eDate.getEditor().getText(), DATE_BUILDER.toFormatter()),
                        turnInPlace.getText(), classFor.getText());
                if(Errors.getBool()){
                    AlertView.display();
                }else{
                    EventStorage.getInstance().addEvent(newEvent);
                    stage.close();
                }
            }
        });
        bottomBtns.getChildren().addAll(saveBtn, cancelBtn);
        layout.getChildren().add(bottomBtns);

        stage.setScene(new Scene(layout));
        stage.setMaxHeight(340);
    }
    
    private void createBusiness(Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        TextField Location = new TextField();
        Location.setPromptText("Location of Meeting");

        TextField Duration = new TextField();
        Duration.setPromptText("Length of Meeting (Hours)");

        TextArea eDetails = new TextArea();
        eDetails.setPromptText("Details of the Meeting");
        eDetails.setWrapText(true);
        eDetails.setPrefColumnCount(1);

        TextField eContact = new TextField();
        eContact.setPromptText("Name of Contact");

        TextField companyName = new TextField();
        companyName.setPromptText("Name of Company");

        HBox bottomBtns = new HBox();
        bottomBtns.setSpacing(50);

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
            Business newEvent = new Business(eContact.getText(), eDetails.getText(),
                    LocalDate.parse(eDate.getEditor().getText(), DATE_BUILDER.toFormatter()),
                    Location.getText(), temp);
            if(eDate.getEditor().getText().equals("")) {
                Errors.setError("Please select a date");
            } else {

                if(Errors.getBool()){
                    AlertView.display();
                }else{
                    EventStorage.getInstance().addEvent(newEvent);
                    stage.close();
                }
            }

        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());
        bottomBtns.getChildren().addAll(saveBtn, cancelBtn);
        layout.getChildren().addAll(eContact, Location, companyName, eDate, Duration, eDetails, bottomBtns);
        stage.setScene(new Scene(layout));
        stage.setMaxHeight(340);
    }


}
