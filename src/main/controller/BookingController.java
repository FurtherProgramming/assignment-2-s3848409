package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import main.model.BookingSession;
import main.model.UserModel;
import main.model.UserSession;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    Map<String, String> userObject = new HashMap<>();
    SceneController sceneController = new SceneController();
    UserModel userModel = new UserModel();
    BookingSession bookingSession;
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            userObject = userModel.getUserDetail(UserSession.getUserName(), UserSession.getPassword());
            bookingSession = new BookingSession(selectedToggleButton.getText(), Date.valueOf(bookDate.getValue()), userObject.get("firstname") + " " + userObject.get("lastname"), false);
            sceneController.openScene(btnNext, "ui/UserConfirmBooking.fxml");
        }else{
            sceneController.showError("Error", "Please select a seat and date.");
        }

    }

    public void Back(ActionEvent event) throws Exception {
        sceneController.openScene(btnBack, "ui/UserProfile.fxml");
    }

}
