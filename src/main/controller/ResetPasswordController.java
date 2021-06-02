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
    SceneController sceneController = new SceneController();

    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnContinue;
    @FXML
    private Button btnBack;

    public void Back(ActionEvent event) throws Exception {
        sceneController.openScene(btnBack, "ui/Login.fxml");
    }

    public void Continue(ActionEvent event) throws Exception {
        try {
            if (resetPasswordModel.userFound(txtUsername.getText())){
                sceneController.openScene(btnContinue, "ui/AnswerSecretQuestion.fxml");
            }else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Username not found, please try again.");
                errorAlert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
