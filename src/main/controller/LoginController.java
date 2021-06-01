package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.Main;
import main.model.LoginModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import main.model.UserSession;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    UserSession user;

    @FXML
    private AnchorPane loginScene;
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
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                if (loginModel.admin == true){
                    user = user.getInstance(txtUsername.getText(), txtPassword.getText(), true);
                    openAdminHomePage(stage);
                }else{
                    user = user.getInstance(txtUsername.getText(), txtPassword.getText(), false);
                    openUserHomePage(stage);
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
        Stage stage = (Stage) btnRegister.getScene().getWindow();
        openRegister(stage);
    }

    public void openRegister(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/Register.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void openUserHomePage(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserProfile.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void openAdminHomePage(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/AdminProfile.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void ResetPassword(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnResetPassword.getScene().getWindow();
        openResetPassword(stage);
    }

    public void openResetPassword(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/ResetPassword.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }
}
