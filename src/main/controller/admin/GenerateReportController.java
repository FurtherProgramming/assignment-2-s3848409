package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.controller.SceneController;
import main.model.admin.AdminModel;
import main.model.admin.AdminViewBookingModel;

import java.io.IOException;
import java.sql.SQLException;

public class GenerateReportController {
    SceneController sceneController = new SceneController();
    AdminModel adminModel = new AdminModel();
    AdminViewBookingModel adminViewBookingModel = new AdminViewBookingModel();

    @FXML
    private Button btnBack;
    @FXML
    private Button btnBookingToCsv;
    @FXML
    private Button btnUserToCsv;

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
    }

    public void BookingToCsv(ActionEvent event) throws SQLException, IOException {
        String filename = sceneController.showDialog("Bookings To CSV", "Please enter the file name you want to save.");
        if(filename.trim().isEmpty()){
            sceneController.showError("Error", "File name must not be empty.");
        }else{
            boolean success = adminViewBookingModel.GenerateBookingtoCsv(filename + ".csv");
            if(success){
                sceneController.showInfoOnly("Success", "Your bookings report has been generated.\nFile name is: " + filename + ".csv");
            }else {
                sceneController.showError("Error", "There's an error while generating csv file.");
            }
        }
    }

    public void UserToCsv(ActionEvent event) throws SQLException, IOException {
        String filename = sceneController.showDialog("Users to CSV", "Please enter the file name you want to save.");
        if(filename.trim().isEmpty()){
            sceneController.showError("Error", "File name must not be empty.");
        }else{
            boolean success = adminModel.GenerateUsertoCsv(filename + ".csv");
            if(success){
                sceneController.showInfoOnly("Success", "Your users report has been generated.\nFile name is: " + filename + ".csv");
            }else {
                sceneController.showError("Error", "There's an error while generating csv file.");
            }
        }
    }
}
