package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import main.model.BookingSession;
import main.model.BookingModel;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConfirmBookingController implements Initializable {
    SceneController sceneController = new SceneController();
    BookingModel bookingModel = new BookingModel();
    BookingSession bookingSession;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnConfirmBooking;
    @FXML
    private Label lblSeat;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblOwner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblOwner.setText(BookingSession.getBookingOwner());
        lblSeat.setText(BookingSession.getBookingSeat());
        lblDate.setText(BookingSession.getBookingDateAsString());
    }

    public void BackToUserBooking(ActionEvent event) throws Exception {
        sceneController.openScene(btnBack, "ui/UserBooking.fxml");
    }

    public void ConfirmBooking(ActionEvent event) throws Exception {
        try{
            if(bookingModel.isBooked(BookingSession.getBookingSeat(), BookingSession.getBookingDate(), BookingSession.getBookingOwner(), false)){
                sceneController.showInfo("Success","Your booking has been made...", btnConfirmBooking, "ui/UserProfile.fxml");
                BookingSession.deleteBookingObject();
            }else{
                sceneController.showError("Error", "Booking failed, please try again.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
