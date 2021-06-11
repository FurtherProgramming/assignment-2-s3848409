package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.admin.AdminModel;
import main.model.user.UserModel;
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
    UserModel userModel = new UserModel();
    UserSession userSession;
    TempUserSession tempUserSession;
    //account object from database using hash map<>
    Map<String, String> accountObject = new HashMap<>();
    //two booleans to check for changes in text fields
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
            txtId.setText(accountObject.get("id"));
            txtFirstName.setText(accountObject.get("firstname"));
            txtLastName.setText(accountObject.get("lastname"));
            txtUsername.setText(accountObject.get("username"));
            txtPassword.setText(accountObject.get("password"));
            txtRole.setText(accountObject.get("role"));
            chkAdmin.setSelected(isAdmin);
            secretQuestion.setPromptText(accountObject.get("question"));
            secretQuestion.setValue(String.valueOf(accountObject.get("question")));
            txtAnswer.setText(accountObject.get("answer"));

            //scanning for changes in these two txt fields, id and user name
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

    public void DeleteAcc(ActionEvent event) throws IOException {
        //delete an account will prompt for confirmation
        boolean delete = sceneController.showConfirmation("Delete " + TempUserSession.getUserName() + "?",
                "Do you want to delete this account?");
        if(delete){
            if(txtUsername.getText().equals(UserSession.getUserName())){
                if(adminModel.DeleteAcc(txtUsername.getText())){
                    //alert admin when deletion is successful
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
        String id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String role = txtRole.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        boolean admin = chkAdmin.isSelected();
        String question = String.valueOf(secretQuestion.getValue());
        String answer = txtAnswer.getText();
        if(id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || role.isEmpty() || userName.isEmpty() || password.isEmpty() || question.isEmpty() || answer.isEmpty()){
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
                    if(adminModel.UpdateDetail(TempUserSession.getUserName(), employeeId, firstName, lastName, role, userName, password, admin, question, answer)){
                        sceneController.showInfo("Success", "This account detail has been changed.", btnSave, "ui/admin/ManageUser.fxml");
                    }else{
                        sceneController.showError("Error", "Unable to update account detail at the moment.");
                    }
                }
            }catch (NumberFormatException e){
                sceneController.showError("Wrong Format", "Please enter numbers only for Employee ID");
            }
        }
    }
}
