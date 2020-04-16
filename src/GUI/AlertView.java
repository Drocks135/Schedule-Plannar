package GUI;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import projEvents.Errors;

public class AlertView {

    private static boolean warned = false;

    public static void display(String alert){
        warned = true;
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Warning");
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText(alert);
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> stage.close());

        VBox layout = new VBox(10,label, closeBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
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

    public static void resetWarning(){warned = false;}

    public static boolean isWarned() {
        return warned;
    }
}