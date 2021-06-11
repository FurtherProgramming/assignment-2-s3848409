package main.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import main.controller.SceneController;
import main.model.user.UserModel;
import main.session.UserSession;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UpdateAccountUserController implements Initializable {
    SceneController sceneController = new SceneController();
    Map<String, String> userObject = new HashMap<>();
    UserModel userModel = new UserModel();
    UserSession userSession;
    boolean idAvailable = true;
    boolean userNameAvailable = true;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtRole;
    @FXML
    private ComboBox<String> secretQuestion;
    @FXML
    private TextField txtAnswer;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            sceneController.getQuestion(secretQuestion);
            userObject = userModel.getUserDetail(UserSession.getUserName(), UserSession.getPassword());
            txtId.setText(userObject.get("id"));
            txtFirstName.setText(userObject.get("firstname"));
            txtLastName.setText(userObject.get("lastname"));
            txtUsername.setText(userObject.get("username"));
            txtPassword.setText(userObject.get("password"));
            txtRole.setText(userObject.get("role"));
            secretQuestion.setPromptText(userObject.get("question"));
            secretQuestion.setValue(String.valueOf(userObject.get("question")));
            txtAnswer.setText(userObject.get("answer"));

            txtId.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!oldValue.equals(newValue)){
                    try{
                        int employeeId = Integer.parseInt(newValue);
                        if(employeeId <= 0){
                            sceneController.showError("Invalid Value", "Employee ID must be bigger than '0' ");
                        }else{
                            idAvailable = !userModel.idExist(employeeId);
                        }
                    }catch (NumberFormatException | SQLException e){
                        sceneController.showError("Wrong Format", "Please enter numbers only for Employee ID");
                    }
                }
            });
            txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!oldValue.equals(newValue)){
                    try {
                        userNameAvailable = !userModel.userNameExist(newValue);
                    } catch (SQLException e) {
                        sceneController.showError("Something went wrong", e.getMessage());
                    }
                }
            });
        } catch (SQLException e) {
            sceneController.showError("Something went wrong", e.getMessage());
        }
    }

    public void SaveChanges(ActionEvent event) throws Exception {
        String id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String role = txtRole.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String question = String.valueOf(secretQuestion.getValue());
        String answer = txtAnswer.getText();
        if(id.isEmpty()|| firstName.isEmpty() || lastName.isEmpty() || role.isEmpty() || userName.isEmpty() || password.isEmpty() || question.isEmpty() || answer.isEmpty()){
            sceneController.showError("Some fields may be blank", "Please complete all fields to continue.");
        }else if (!Character.isUpperCase(firstName.charAt(0)) || !Character.isUpperCase(lastName.charAt(0)) || !Character.isUpperCase(role.charAt(0)) ){
            sceneController.showError("Missing uppercase letters.", "First Name, Last Name, and Role must have first uppercase letter.");
        }else if (password.length() < 5){
            sceneController.showError("Password is too short...", "Password must be more than five characters");
        }else if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")){
            sceneController.showError("Password is too weak...", "Password must have at least a letter and a digit");
        }else if (!idAvailable || !userNameAvailable){
            sceneController.showError("Duplicate User Name or ID", "Sorry, this user name or ID is taken");
        }else{
            try{
                int employeeId = Integer.parseInt(id);
                if(employeeId <= 0){
                    sceneController.showError("Invalid Value", "Employee ID must be bigger than '0' ");
                }else{
                    if (userModel.UpdateDetail(employeeId, firstName, lastName, role, userName, password, question, answer)){
                        UserSession.getInstance(userName, password, false);
                        sceneController.showInfo("Success", "Your account detail has been changed.", btnSave, "ui/user/UserProfile.fxml");
                    }else{
                        sceneController.showError("Error", "There is a problem when registering your account. Please try again...");
                    }
                }
            }catch (NumberFormatException e){
                sceneController.showError("Wrong Format", "Please enter numbers only for Employee ID");
            }
        }
    }

    public void Cancel(ActionEvent event) throws Exception {
        sceneController.openScene(btnCancel, "ui/user/UserProfile.fxml");
    }
}
