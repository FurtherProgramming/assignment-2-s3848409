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
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText("Success");
                successAlert.setContentText("Your booking has been made...");
                Optional<ButtonType> result = successAlert.showAndWait();
                if(!((Optional<?>) result).isPresent() || result.get() == ButtonType.OK){
                    sceneController.openScene(btnConfirmBooking, "ui/UserProfile.fxml");
                }
                BookingSession.deleteBookingObject();
            }else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error...");
                errorAlert.setContentText("Booking failed, please try again.");
                errorAlert.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
