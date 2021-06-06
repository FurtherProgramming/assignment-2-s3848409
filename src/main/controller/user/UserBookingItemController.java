package main.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.controller.SceneController;
import main.model.user.UserViewBookingModel;
import main.session.BookingSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserBookingItemController implements Initializable {
    UserViewBookingModel userViewBookingModel = new UserViewBookingModel();
    SceneController sceneController = new SceneController();
    BookingSession object;

    @FXML
    private Button btnCheckIn;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblSeat;
    @FXML
    private Label lblStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(BookingSession.getCheckIn()){
            btnCheckIn.setDisable(true);
        }
        lblDate.setText(BookingSession.getBookingDateAsString());
        lblSeat.setText(lblSeat.getText() + "  " + BookingSession.getBookingSeat());
        lblStatus.setText(lblStatus.getText() + "  " + BookingSession.getBookingStatusAsString());
    }

    public void CancelBooking(ActionEvent event) throws IOException {
        boolean cancel = sceneController.showConfirmation("Cancel Booking", "Do you want to cancel this booking?");
        if(cancel){
            if(userViewBookingModel.CancelBooking(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                sceneController.showInfo("Success", "Your booking has been cancelled.", btnCancel, "ui/user/UserViewBooking.fxml");
            }else {
                sceneController.showError("Database Error", "Cannot cancel the booking at the moment");
            }
        }
    }

    public void CheckInBooking(ActionEvent event) throws IOException {
        boolean checkIn = sceneController.showConfirmation("Check-In", "Do you want to check-in this booking?");
        if(checkIn){
            if(userViewBookingModel.CheckInBooking(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                sceneController.showInfo("Success", "You have checked-in this booking", btnCheckIn, "ui/user/UserViewBooking.fxml");
            }else {
                sceneController.showError("Database Error", "Cannot check-in at the moment");
            }
        }
    }

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/user/UserViewBooking.fxml");
        BookingSession.deleteBookingObject();
    }
}
