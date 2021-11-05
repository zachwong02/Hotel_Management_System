//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static Boolean answer;

    public static Boolean display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //block user interaction until current window is settled

        window.setTitle(title);

        window.setMinWidth(480);

        Label label = new Label();

        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            window.close();
            answer = false;
        });

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(yesButton,noButton);
        hbox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,hbox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,300,100);
        scene.getStylesheets().add(ConfirmBox.class.getResource("Popup.css").toExternalForm());
        window.setScene(scene);
        window.setOnCloseRequest(e -> answer = false);

        window.showAndWait();

        return answer;

    }
}
