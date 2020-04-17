package GUI;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import projEvents.Errors;

/**************************************************************************************************
 * @author FredO
 * @version 1.0.3.9
 * AlertView is used to display error messages to the user. It's a simple popup window that the
 * user must click out of before proceeding to use the app. This ensures that the user can't miss
 * the alert being shown to them.
 *************************************************************************************************/
public class AlertView {

    /**Used to keep track of if a user has seen a warning yet*/
    private static boolean warned = false;

    /**********************************************************************************************
     * This display method is an overload of the default display method. It's used to create an
     * alert message that takes advantage of the warned boolean. It will notify the user of the
     * alert, then prompt them to repeat the action if they're sure about it. The second time
     * around, the alert will know that the user has been notified and allow the user to continue.
     * @param alert: This is the string that the AlertView will display to the user.
     *********************************************************************************************/
    public static void display(String alert){

        //Sets warned to true so the next time the user can proceed with their action
        warned = true;
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Warning");
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText(alert);
        Button closeBtn = new Button("Ok");
        closeBtn.setOnAction(e -> stage.close());

        VBox layout = new VBox(10,label, closeBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**********************************************************************************************
     * The default display message used to show the user an alert. Uses the current error message
     * held by the error class.
     *********************************************************************************************/
    public static void display() {
        Stage alert = new Stage();

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Input Error");
        alert.setMinWidth(250);

        Label label = new Label();
        label.setText(Errors.getError());
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> alert.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alert.setScene(scene);
        alert.showAndWait();
    }

    /**********************************************************************************************
     *Used to reset the warned boolean to false.
     *********************************************************************************************/
    public static void resetWarning(){warned = false;}

    /**********************************************************************************************
     *Used to see the current value of warned.
     * @return Returns a boolean that shows the current state of warned.
     *********************************************************************************************/
    public static boolean isWarned() {
        return warned;
    }
}