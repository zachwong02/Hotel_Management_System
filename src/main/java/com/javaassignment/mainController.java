//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class  mainController {

    @FXML
    private TextField userInput;
    @FXML
    private PasswordField passInput;

    @FXML
    private ListView<String> listView;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Function: authentication of user (1 user as there is no benefit of having multiple users)
    public void authenticate(ActionEvent event) throws Exception {

        if (userInput.getText().isBlank() && passInput.getText().isBlank()){
            MessageBox.display("Invalid Credentials","Please enter your username and password");
        }

        else if(userInput.getText().isBlank()){
            MessageBox.display("Invalid Credentials","Please enter your username");
        }

        else if(passInput.getText().isBlank()){
            MessageBox.display("Invalid Credentials","Please enter your password");
        }

        else if(passInput.getText().equals("staff") && userInput.getText().equals("staff")){
            loggedIn(event);
        }

        else{
            MessageBox.display("Invalid Credentials","The username or password is incorrect. \nPlease try again");
        }

    }


    //Function: for easy navigation
    private void goSomewhere(ActionEvent event, String fxml, String title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();

        //On closing the application, a confirmation message box will appear to confirm the user's decision

        stage.setOnCloseRequest(e -> {
            Boolean answer = ConfirmBox.display("Close Application", "Are you sure you want to close the application?");
            if (answer == true){
                stage.close();
            }

            else if(answer == false){
                e.consume();
            }
        });
        scene = new Scene(root,1080,640);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    //Function: to load user dashboard

    private void loggedIn(ActionEvent event) throws IOException {
        goSomewhere(event,"dash.fxml","Dashboard");
    }

    //Function: confirmation to log out from system

    public void logOut(ActionEvent event) throws IOException {

        Boolean answer = ConfirmBox.display("Logout","Do you want to log out?");

        if (answer == true){
            goSomewhere(event,"login.fxml","Login");
        }


    }

    //System directories

    public void goToRooms(ActionEvent event) throws IOException {
        goSomewhere(event,"room.fxml","Room Availability");
    }
    public void goToBookings(ActionEvent event) throws IOException{
        goSomewhere(event,"booking.fxml","Bookings");
    }
    public void goToGuests(ActionEvent event) throws IOException{
        goSomewhere(event,"guests.fxml","Guests");
    }



}

