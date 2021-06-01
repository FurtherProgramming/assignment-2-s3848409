package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.model.ResetPasswordModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResetPasswordController {
    public ResetPasswordModel resetPasswordModel = new ResetPasswordModel();

    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnContinue;
    @FXML
    private Button btnBack1;

    public void Back1(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnBack1.getScene().getWindow();
        openLogin(stage);
    }

    public void openLogin(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/Login.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void Continue(ActionEvent event) throws Exception {
        try {
            if (resetPasswordModel.userFound(txtUsername.getText())){
                Stage stage = (Stage) btnContinue.getScene().getWindow();
                openSecretQuestion(stage);
            }else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Username not found, please try again.");
                errorAlert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void openSecretQuestion(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/AnswerSecretQuestion.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

}
