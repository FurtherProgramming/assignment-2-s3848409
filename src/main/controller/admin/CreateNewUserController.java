package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.admin.AdminModel;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewUserController implements Initializable {
    SceneController sceneController = new SceneController();
    AdminModel adminModel = new AdminModel();

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
    private CheckBox chkAdmin;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnCancel;

    public void Create(ActionEvent event) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String role = txtRole.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        boolean admin = chkAdmin.isSelected();
        String question = String.valueOf(secretQuestion.getValue());
        String answer = txtAnswer.getText();
        if(firstName.isEmpty() || lastName.isEmpty() || role.isEmpty() || userName.isEmpty() || password.isEmpty() || question.isEmpty() || answer.isEmpty()){
            sceneController.showError("Some fields may be blank", "Please complete all fields to continue.");
        }else if (!Character.isUpperCase(firstName.charAt(0)) || !Character.isUpperCase(lastName.charAt(0)) || !Character.isUpperCase(role.charAt(0)) ){
            sceneController.showError("Missing uppercase letters.", "First Name, Last Name, and Role must have first uppercase letter.");
        }else if (password.length() < 5){
            sceneController.showError("Password is too short...", "Password must be more than five characters");
        }else{
            if (adminModel.CreateAccount(firstName, lastName, role, userName, password, admin, question, answer)){
                sceneController.showInfo("Success", "This account has been created", btnCreate, "ui/admin/ManageUser.fxml");
            }else{
                sceneController.showError("Error", "There is a problem when creating this account.");
            }
        }
    }

    public void Cancel(ActionEvent event) throws IOException {
        sceneController.openScene(btnCancel, "ui/admin/ManageUser.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneController.getQuestion(secretQuestion);
    }
}
