package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.RegisterModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateNewUserController implements Initializable {
    SceneController sceneController = new SceneController();
    RegisterModel registerModel = new RegisterModel();

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
    private CheckBox chkAdmin;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnCancel;

    public void Create(ActionEvent event) throws IOException, SQLException {
        //get all text field data
        String id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String role = txtRole.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        boolean admin = chkAdmin.isSelected();
        String question = String.valueOf(secretQuestion.getValue());
        String answer = txtAnswer.getText();
        //check for possible errors
        if(id.isEmpty() ||firstName.isEmpty() || lastName.isEmpty() || role.isEmpty() || userName.isEmpty() || password.isEmpty() || question.isEmpty() || answer.isEmpty()){
            sceneController.showError("Some fields may be blank", "Please complete all fields to continue.");
        }else if (!Character.isUpperCase(firstName.charAt(0)) || !Character.isUpperCase(lastName.charAt(0)) || !Character.isUpperCase(role.charAt(0)) ){
            sceneController.showError("Missing uppercase letters.", "First Name, Last Name, and Role must have first uppercase letter.");
        }else if (password.length() < 5){
            sceneController.showError("Password is too short...", "Password must be more than five characters");
        }else{
            try {
                int employeeId = Integer.parseInt(id);
                if(employeeId <= 0){
                    sceneController.showError("Invalid Value", "Employee ID must be bigger than '0' ");
                    //check for same id or name in database
                }else if (registerModel.accountExist(employeeId, userName)){
                    sceneController.showError("Account already exists", "Sorry, an account with this user name or ID is already exist");
                }else{
                    //register to database
                    if (registerModel.isRegistered(employeeId, firstName, lastName, role, userName, password, admin, question, answer)){
                        sceneController.showInfo("Success", "This account has been created", btnCreate, "ui/admin/ManageUser.fxml");
                    }else{
                        sceneController.showError("Error", "There is a problem when creating this account.");
                    }
                }
            }catch (NumberFormatException e){
                sceneController.showError("Wrong Format", "Please enter numbers only for Employee ID");
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
