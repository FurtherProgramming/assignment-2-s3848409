package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.controller.SceneController;
import main.model.admin.AdminViewBookingModel;
import main.session.BookingSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminBookingItemController implements Initializable {
    //instantiate objects for usage later
    AdminViewBookingModel adminViewBookingModel = new AdminViewBookingModel();
    SceneController sceneController = new SceneController();
    BookingSession bookingSession;

    //ui buttons and labels
    @FXML
    private Button btnApprove;
    @FXML
    private Button btnDeny;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblOwner;
    @FXML
    private Label lblSeat;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblCheckIn;

    //initiate booking info to labels
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(BookingSession.getBookingStatus()){
            btnApprove.setDisable(true);
        }
        lblDate.setText(BookingSession.getBookingDateAsString());
        lblOwner.setText(lblOwner.getText() + "  " + BookingSession.getBookingOwner());
        lblSeat.setText(lblSeat.getText() + "  " + BookingSession.getBookingSeat());
        lblStatus.setText(lblStatus.getText() + "  " + BookingSession.getBookingStatusAsString());
        lblCheckIn.setText(lblCheckIn.getText() + "  " + BookingSession.getCheckInAsString());
    }

    //back to booking list
    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/BookingList.fxml");
        BookingSession.deleteBookingObject();
    }

    //approve booking, ask for confirmation and commit action
    public void ApproveBooking(ActionEvent event) throws IOException {
        boolean approve = sceneController.showConfirmation("Approve Booking?", "Do you want to approve this booking?");
        if(approve){
            if(adminViewBookingModel.ApproveBooking(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                sceneController.showInfo("Success", "This booking has been approved.", btnApprove, "ui/admin/BookingList.fxml");
            }else {
                sceneController.showError("Database Error", "Cannot approve the booking at the moment");
            }
        }
    }

    //deny booking, ask for confirmation and commit action
    public void DenyBooking(ActionEvent event) throws IOException {
        boolean deny = sceneController.showConfirmation("Deny Booking?", "Do you want to deny this booking?");
        if(deny){
            if(adminViewBookingModel.DenyBooking(BookingSession.getBookingSeat(), BookingSession.getBookingDate())){
                sceneController.showInfo("Success", "This booking has been denied.", btnDeny, "ui/admin/BookingList.fxml");
            }else {
                sceneController.showError("Database Error", "Cannot deny the booking at the moment");
            }
        }
    }


}
