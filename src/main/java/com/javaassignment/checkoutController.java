package com.javaassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class checkoutController {
    @FXML
    private Label guestNameLabel;
    @FXML
    private Label guestICLabel;
    @FXML
    private Label guestRoomNoLabel;
    @FXML
    private Label guestStayDaysLabel;
    @FXML
    private Label guestPrice;
    @FXML
    private Label guestSubtotalLabel;
    @FXML
    private Label guestTaxLabel;
    @FXML
    private Label guestTouristTaxLabel;
    @FXML
    private Label guestTotalLabel;
    private ArrayList<Guest> guestList = new ArrayList<Guest>();
    private Guest currentGuest;

    private ArrayList<ArrayList<LocalDate>> rooms = new ArrayList<ArrayList<LocalDate>>();

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void setLabels(Guest guest, ArrayList<Guest> guestlist){
        this.currentGuest = guest;
        this.guestList = guestlist;
        String guestName = guest.getGuestFirstName() + " " + guest.getGuestLastName();
        String guestIC = String.valueOf(guest.getGuestIC());
        String guestRoomNo = String.valueOf(guest.getGuestRoom());
        String guestStayDays = String.valueOf(guest.getGuestStayDays());
        String guestRoomPrice = String.valueOf(guest.getRoomCharges());
        String guestSubtotal = String.valueOf(guest.getSubtotal());
        String guestTax = String.valueOf(guest.getServiceTax());
        String guestTotalTax = String.valueOf(guest.getTotalOfServiceTax());
        String guestTouristTax = String.valueOf(guest.getTouristTax());
        String guestTotalTouristTax = String.valueOf(guest.getTotalofTourismTax());
        String guestTotal = String.valueOf(guest.totalCharges());


        guestNameLabel.setText(guestName);
        guestICLabel.setText(guestIC);
        guestRoomNoLabel.setText("Room "+guestRoomNo);
        guestStayDaysLabel.setText(guestStayDays + " days");
        guestPrice.setText("RM "+guestRoomPrice);
        guestSubtotalLabel.setText("RM "+guestSubtotal);
        guestTaxLabel.setText("RM "+guestTotalTax +" ( RM "+ guestSubtotal +" X "+ guestTax +" ) ");
        guestTouristTaxLabel.setText("RM "+ guestTotalTouristTax + " ( " + guestStayDays + " X RM " + guestTouristTax + " ) ");
        guestTotalLabel.setText("RM "+ guestTotal);

    }

    public void checkout(ActionEvent e) throws IOException {

        LocalDate startDate = currentGuest.getGuestStartDate();
        LocalDate endDate = startDate.plusDays(currentGuest.getGuestStayDays() - 1);

        //Check out early confirmation
        if (LocalDate.now().isBefore(endDate)){
            Boolean answer = ConfirmBox.display("Checkout Early","Are you sure you want to checkout early? The fees will still be the same.");
            if(answer){
                roomDetailsFiles y = new roomDetailsFiles();
                y.openExistingFile();
                rooms = y.readFile();
                y.closeScanner();

                //Function: Get the current guest's stay dates
                List<LocalDate> dates = currentGuest.getGuestStartDate().datesUntil(currentGuest.getGuestStartDate().plusDays(currentGuest.getGuestStayDays())).toList();

                //Function: Remove the stay dates
                for(int i = 0; i < dates.size(); i++){
                    rooms.get(currentGuest.getGuestRoom()-1).remove(dates.get(i));
                }
                y.openFile();
                y.addRecords(rooms);
                y.closeFormatter();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("guests.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                //Function: Get the all guests' information
                guestController controller = fxmlLoader.getController();
                controller.getGuestList();

                //Function: Remove the Guest
                guestList.remove(currentGuest);
                guestDetailsFiles x = new guestDetailsFiles();
                x.openFile();
                for (int i = 0; i < guestList.size(); i++){
                    x.addRecords(guestList.get(i).getGuestFirstName(),guestList.get(i).getGuestLastName(),guestList.get(i).getGuestEmail(),guestList.get(i).getGuestIC(),guestList.get(i).getGuestContact(),guestList.get(i).getGuestRoom(),guestList.get(i).getGuestStayDays(),guestList.get(i).getGuestStartDate());
                }
                x.closeFormatter();
                closeWindow(e);
            }

        }

        else if(LocalDate.now().isEqual(endDate)){
            roomDetailsFiles y = new roomDetailsFiles();
            y.openExistingFile();
            rooms = y.readFile();
            y.closeScanner();
            List<LocalDate> dates = currentGuest.getGuestStartDate().datesUntil(currentGuest.getGuestStartDate().plusDays(currentGuest.getGuestStayDays())).toList();
            for(int i = 0; i < dates.size(); i++){
                rooms.get(currentGuest.getGuestRoom()-1).remove(dates.get(i));
            }
            y.openFile();
            y.addRecords(rooms);
            y.closeFormatter();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("guests.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            guestController controller = fxmlLoader.getController();
            controller.getGuestList();
            guestList.remove(currentGuest);
            guestDetailsFiles x = new guestDetailsFiles();
            x.openFile();
            for (int i = 0; i < guestList.size(); i++){
                x.addRecords(guestList.get(i).getGuestFirstName(),guestList.get(i).getGuestLastName(),guestList.get(i).getGuestEmail(),guestList.get(i).getGuestIC(),guestList.get(i).getGuestContact(),guestList.get(i).getGuestRoom(),guestList.get(i).getGuestStayDays(),guestList.get(i).getGuestStartDate());
            }
            x.closeFormatter();
            closeWindow(e);
        }

    }

    private void goSomewhere(ActionEvent event, String fxml,String title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,800,600);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


    public void closeWindow(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("checkout.fxml"));
        Stage stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
