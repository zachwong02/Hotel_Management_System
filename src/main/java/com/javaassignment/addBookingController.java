//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import com.dlsc.formsfx.model.validators.ValidationResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class addBookingController {
    @FXML
    private TextField guestFirstNameInput;
    @FXML
    private TextField guestLastNameInput;
    @FXML
    private TextField guestEmailInput;
    @FXML
    private TextField guestICInput;
    @FXML
    private TextField guestContactInput;
    @FXML
    private ComboBox<Integer> guestRoomInput;
    @FXML
    private Spinner<Integer> guestStayDaysInput;
    @FXML
    private DatePicker guestStartDateInput;

    private ArrayList<ArrayList<LocalDate>> rooms = new ArrayList<ArrayList<LocalDate>>();


    //Function: to add elements to "New Booking" window for user convenience
    public void initialize(){
        SpinnerValueFactory<Integer> guestStayDaysValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,365,1);
        guestStayDaysInput.setValueFactory(guestStayDaysValues);
        setRooms();
    }

    public void setRooms(){
        for(int i = 1; i <= 20; i++) {
            guestRoomInput.getItems().add(i);
        }
        guestStartDateInput.setValue(LocalDate.now());
    }

    public void setRooms(int room,LocalDate date){

        for(int i = 1; i <= 20; i++) {
            guestRoomInput.getItems().add(i);
        }
        guestStartDateInput.setValue(date);
        guestRoomInput.setValue(room);
    }



    public void addBooking(ActionEvent e) throws IOException {

        boolean status;
        status = validation();

        if(status == true){
            // Guest Controller
            //Function: to create a new guest object
            FXMLLoader loader = new FXMLLoader(getClass().getResource("guests.fxml"));
            Parent root = loader.load();
            guestController controller = loader.getController();
            Guest newGuest = new Guest(guestFirstNameInput.getText(), guestLastNameInput.getText(),guestEmailInput.getText(), Long.parseLong(guestICInput.getText()), Integer.parseInt(guestContactInput.getText()), guestRoomInput.getValue(), guestStayDaysInput.getValue(), guestStartDateInput.getValue());
            controller.createGuest(newGuest);


            //Booking Controller
            //Function: to update table
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("booking.fxml"));
            Parent root2 = loader2.load();
            bookingController controller2 = loader2.getController();
            controller2.addTableItem(new Guest(guestFirstNameInput.getText(), guestLastNameInput.getText(),guestEmailInput.getText(), Long.parseLong(guestICInput.getText()), Integer.parseInt(guestContactInput.getText()), guestRoomInput.getValue(), guestStayDaysInput.getValue(), guestStartDateInput.getValue()));


            //Rooms Controller
            //Function: to update room availability
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("room.fxml"));
            Parent root3 = loader3.load();
            roomController controller3 = loader3.getController();
            controller3.addRoomBooking(guestRoomInput.getValue(),guestStartDateInput.getValue(),guestStayDaysInput.getValue());
            controller3.searchAvailable();
            closeWindow(e);
        }





    }

    //Function: to close "New Booking" window
    public void closeWindow(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addBooking.fxml"));
        Stage stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }

    public boolean validation() throws IOException {

        ArrayList<Boolean> errors = new ArrayList<Boolean>();
        boolean status;

        //Check Numbers or Blank inside of First Name

        if(guestFirstNameInput.getText().isBlank()){
            guestFirstNameInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }

        else{
            char[] firstNameChar = guestFirstNameInput.getText().toCharArray();

            for(char c: firstNameChar){
                if(Character.isDigit(c)){
                    guestFirstNameInput.setStyle("-fx-border-color: red;");
                    errors.add(false);
                    break;
                }
            }

        }


        //Check Numbers or Blank inside of Last Name

        if(guestLastNameInput.getText().isBlank()){
            guestLastNameInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }

        else {
            char[] lastNameChar = guestLastNameInput.getText().toCharArray();

            for(char c: lastNameChar){
                if(Character.isDigit(c)){
                    guestLastNameInput.setStyle("-fx-border-color: red;");
                    errors.add(false);
                    break;
                }

            }

        }

        //Check for Strings in Contact Field
        try {
            Integer.parseInt(guestContactInput.getText());
        }

        catch (NumberFormatException e){
            guestContactInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }

        //Check for Strings in IC Field

        try {
            Long.parseLong(guestICInput.getText());
        }

        catch (NumberFormatException e){
            guestICInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }


        //Check Duration of Stay
        if(guestStayDaysInput.getValue() < 1){
            guestStayDaysInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }


        //Check Date Booking
        if(guestStartDateInput.getValue().isBefore(LocalDate.now())){
            guestStartDateInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }

        //Check Blank Email
        if(guestEmailInput.getText().isBlank()){
            guestEmailInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }

        //Check Blank Room No
        if(guestRoomInput.getSelectionModel().isEmpty()){
            guestRoomInput.setStyle("-fx-border-color: red;");
            errors.add(false);
        }

        //Check Availability

        roomDetailsFiles y = new roomDetailsFiles();
        y.openExistingFile();
        rooms = y.readFile();
        y.closeScanner();

        LocalDate startDate = guestStartDateInput.getValue();
        LocalDate endDate = startDate.plusDays(guestStayDaysInput.getValue());
        ArrayList<LocalDate> badDates = new ArrayList<>();
        List<LocalDate> datesSelected = startDate.datesUntil(endDate).toList();

        if(guestRoomInput.getSelectionModel().isEmpty() == false) {
            for (int i = 0; i < datesSelected.size(); i++) {
                if (rooms.get(guestRoomInput.getValue() - 1).contains(datesSelected.get(i))) {
                    badDates.add(datesSelected.get(i));
                }
            }
        }

        if(badDates.size() > 0){
            MessageBox.display("Date Unavailable","These dates are unavailable for booking\n" + String.valueOf(badDates));
            errors.add(false);
        }



        if(errors.contains(false)){
            status = false;
        }

        else{
            status = true;
        }


        return status;
    }


}
