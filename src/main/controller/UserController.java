package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import main.session.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    SceneController sceneController = new SceneController();
    UserSession user;

    @FXML
    private Label userName;
    @FXML
    private Button btnBook;
    @FXML
    private Button btnViewBooking;
    @FXML
    private Button btnUpdateAcc;
    @FXML
    private Button btnSignOut;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            String currentUserName = UserSession.getUserName();
            userName.setText(currentUserName);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void Book(ActionEvent event) throws Exception {
        sceneController.openScene(btnBook, "ui/UserBooking.fxml");
    }

    public void ViewBooking(ActionEvent event) throws Exception {
        sceneController.openScene(btnViewBooking, "ui/UserViewBooking.fxml");
    }

    public void UpdateAccount(ActionEvent event) throws Exception {
        sceneController.openScene(btnUpdateAcc, "ui/UpdateAccountUser.fxml");
    }

    public void SignOut(ActionEvent event) throws Exception {
        UserSession.cleanUserSession();
        sceneController.openScene(btnSignOut, "ui/Login.fxml");
    }

}
