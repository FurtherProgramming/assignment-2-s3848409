package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import main.model.ResetPasswordModel;

import java.io.IOException;
import java.sql.SQLException;

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
                sceneController.showError("Error", "Username not found, please try again.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
