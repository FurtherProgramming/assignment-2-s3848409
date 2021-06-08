package main.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import main.controller.SceneController;
import main.model.admin.AdminViewBookingModel;
import main.model.user.UserViewBookingModel;
import main.object.BookingObject;
import main.session.BookingSession;
import main.model.user.UserModel;
import main.session.UserSession;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    Map<String, String> userObject = new HashMap<>();
    UserViewBookingModel userViewBookingModel = new UserViewBookingModel();
    SceneController sceneController = new SceneController();
    ArrayList<BookingObject> bookingObject = new ArrayList<>();
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
    private DatePicker bookDatePicker;
    @FXML
    private ToggleGroup seatGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        bookDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            initButtons(Date.valueOf(newValue));
        });
    }

    private void initButtons(Date dateValue){
        try {
            final ToggleButton[] seatButtons = {btnSeat1, btnSeat2, btnSeat3, btnSeat4, btnSeat5, btnSeat6, btnSeat7, btnSeat8,
                    btnSeat9, btnSeat10, btnSeat11, btnSeat12, btnSeat13, btnSeat14, btnSeat15};
            bookingObject.clear();
            bookingObject = userViewBookingModel.getAllBookingsOnDate(dateValue);
            for (ToggleButton seatButton : seatButtons) {
                seatButton.setStyle("-fx-base: #11cb53");
                seatButton.setDisable(false);
            }
            for (BookingObject object : bookingObject){
                for(ToggleButton button : seatButtons){
                    if(object.getBookingSeat().equals(button.getText())) {
                        if(object.getCovidLocked().equals("1") || object.getCovidLocked().equals("true")){
                            button.setStyle("-fx-base: #ff8c00");
                            button.setDisable(true);
                        }else{
                            button.setStyle("-fx-base: #dc2430");
                            button.setDisable(true);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GoToConfirmBooking(ActionEvent event) throws Exception {
        ToggleButton selectedToggleButton = (ToggleButton) seatGroup.getSelectedToggle();
        if (selectedToggleButton != null && bookDatePicker.getValue() != null){
            userObject = userModel.getUserDetail(UserSession.getUserName(), UserSession.getPassword());
            bookingSession = new BookingSession(selectedToggleButton.getText(), Date.valueOf(bookDatePicker.getValue()), userObject.get("firstname") + " " + userObject.get("lastname"), false, false);
            sceneController.openScene(btnNext, "ui/user/UserConfirmBooking.fxml");
        }else{
            sceneController.showError("Error", "Please select a seat and date.");
        }
    }

    public void Back(ActionEvent event) throws Exception {
        sceneController.openScene(btnBack, "ui/user/UserProfile.fxml");
    }

}
