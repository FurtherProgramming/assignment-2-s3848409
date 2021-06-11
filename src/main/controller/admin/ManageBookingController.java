package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.admin.AdminViewBookingModel;
import main.object.BookingObject;
import main.session.BookingSession;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {
    AdminViewBookingModel adminViewBookingModel = new AdminViewBookingModel();
    SceneController sceneController = new SceneController();
    ArrayList<BookingObject> bookingObject = new ArrayList<>();
    BookingSession bookingSession;
    UserSession userSession;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnLockAllSeat;
    @FXML
    private Button btnLockSeat;
    @FXML
    private Button btnUnlockSeat;
    @FXML
    private Button btnBookingList;
    @FXML
    private ToggleButton btnSeat1;
    @FXML
    private ToggleButton btnSeat2;
    @FXML
    private ToggleButton btnSeat3;
    @FXML
    private ToggleButton btnSeat4;
    @FXML
    private ToggleButton btnSeat5;
    @FXML
    private ToggleButton btnSeat6;
    @FXML
    private ToggleButton btnSeat7;
    @FXML
    private ToggleButton btnSeat8;
    @FXML
    private ToggleButton btnSeat9;
    @FXML
    private ToggleButton btnSeat10;
    @FXML
    private ToggleButton btnSeat11;
    @FXML
    private ToggleButton btnSeat12;
    @FXML
    private ToggleButton btnSeat13;
    @FXML
    private ToggleButton btnSeat14;
    @FXML
    private ToggleButton btnSeat15;
    @FXML
    private DatePicker bookDatePicker;
    @FXML
    private ToggleGroup seatGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialise date picker and set starting date to today
        bookDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        //add listener for instant changes when lock or unlock seats
        bookDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            initButtons();
        });
    }

    private void initButtons(){
        try {
            //add buttons to array so we can iterate
            final ToggleButton[] seatButtons = {btnSeat1, btnSeat2, btnSeat3, btnSeat4, btnSeat5, btnSeat6, btnSeat7, btnSeat8,
                    btnSeat9, btnSeat10, btnSeat11, btnSeat12, btnSeat13, btnSeat14, btnSeat15};
            //clears array list before adding, took me a day to get through this problem because i thought using '=' will clear old values out
            bookingObject.clear();
            bookingObject = adminViewBookingModel.getAllBookingsOnDate(Date.valueOf(bookDatePicker.getValue()));
            //iterate to find locked and booked seats
            for (ToggleButton seatButton : seatButtons) {
                seatButton.setStyle("-fx-base: #11cb53");
            }
            for (BookingObject object : bookingObject){
                for(ToggleButton button : seatButtons){
                    if(object.getBookingSeat().equals(button.getText())) {
                        if(object.getCovidLocked().equals("1") || object.getCovidLocked().equals("true")){
                            button.setStyle("-fx-base: #ff8c00");
                        }else{
                            button.setStyle("-fx-base: #dc2430");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
    }

    public void ViewBookingList(ActionEvent event) throws IOException {
        sceneController.openScene(btnBookingList, "ui/admin/BookingList.fxml");
    }

    //lock seat basically create a new booking under 'COVID_locked' name
    public void LockSeat(ActionEvent event) throws IOException, SQLException {
        ToggleButton selectedToggleButton = (ToggleButton) seatGroup.getSelectedToggle();
        if (selectedToggleButton != null && bookDatePicker.getValue() != null){
            boolean lock = sceneController.showConfirmation("Lock seat?","Would you like to lock this seat?");
            if(lock){
                bookingSession = new BookingSession(selectedToggleButton.getText(), Date.valueOf(bookDatePicker.getValue()), UserSession.getUserName(), false, false);
                if(adminViewBookingModel.seatNotEmpty(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                    if(sceneController.showConfirmation("Booking Found", "Would you like to delete the booking and lock this seat?")){
                        setLockToSeat();
                    }
                } else {
                    setLockToSeat();
                }
            }
        }else{
            sceneController.showError("Error", "Please select a seat and date.");
        }
    }

    //split to 2 parts to avoid long methods
    private void setLockToSeat(){
        try{
            if(adminViewBookingModel.DenyBooking(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                if(adminViewBookingModel.isLocked(BookingSession.getBookingSeat(), BookingSession.getBookingDate(), "COVID_Locked", true)){
                    BookingSession.deleteBookingObject();
                    initButtons();
                }else{
                    sceneController.showError("Error", "Failed to lock this seat");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //lock all seats iterate thorugh a 15 times loop to lock all seats
    public void LockAllSeat(ActionEvent event) throws IOException, SQLException {
        ToggleButton selectedToggleButton = (ToggleButton) seatGroup.getSelectedToggle();
        if (selectedToggleButton != null && bookDatePicker.getValue() != null){
            boolean lock = sceneController.showConfirmation("Lock all seat?","Are you sure you want to lock all seat on this date?");
            if(lock){
                for(int i=1; i<=15; i++){
                    bookingSession = new BookingSession(String.valueOf(i), Date.valueOf(bookDatePicker.getValue()), UserSession.getUserName(), false, false);
                    if(adminViewBookingModel.seatNotEmpty(String.valueOf(i), BookingSession.getBookingDate())){
                        if(sceneController.showConfirmation("Booking Found", "Would you like to delete the booking and lock this seat?")){
                            setLockToSeat();
                        }
                    } else {
                        setLockToSeat();
                    }
                }
            }
        }else{
            sceneController.showError("Error", "Please select a seat and date.");
        }
    }

    //unlock seat will delete selected seat that has any bookings with lock
    public void UnlockSeat(ActionEvent event) throws SQLException {
        ToggleButton selectedToggleButton = (ToggleButton) seatGroup.getSelectedToggle();
        if (selectedToggleButton != null && bookDatePicker.getValue() != null){
            boolean unlock = sceneController.showConfirmation("Unlock this seat?","Do you want to unlock this seat?");
            if(unlock){
                bookingSession = new BookingSession(selectedToggleButton.getText(), Date.valueOf(bookDatePicker.getValue()), UserSession.getUserName(), false, false);
                if(adminViewBookingModel.seatNotEmpty(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                    if(sceneController.showConfirmation("Booking Found", "Would you like to delete the booking and unlock this seat?")){
                        removeLockToSeat();
                    }
                } else {
                    removeLockToSeat();
                }
            }
        }else{
            sceneController.showError("Error", "Please select a seat and date.");
        }
    }

    //second part of unlock seat
    private void removeLockToSeat(){
        try{
            if(adminViewBookingModel.DenyBooking(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                BookingSession.deleteBookingObject();
                initButtons();
            }else{
                sceneController.showError("Error", "Failed to unlock this seat");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
