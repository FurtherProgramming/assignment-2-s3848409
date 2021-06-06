package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminManageBookingController implements Initializable {
    SceneController sceneController = new SceneController();
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
    }

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
    }

    public void ViewBookingList(ActionEvent event) {

    }

    public void GoToBookingDetail(ActionEvent event) {

    }
}
