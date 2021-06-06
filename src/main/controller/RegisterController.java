package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import main.model.RegisterModel;
import main.session.UserSession;

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
        String firstName = txtFirstname.getText();
        String lastName = txtLastname.getText();
        String role = txtRole.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String question = String.valueOf(txtQuestion.getValue());
        String answer = txtAnswer.getText();
        if(firstName.isEmpty() || lastName.isEmpty() || role.isEmpty() || userName.isEmpty() || password.isEmpty() || question.isEmpty() || answer.isEmpty()){
            sceneController.showError("Some fields may be blank", "Please complete all fields to continue.");
        }else if (!Character.isUpperCase(firstName.charAt(0)) || !Character.isUpperCase(lastName.charAt(0)) || !Character.isUpperCase(role.charAt(0)) ){
            sceneController.showError("Missing uppercase letters.", "First Name, Last Name, and Role must have first uppercase letter.");
        }else if (password.length() < 5){
            sceneController.showError("Password is too short...", "Password must be more than five characters");
        }else{
            if (RegisterModel.isRegistered(firstName, lastName, role, userName, password, question, answer)){
                UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), false);
                sceneController.showInfo("Success", "Your account has been created", btnRegister, "ui/user/UserProfile.fxml");
            }else{
                sceneController.showError("Error", "There is a problem when registering your account. Please try again...");
            }
        }
    }

    public void btnCancel(ActionEvent event)throws Exception{
        sceneController.openScene(btnCancel, "ui/Login.fxml");
    }
}
