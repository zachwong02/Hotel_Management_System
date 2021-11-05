//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class bookingController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<Guest> bookingsTable;
    @FXML
    private TableColumn<Guest,String> firstNameColumn;
    @FXML
    private TableColumn<Guest,String> lastNameColumn;
    @FXML
    private TableColumn<Guest,String> emailColumn;
    @FXML
    private TableColumn<Guest,Long> icColumn;
    @FXML
    private TableColumn<Guest,Integer> daysColumn;
    @FXML
    private TableColumn<Guest,Integer> contactNoColumn;
    @FXML
    private TableColumn<Guest,Integer> roomColumn;
    @FXML
    private TableColumn<Guest,LocalDate> dateColumn;
    @FXML
    private TextField searchInput;


    //Function: to set up column fields with their properties and get the values
    public void initialize(){
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestFirstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestLastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("guestEmail"));
        icColumn.setCellValueFactory(new PropertyValueFactory<>("guestIC"));
        daysColumn.setCellValueFactory(new PropertyValueFactory<>("guestStayDays"));
        contactNoColumn.setCellValueFactory(new PropertyValueFactory<>("guestContact"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("guestRoom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("guestStartDate"));

        searchInput.setOnKeyPressed(e -> getGuestList(searchInput.getText()));

        bookingsTable.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2){

                if(bookingsTable.getSelectionModel().isEmpty() == false){
                    Guest guest = bookingsTable.getSelectionModel().getSelectedItem();

                    try {
                        loadEditBooking(e,guest);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }

        });

        getGuestList();

    }

    //Function: to get guest data from object and load them into the table
    public void getGuestList(){

        //Guest Controller
        guestController controller = new guestController();
        ArrayList<Guest> guestList = controller.getGuestList();
        ObservableList<Guest> guestItems = FXCollections.observableArrayList();
        for(int i=0; i< guestList.size();i++){
            String firstName = guestList.get(i).getGuestFirstName();
            String lastName = guestList.get(i).getGuestLastName();
            String email = guestList.get(i).getGuestEmail();
            long ic = guestList.get(i).getGuestIC();
            LocalDate date = guestList.get(i).getGuestStartDate();
            int guestStayDays = guestList.get(i).getGuestStayDays();
            int guestContact = guestList.get(i).getGuestContact();
            int guestRoom = guestList.get(i).getGuestRoom();
            guestItems.add(new Guest(firstName,lastName,email,ic,guestContact,guestRoom,guestStayDays,date));
        }
        bookingsTable.setItems(guestItems);

    }

    public void getGuestList(String search){

        //Guest Controller
        guestController controller = new guestController();
        ArrayList<Guest> guestList = controller.getGuestList();
        ObservableList<Guest> guestItems = FXCollections.observableArrayList();
        for(int i=0; i< guestList.size();i++){

            String firstName = guestList.get(i).getGuestFirstName();
            String lastName = guestList.get(i).getGuestLastName();
            String email = guestList.get(i).getGuestEmail();
            long ic = guestList.get(i).getGuestIC();
            LocalDate date = guestList.get(i).getGuestStartDate();
            int guestStayDays = guestList.get(i).getGuestStayDays();
            int guestContact = guestList.get(i).getGuestContact();
            int guestRoom = guestList.get(i).getGuestRoom();

            if (firstName.contains(search) || lastName.contains(search) || String.valueOf(guestContact).contains(search)) {
                guestItems.add(new Guest(firstName,lastName,email,ic,guestContact,guestRoom,guestStayDays,date));
            }
        }
        bookingsTable.setItems(guestItems);

    }


    //Function: to add items to the table
    public void addTableItem(Guest guest){
        bookingsTable.getItems().addAll(guest);
    }

    //Function: for easy navigation
    private void goSomewhere(ActionEvent event, String fxml,String title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,1080,640);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private void goSomewhere(MouseEvent event, String fxml,String title) throws IOException {
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


    //Function: to launch "New Booking" window
    public void loadAddBooking(ActionEvent e) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBooking.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New Booking");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            goSomewhere(e,"booking.fxml","Bookings");
        }

    public void loadEditBooking(MouseEvent e, Guest guest) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editBooking.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        //Open editBooking ui with guest info
        editBookingController controller = fxmlLoader.getController();
        controller.setItems(guest);

        Stage stage = new Stage();
        stage.setTitle("Booking Details");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        goSomewhere(e,"booking.fxml","Bookings");
    }



}
