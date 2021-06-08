package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import main.model.ResetPasswordModel;
import main.session.UserSession;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AnswerSecretQuestionController implements Initializable {
    ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    SceneController sceneController = new SceneController();

    @FXML
    private TextField txtQuestion;
    @FXML
    private TextField txtAnswer;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnDone;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            txtQuestion.setText(resetPasswordModel.getSecretQuestion());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Back(ActionEvent event) throws Exception {
        sceneController.openScene(btnBack, "ui/ResetPassword.fxml");
    }

    public void Done(ActionEvent event) throws Exception {
        try {
            if (resetPasswordModel.compareAnswer(txtAnswer.getText())){
                TextArea textArea = new TextArea("Your new password is:  " + resetPasswordModel.getNewPassword());
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefSize(300,10);

                GridPane gridPane = new GridPane();
                gridPane.add(textArea, 1, 1);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText("Your password has been changed.");
                successAlert.getDialogPane().setContent(gridPane);

                Optional<ButtonType> result = successAlert.showAndWait();
                if(!((Optional<?>) result).isPresent() || result.get() == ButtonType.OK){
                    sceneController.openScene(btnDone, "ui/Login.fxml");
                }
                UserSession.cleanUserSession();
            }else{
                sceneController.showError("Error", "Incorrect Answer to your secret question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sceneController.showError("Error", "Unable to change your password.");
        }
    }
}
