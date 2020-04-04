package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projEvents.Errors;

/**
 * EventCreatorView will diplay a window based on the user's selected event type.
 * There is still a bunch of work to be done here, but the idea is to have a user
 * enter in the required info, then relay that info to main, which will in turn create
 * an event to be stored for later use.
 */
public class EventCreatorView {
    private static EventPackage dummy;

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
                    System.out.println("????????????????????????????????????");
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


        TextField eName = new TextField();
        eName.setPromptText("Name of Event");

        TextField eContact = new TextField();
        eContact.setPromptText("Event Contact Name");

        DatePicker eDate = new DatePicker();
        eContact.requestFocus();

        Button saveBtn = new Button("Save Event");
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> addEventStage.close());


        saveBtn.setOnAction(e -> {
            if(eName.getText().equals("")){
                Errors.setError("Need to enter a name for the event.");
                AlertView.display();
                return;
            } else if( eContact.getText().equals("")){
                Errors.setError("Missing contact name for the Event.");
                AlertView.display();
                return;
            } else if(eDate.getEditor().getText().equals("")){
                Errors.setError("Event Needs A valid date 'MM/DD,YYY'");
                AlertView.display();
                return;
            } else {
                dummy = new EventPackage();
                dummy.setEventName(eName.getText());
                dummy.setEventContactName(eContact.getText());
                dummy.setEventDate(eDate.getEditor().getText());
            }
            addEventStage.close();
        });

        layout.getChildren().addAll(eName, eContact, eDate, cancelBtn, saveBtn);

        addEventStage.setScene(new Scene(layout, 250, 250));
        addEventStage.showAndWait();
    }
}
