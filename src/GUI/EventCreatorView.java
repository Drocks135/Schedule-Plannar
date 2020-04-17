package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projEvents.*;

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
    private LocalDate date;
    private final DateTimeFormatterBuilder DATE_BUILDER = new DateTimeFormatterBuilder()
            .appendPattern("[M/d/yyyy]")
            .appendPattern("[MM/d/yyyy]")
            .appendPattern("[M/dd/yyyy]")
            .appendPattern("[MM/dd/yyyy]");

    /**************************************************************************************************
     * Default constructor will set the date for the date picker field to the current date.
     *************************************************************************************************/
    public EventCreatorView(){
        eDate = new DatePicker(LocalDate.now());
    }

    /**************************************************************************************************
     * This constructor overloads the default constructor and allows the date picker field to be set to
     * the day from which the user navigated from.
     * @param eDate: A local date object that holds the date of selected day
     *************************************************************************************************/
    public EventCreatorView(LocalDate eDate){
        this.date = eDate;
        this.eDate = new DatePicker(eDate);
    }

    /**************************************************************************************************
     *This overload of display will set the date picker to be autofilled with the selected date.
     * @param date: A local date object that holds the date of selected day
     *************************************************************************************************/
    public void display(LocalDate date){
        this.date = date;
        eDate = new DatePicker(date);
        display();
    }

    /**************************************************************************************************
     * This method will create a window, which will allow the user to edit all of the elements of the
     * selected event, including deleting said event.
     * @param originalEvent: A homework object that holds the event that will be edited.
     *************************************************************************************************/
    public void editHomework(Homework originalEvent){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        TextField turnInPlace = new TextField(originalEvent.getturnInPlace());

        TextField classFor = new TextField(originalEvent.getclassFor());

        TextArea eDetails = new TextArea(originalEvent.getDetails());
        eDetails.setWrapText(true);
        eDetails.setPrefColumnCount(1);

        TextField eName = new TextField(originalEvent.getName());

        eDate.setPromptText(originalEvent.getDue().toString());

        layout.getChildren().addAll(eName, classFor, eDate, turnInPlace, eDetails);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Homework newEvent = homeworkCheck(turnInPlace, classFor, eDetails, eName);
            if(newEvent != null){
                EventStorage.getInstance().DeleteEvent(date, originalEvent.getName());
                EventStorage.getInstance().addEvent(newEvent);
                stage.close();
            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());

        Button deleteBtn = deleteAction(originalEvent, stage);

        HBox bottomBtns = new HBox(10, saveBtn, deleteBtn, cancelBtn);

        layout.getChildren().add(bottomBtns);

        stage.setScene(new Scene(layout));
        stage.setMaxHeight(340);
        stage.showAndWait();
    }

    /**************************************************************************************************
     *This method will create a window, which will allow the user to edit all of the elements of the
     * selected event, including deleting said event.
     * @param originalEvent: A Business object that holds the event that will be edited.
     *************************************************************************************************/
    public void editBusiness(Business originalEvent){
        Stage stage = new Stage();

        TextField name = new TextField(originalEvent.getName());

        TextField location = new TextField(originalEvent.getLocation());

        TextArea details = new TextArea(originalEvent.getDetails());
        details.setWrapText(true);
        details.setPrefColumnCount(1);

        TextField duration = new TextField("" + originalEvent.getDuration());

        eDate.setPromptText(originalEvent.getDue().toString());

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Business newEvent = businessCheck(name, location, duration, details);
            if(newEvent != null){
                EventStorage.getInstance().DeleteEvent(date, originalEvent.getName());
                EventStorage.getInstance().addEvent(newEvent);
                stage.close();
            }
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());

        Button deleteBtn = deleteAction(originalEvent, stage);

        HBox bottomBtns = new HBox(10, saveBtn, deleteBtn, cancelBtn);

        VBox layout = new VBox(10, name, location, eDate, duration, details, bottomBtns );

        layout.setPadding(new Insets(10));
        stage.setScene(new Scene(layout));
        stage.setMaxHeight(340);
        stage.showAndWait();
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
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> addEventStage.close());

        layout.getChildren().addAll(eTypeDropBx ,cancelBtn);

        addEventStage.setScene(new Scene(layout));
        addEventStage.showAndWait();
    }


    /**************************************************************************************************
     * This method is where the event creation window in generated for a homework event.
     * @param stage: Takes in the stage from display to generate the new window for the user.
     *************************************************************************************************/
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

        eDate.setPromptText("Assignment due date");

        layout.getChildren().addAll(eName, classFor, eDate, turnInPlace, eDetails);

        HBox bottomBtns = new HBox();
        bottomBtns.setSpacing(50);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Homework newEvent = homeworkCheck(turnInPlace, classFor, eDetails, eName);
            if(newEvent != null){
                EventStorage.getInstance().addEvent(newEvent);
                stage.close();
            }
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());
        bottomBtns.getChildren().addAll(saveBtn, cancelBtn);
        layout.getChildren().add(bottomBtns);

        stage.setScene(new Scene(layout));
        stage.setMaxHeight(340);
    }

    /**************************************************************************************************
     *This method is where the event creation window in generated for a business event.
     * Sets all the views to the current stage
     * @param stage: Takes in the stage from display to generate the new window for the user.
     *************************************************************************************************/
    private void createBusiness(Stage stage){

        TextField Location = new TextField();
        Location.setPromptText("Location of Meeting");

        TextField Duration = new TextField();
        Duration.setPromptText("Length of Meeting (Hours)");

        TextArea eDetails = new TextArea();
        eDetails.setPromptText("Details of the Meeting");
        eDetails.setWrapText(true);
        eDetails.setPrefColumnCount(1);

        TextField eName = new TextField();
        eName.setPromptText("Name of Company");

        TextField eContact = new TextField();
        eContact.setPromptText("Name of Contact");

        TextField companyName = new TextField();
        companyName.setPromptText("Name of Company");

        HBox bottomBtns = new HBox();
        bottomBtns.setSpacing(50);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Business newEvent = businessCheck(companyName, Location, Duration, eDetails);
            if(newEvent != null) {
                EventStorage.getInstance().addEvent(newEvent);
                stage.close();
            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());
        bottomBtns.getChildren().addAll(saveBtn, cancelBtn);

        VBox layout = new VBox(10, eContact, Location, companyName, eDate, Duration, eDetails, bottomBtns);
        layout.setPadding(new Insets(10));
        stage.setScene(new Scene(layout));
        stage.setMaxHeight(340);
    }

    /**************************************************************************************************
     * This method was created to save some lines of coding. It takes in all of the event information a
     * user has generated and tries to create a new event object with it.
     * @param turnInPlace: Holds info about where to turn the assignment in.
     * @param classFor: Holds which class the homework is for.
     * @param eDetails: Holds the details of the homework event.
     * @param eName: Holds the name of the homework event.
     *************************************************************************************************/
    private Homework homeworkCheck(TextField turnInPlace, TextField classFor,
                                   TextArea eDetails, TextField eName) {
        Homework newEvent;
        if(eDate.getEditor().getText().equals("")) {
            Errors.setError("Please select a date");
            AlertView.display();
            return null;
        } else {
            newEvent = new Homework(eName.getText(), eDetails.getText(),
                    LocalDate.parse(eDate.getEditor().getText(), DATE_BUILDER.toFormatter()),
                    turnInPlace.getText(), classFor.getText());
            if(Errors.getBool()){
                AlertView.display();
                return null;
            }
        }
        return newEvent;
    }

    private Business businessCheck(TextField name, TextField location,
                                   TextField duration, TextArea details ){
        Business newEvent;
        double temp = 0.0;
        try {
            temp = Double.parseDouble(duration.getText());
        } catch (Exception e1) {
            Errors.setError("Please enter a number for the duration");
            AlertView.display();
            return null;
        }
        if (eDate.getEditor().getText().equals("")) {
            Errors.setError("Please select a date");
            AlertView.display();
            return null;
        } else {
            newEvent = new Business(name.getText(), details.getText(),
                    LocalDate.parse(eDate.getEditor().getText(), DATE_BUILDER.toFormatter()),
                    location.getText(), temp);
            if (Errors.getBool()) {
                AlertView.display();
                return null;
            }
        }
        return newEvent;
    }

    private Button deleteAction(Events event, Stage stage){
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            if(!AlertView.isWarned()){
                AlertView.display("Press Delete again if you're sure about deleting this event");
            }else {
                EventStorage.getInstance().DeleteEvent(date, event.getName());
                stage.close();
                AlertView.resetWarning();
            }
        });
        return deleteBtn;
    }
}
