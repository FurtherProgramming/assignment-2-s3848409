package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Main;
import main.model.Booking;
import main.model.BookingModel;
import main.model.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConfirmBookingController implements Initializable {
    BookingModel bookingModel = new BookingModel();
    Booking booking;

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
        lblOwner.setText(Booking.getBookingOwner());
        lblSeat.setText(Booking.getBookingSeat());
        lblDate.setText(Booking.getBookingDateAsString());
    }

    public void BackToUserBooking(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        openUserBooking(stage);
    }

    public void openUserBooking(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserBooking.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void openUserProfile(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserProfile.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void ConfirmBooking(ActionEvent event) throws Exception {
        try{
            if(bookingModel.isBooked(Booking.getBookingSeat(), Booking.getBookingDate(), Booking.getBookingOwner())){
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText("Success");
                successAlert.setContentText("Your booking has been made...");
                Optional<ButtonType> result = successAlert.showAndWait();
                if(!((Optional<?>) result).isPresent()){
                    Stage stage = (Stage) btnConfirmBooking.getScene().getWindow();
                    openUserProfile(stage);
                } else if(result.get() == ButtonType.OK){
                    Stage stage = (Stage) btnConfirmBooking.getScene().getWindow();
                    openUserProfile(stage);
                }
                Booking.deleteBookingObject();
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
