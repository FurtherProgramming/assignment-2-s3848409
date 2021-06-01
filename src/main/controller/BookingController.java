package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import main.model.Booking;
import main.model.BookingModel;
import main.model.UserSession;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    BookingModel bookingModel = new BookingModel();
    Booking booking;
    UserSession userSession;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnNext;
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
    private DatePicker bookDate;
    @FXML
    private ToggleGroup seatGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    public void GoToConfirmBooking(ActionEvent event) throws Exception {
        ToggleButton selectedToggleButton = (ToggleButton) seatGroup.getSelectedToggle();
        if (selectedToggleButton != null && bookDate.getValue() != null){
            booking = new Booking(selectedToggleButton.getText(), bookDate.getValue(), UserSession.getUserName());
            Stage stage = (Stage) btnNext.getScene().getWindow();
            openConfirmBooking(stage);
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Please select a seat and date.");
            errorAlert.showAndWait();
        }

    }

    public void openConfirmBooking(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserConfirmBooking.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void Back(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        openUserHome(stage);
    }

    public void openUserHome(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserProfile.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

}
