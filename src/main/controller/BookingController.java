package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class BookingController {

    @FXML
    private Button btnBackUserViewBooking;
    @FXML
    private Button btnBackConfirmBooking;
    @FXML
    private Button btnConfirmBookingUser;
    @FXML
    private Button btnBackUserBooking;
    @FXML
    private Button btnNextUserBooking;
    @FXML
    private Button btnSeat1;
    @FXML
    private Button btnSeat2;
    @FXML
    private Button btnSeat3;
    @FXML
    private Button btnSeat4;
    @FXML
    private Button btnSeat5;
    @FXML
    private Button btnSeat6;
    @FXML
    private Button btnSeat7;
    @FXML
    private Button btnSeat8;
    @FXML
    private Button btnSeat9;
    @FXML
    private Button btnSeat10;
    @FXML
    private Button btnSeat11;
    @FXML
    private Button btnSeat12;
    @FXML
    private Button btnSeat13;
    @FXML
    private Button btnSeat14;
    @FXML
    private Button btnSeat15;
    @FXML
    private DatePicker bookDate;

    public void GoToConfirmBooking(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnNextUserBooking.getScene().getWindow();
        openConfirmBooking(stage);
    }

    public void openConfirmBooking(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserConfirmBooking.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void BackUserViewBooking(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBackUserViewBooking.getScene().getWindow();
        openUserHome(stage);
    }

    public void BackUserBooking(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBackUserBooking.getScene().getWindow();
        openUserHome(stage);
    }

    public void openUserHome(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserProfile.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void BackToUserBooking(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBackConfirmBooking.getScene().getWindow();
        openUserBooking(stage);
    }

    public void openUserBooking(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserBooking.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }
    public void ConfirmBooking(ActionEvent event) throws Exception {

    }

}
