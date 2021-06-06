package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.admin.AdminViewBookingModel;
import main.object.BookingObject;
import main.session.BookingSession;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {
    AdminViewBookingModel adminViewBookingModel = new AdminViewBookingModel();
    SceneController sceneController = new SceneController();
    ArrayList<BookingObject> bookingObject = new ArrayList<>();
    BookingSession bookingSession;
    UserSession userSession;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnBookingList;
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
            try {
                final ToggleButton[] seatButtons = {btnSeat1, btnSeat2, btnSeat3, btnSeat4, btnSeat5, btnSeat6, btnSeat7, btnSeat8,
                                                    btnSeat9, btnSeat10, btnSeat11, btnSeat12, btnSeat13, btnSeat14, btnSeat15};
                bookingObject = adminViewBookingModel.getAllBookingsOnDate(Date.valueOf(newValue));
                System.out.println(bookingObject.size());
                for (BookingObject object : bookingObject){
                    for(ToggleButton button : seatButtons){
                        if(object.getBookingSeat().equals(button.getText())) {
                            if(object.getCovidLocked()){
                                button.setStyle("-fx-base: #ff8c00");
                            }else{
                                button.setStyle("-fx-base: #dc2430");
                            }
                        }else {
                            button.setStyle("-fx-base: #11cb53");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
    }

    public void ViewBookingList(ActionEvent event) throws IOException {
        sceneController.openScene(btnBookingList, "ui/admin/BookingList.fxml");
    }

    public void BookingDetail(ActionEvent event) throws IOException {
        ToggleButton selectedToggleButton = (ToggleButton) seatGroup.getSelectedToggle();
        if (selectedToggleButton != null && bookDatePicker.getValue() != null){
            bookingSession = new BookingSession(selectedToggleButton.getText(), Date.valueOf(bookDatePicker.getValue()), UserSession.getUserName(), false, false);
//            sceneController.openScene(btnNext, "ui/user/UserConfirmBooking.fxml");
        }else{
            sceneController.showError("Error", "Please select a seat and date.");
        }
    }
}
