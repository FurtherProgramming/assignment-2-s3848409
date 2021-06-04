package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

import main.model.LoginModel;
import main.model.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    UserSession userSession;
    SceneController sceneController = new SceneController();

    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnResetPassword;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }
    }

    public void Login(ActionEvent event){
        try {
            if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText())){

                if (loginModel.admin){
                    userSession = UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), true);
                    sceneController.openScene(btnLogin, "ui/AdminProfile.fxml");
                }else{
                    userSession = UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), false);
                    sceneController.openScene(btnLogin, "ui/UserProfile.fxml");
                }
            }else{
                isConnected.setTextFill(Color.web("red"));
                isConnected.setText("Username or Password is incorrect");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Username or Password is incorrect, please try again.");
                errorAlert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void Register(ActionEvent event) throws Exception {
        sceneController.openScene(btnRegister, "ui/Register.fxml");
    }

    public void ResetPassword(ActionEvent event) throws Exception {
        sceneController.openScene(btnResetPassword, "ui/ResetPassword.fxml");
    }

}
