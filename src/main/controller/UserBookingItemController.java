package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.model.BookingSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserBookingItemController implements Initializable {
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
        lblDate.setText(BookingSession.getBookingDateAsString());
        lblSeat.setText(lblSeat.getText() + "  " + BookingSession.getBookingSeat());
        lblStatus.setText(lblStatus.getText() + "  " + BookingSession.getBookingStatusAsString());
    }

    public void CancelBooking(ActionEvent event) {
    }

    public void CheckInBooking(ActionEvent event) {
    }

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/UserViewBooking.fxml");
        BookingSession.deleteBookingObject();
    }
}
