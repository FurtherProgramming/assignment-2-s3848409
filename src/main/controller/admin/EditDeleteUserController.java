package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.admin.AdminModel;
import main.session.TempUserSession;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EditDeleteUserController implements Initializable {
    SceneController sceneController = new SceneController();
    AdminModel adminModel = new AdminModel();
    UserSession userSession;
    TempUserSession tempUserSession;
    Map<String, String> accountObject = new HashMap<>();

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
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            sceneController.getQuestion(secretQuestion);
            accountObject = adminModel.getAccountDetail(TempUserSession.getUserName());
            boolean isAdmin = accountObject.get("admin").equals("1") || accountObject.get("admin").equals("true");
            txtFirstName.setText(accountObject.get("firstname"));
            txtLastName.setText(accountObject.get("lastname"));
            txtUsername.setText(accountObject.get("username"));
            txtPassword.setText(accountObject.get("password"));
            txtRole.setText(accountObject.get("role"));
            chkAdmin.setSelected(isAdmin);
            secretQuestion.setPromptText(accountObject.get("question"));
            secretQuestion.setValue(String.valueOf(accountObject.get("question")));
            txtAnswer.setText(accountObject.get("answer"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteAcc(ActionEvent event) throws IOException {
        boolean delete = sceneController.showConfirmation("Delete " + tempUserSession.getUserName() + "?",
                "Do you want to delete this account?");
        if(delete){
            if(txtUsername.getText().equals(UserSession.getUserName())){
                if(adminModel.DeleteAcc(txtUsername.getText())){
                    sceneController.showInfo("Alert!", "Your account has been deleted!", btnDelete, "ui/Login.fxml");
                    UserSession.cleanUserSession();
                }else{
                    sceneController.showError("Error", "Unable to delete this account.");
                }
            }else{
                if(adminModel.DeleteAcc(txtUsername.getText())){
                    sceneController.showInfo("Success", "This account has been deleted!", btnDelete, "ui/admin/ManageUser.fxml");
                }else{
                    sceneController.showError("Error", "Unable to delete this account.");
                }
            }
        }
    }

    public void Cancel(ActionEvent event) throws IOException {
        sceneController.openScene(btnCancel, "ui/admin/ManageUser.fxml");
    }

    public void SaveChanges(ActionEvent event) throws SQLException, IOException {
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
        }else if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")){
            sceneController.showError("Password is too weak...", "Password must have at least a letter and a digit");
        }else{
            if(adminModel.UpdateDetail(TempUserSession.getUserName(), firstName, lastName, role, userName, password, admin, question, answer)){
                sceneController.showInfo("Success", "This account detail has been changed.", btnSave, "ui/admin/ManageUser.fxml");
            }else{
                sceneController.showError("Error", "Unable to update account detail at the moment.");
            }
        }
    }
}
