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
import javafx.scene.effect.Glow;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class guestController{
    @FXML
    private FlowPane listView;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ArrayList<Guest> guestList = new ArrayList<Guest>();

    //Function: get the guest list and load up guest buttons
    public void initialize(){
        getGuestList();
        if(guestList.size() > 0) {
            for (int i = 0; i < guestList.size(); i++) {
                Guest currentGuest = guestList.get(i);
                int stayDays = currentGuest.getGuestStayDays();
                LocalDate startDate = currentGuest.getGuestStartDate();
                LocalDate endDate = startDate.plusDays(stayDays);
                List todayGuest = startDate.datesUntil(endDate).toList();

                if(todayGuest.contains(LocalDate.now())) {
                    Button button = new Button(currentGuest.getGuestFirstName() + " " + currentGuest.getGuestLastName());
                    button.setOnAction(e -> {
                        try {
                            Details(currentGuest, e);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    listView.getChildren().add(button);
                }
                else{
                    Label label = new Label("No guests for today");
                    label.setStyle("-fx-text-fill: white; -fx-font-size: 12pt;");
                    listView.getChildren().add(label);
                }
            }
        }
        else{
            Label label = new Label("No guests for today");
            label.setStyle("-fx-text-fill: white; -fx-font-size: 12pt;");
            listView.getChildren().add(label);
        }


    }

    //Function: to load up full details about the guest
    private void Details(Guest guest,ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("checkout.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        checkoutController controller = fxmlLoader.getController();
        controller.setLabels(guest,guestList);
        Stage stage = new Stage();
        stage.setTitle(guest.getGuestFirstName() + " " + guest.getGuestLastName());
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        goSomewhere(e, "guests.fxml","Guests");
    }


    //Function: for easy navigation
    public void goSomewhere(ActionEvent event, String fxml,String title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,1080,640);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    //Function: to return to dashboard
    public void goToHome(ActionEvent event) throws IOException{
        goSomewhere(event,"dash.fxml","Dashboard");
    }

    //Function: to load the latest guestlist and turn them into readable data before flushing to txt file
    public void setGuestList(Guest guest){
        guestList.add(guest);
        guestDetailsFiles x = new guestDetailsFiles();
        x.openFile();
        for (int i = 0; i < guestList.size(); i++){
            x.addRecords(guestList.get(i).getGuestFirstName(),guestList.get(i).getGuestLastName(),guestList.get(i).getGuestEmail(),guestList.get(i).getGuestIC(),guestList.get(i).getGuestContact(),guestList.get(i).getGuestRoom(),guestList.get(i).getGuestStayDays(),guestList.get(i).getGuestStartDate());
        }
            x.closeFormatter();
    }

    public void setGuestList(){
        guestDetailsFiles x = new guestDetailsFiles();
        x.openFile();
        for (int i = 0; i < guestList.size(); i++){
            x.addRecords(guestList.get(i).getGuestFirstName(),guestList.get(i).getGuestLastName(),guestList.get(i).getGuestEmail(),guestList.get(i).getGuestIC(),guestList.get(i).getGuestContact(),guestList.get(i).getGuestRoom(),guestList.get(i).getGuestStayDays(),guestList.get(i).getGuestStartDate());
        }
        x.closeFormatter();
    }



    //Function to get latest stored guestlist from txt file
    public ArrayList<Guest> getGuestList(){
        guestDetailsFiles x = new guestDetailsFiles();

        x.openExistingFile();
        guestList = x.readFile();
        x.closeScanner();
        return guestList;
    }

    //Function: to create a new guest
    public void createGuest(Guest newGuest){
        setGuestList(newGuest);

    }

}
