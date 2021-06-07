package main.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import main.controller.SceneController;
import main.session.BookingSession;
import main.model.BookingModel;

import java.net.URL;
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
        sceneController.openScene(btnBack, "ui/user/UserBooking.fxml");
    }

    public void ConfirmBooking(ActionEvent event) throws Exception {
        try{
            if(bookingModel.bookingExist(BookingSession.getBookingOwner(), BookingSession.getBookingDate())){
                sceneController.showError("Error", "Sorry, you have booked a seat for this date already.");
            }else{
                if(bookingModel.isBooked(BookingSession.getBookingSeat(), BookingSession.getBookingDate(), BookingSession.getBookingOwner())){
                    sceneController.showInfo("Success","Your booking has been made...", btnConfirmBooking, "ui/user/UserProfile.fxml");
                    BookingSession.deleteBookingObject();
                }else{
                    sceneController.showError("Error", "Booking failed, please try again.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
