package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Main;
import main.model.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    UserSession user;

    @FXML
    private Label adminName;
    @FXML
    private Button btnManageBooking;
    @FXML
    private Button btnManageAcc;
    @FXML
    private Button btnUpdateAcc;
    @FXML
    private Button btnSignOut;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            String currentUserName = user.getUserName();
            adminName.setText(currentUserName + " (Admin)");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void ManageBooking(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnManageBooking.getScene().getWindow();
        GoToManageBooking(stage);
    }

    public void GoToManageBooking(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/Login.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void ManageAcc(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnManageAcc.getScene().getWindow();
        GoToManageAcc(stage);
    }

    public void GoToManageAcc(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/Login.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void UpdateAccount(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnUpdateAcc.getScene().getWindow();
        GoToUpdateAcc(stage);
    }

    public void GoToUpdateAcc(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UpdateAccountAdmin.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void SignOut(ActionEvent event) throws Exception {
        user.cleanUserSession();
        Stage stage = (Stage) btnSignOut.getScene().getWindow();
        GoToLogin(stage);
    }

    public void GoToLogin(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/Login.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }
}
