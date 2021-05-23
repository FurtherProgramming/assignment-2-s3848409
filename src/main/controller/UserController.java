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

public class UserController implements Initializable {
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
            String currentUserName = user.getUserName();
            userName.setText(currentUserName);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void Book(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBook.getScene().getWindow();
        GoToBook(stage);
    }

    public void GoToBook(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserBooking.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void ViewBooking(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnViewBooking.getScene().getWindow();
        GoToViewBooking(stage);
    }

    public void GoToViewBooking(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserViewBooking.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void UpdateAccount(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnUpdateAcc.getScene().getWindow();
        GoToUpdateAcc(stage);
    }

    public void GoToUpdateAcc(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UpdateAccountUser.fxml"));
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
