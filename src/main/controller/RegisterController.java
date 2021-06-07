package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import main.model.RegisterModel;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    public void Register(ActionEvent event) throws IOException, SQLException {
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
        }else if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")){
            sceneController.showError("Password is too weak...", "Password must have at least a letter and a digit");
        }else if (RegisterModel.accountExist(userName)){
            sceneController.showError("Account already exists", "Sorry, an account with this user name is already exist");
        }else{
            UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), false);
            if (RegisterModel.isRegistered(firstName, lastName, role, userName, password, UserSession.getAdmin(), question, answer)){
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
