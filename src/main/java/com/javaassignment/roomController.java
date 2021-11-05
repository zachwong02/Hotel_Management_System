//Author: Wong Zhi Zhen
//TP number: TP058282
//Date Created: 14 October 2021

package com.javaassignment;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class roomController {
    @FXML
    private TableView<RoomsAvailable> roomTable;

    @FXML
    private TableColumn<RoomsAvailable,Integer> roomColumn;
    @FXML
    private TableColumn<RoomsAvailable,String> availabilityColumn;
    @FXML
    private DatePicker datePicker;


    private Stage stage;
    private Scene scene;
    private Parent root;
    private ArrayList<ArrayList<LocalDate>> rooms = new ArrayList<ArrayList<LocalDate>>();


    //Function:
    //initialize by getting the latest room availability from txt file
    //making UI convenient
    //initialization of columns

    public void initialize() {

        getRoomList();

        for(int i = 0; i < 20; i++){
            rooms.add(new ArrayList<LocalDate>());
        }
        rooms.get(0).add(LocalDate.now());

        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(e -> searchAvailable());
        searchAvailable();
    }


    //Function: to find available rooms on the day selected
    public void searchAvailable(){

        //load latest room availability

        getRoomList();
        //clear the table
        roomTable.getItems().clear();


        ObservableList<RoomsAvailable> availableRoom = FXCollections.observableArrayList();

        LocalDate currentDate = datePicker.getValue();

        for (int i = 0; i< 20;i++) {
            ArrayList<LocalDate> currentRoom = rooms.get(i);

            if (currentRoom.contains(currentDate)) { //if there is a date in the room's array

                availableRoom.add(new RoomsAvailable(i + 1, currentDate)); //list it as unavailable

            } else {

                availableRoom.add(new RoomsAvailable(i + 1)); //else make it available
            }
        }

        roomTable.setItems(availableRoom); //set the array to the table to display


            roomTable.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) { //if user double clicks the record


                    //get a model to retrieve data from record selected
                    RoomsAvailable available_room = roomTable.getSelectionModel().getSelectedItem();

                    if(available_room.getAvailable() != "Available"){

                        MessageBox.display("Room Taken","The selected room is unavailable");

                    }

                    else { //launch "New Booking" window
                        try {
                            loadAddBooking(e,available_room.getRoomNo(),datePicker.getValue());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            });


    }

    //Function: load latest room availability from txt file

    public void getRoomList(){
        roomDetailsFiles x = new roomDetailsFiles();
        x.openExistingFile();
        rooms = x.readFile();
        x.closeScanner();
    }

    //Function: to add date to the room's array and write the rooms multidimensional array to files
    public void addRoomBooking(int room, LocalDate date, int stayDays){
        getRoomList();
        List<LocalDate> dates = date.datesUntil(date.plusDays(stayDays)).toList();

        for (int i = 0; i < dates.size();i++){
            rooms.get(room-1).add(dates.get(i));
        }

        roomDetailsFiles x = new roomDetailsFiles();
        x.openFile();
        x.addRecords(rooms);
        x.closeFormatter();
    }

    //Function: for easy navigation
    private void goSomewhere(ActionEvent event, String fxml, String title) throws IOException {

        root = FXMLLoader.load(getClass().getResource(fxml));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,1080,640);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private void goSomewhere(MouseEvent event, String fxml, String title) throws IOException {

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

    public void loadAddBooking(MouseEvent e, int roomNo, LocalDate date) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBooking.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        addBookingController controller = fxmlLoader.getController();
        controller.setRooms(roomNo,date);

        Stage stage = new Stage();
        stage.setTitle("New Booking");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        goSomewhere(e,"room.fxml","Room Availability");
    }

}
