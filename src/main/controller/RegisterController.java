package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import main.model.RegisterModel;
import main.model.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public RegisterModel RegisterModel = new RegisterModel();
    SceneController sceneController = new SceneController();
    UserSession userSession;

    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtRole;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtAnswer;
    @FXML
    private ComboBox<String> txtQuestion;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        sceneController.getQuestion(txtQuestion);
    }

    public void Register(ActionEvent event) throws IOException {
        if (RegisterModel.isRegistered(txtFirstname.getText(), txtLastname.getText(), txtRole.getText(), txtUsername.getText(), txtPassword.getText(), String.valueOf(txtQuestion.getValue()), txtAnswer.getText())){
            UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), false);
            sceneController.openScene(btnRegister, "ui/UserProfile.fxml");
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("There is a problem when registering your account. Please try again...");
            errorAlert.showAndWait();
        }
    }

    public void btnCancel(ActionEvent event)throws Exception{
        sceneController.openScene(btnCancel, "ui/Login.fxml");
    }
}
