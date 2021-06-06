package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.admin.AdminModel;
import main.session.UserSession;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UpdateAccountAdminController implements Initializable {
    SceneController sceneController = new SceneController();
    AdminModel adminModel = new AdminModel();
    UserSession userSession;
    Map<String, String> adminObject = new HashMap<>();

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
    private Button btnSaveChanges;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            sceneController.getQuestion(secretQuestion);
            adminObject = adminModel.getAdminDetail(UserSession.getUserName(), UserSession.getPassword());
            txtFirstName.setText(adminObject.get("firstname"));
            txtLastName.setText(adminObject.get("lastname"));
            txtUsername.setText(adminObject.get("username"));
            txtPassword.setText(adminObject.get("password"));
            txtRole.setText(adminObject.get("role"));
            chkAdmin.setSelected(Boolean.parseBoolean(adminObject.get("admin")));
            secretQuestion.setPromptText(adminObject.get("question"));
            secretQuestion.setValue(String.valueOf(adminObject.get("question")));
            txtAnswer.setText(adminObject.get("answer"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SaveChanges(ActionEvent event) throws Exception {
        if(adminModel.UpdateDetail(txtFirstName.getText(), txtLastName.getText(), txtRole.getText(), txtUsername.getText(), txtPassword.getText(), chkAdmin.isSelected(), String.valueOf(secretQuestion.getValue()), txtAnswer.getText())){
            UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), true);
            sceneController.showInfo("Success", "Your account detail has been changed.", btnSaveChanges, "ui/admin/AdminProfile.fxml");
        }else{
            sceneController.showError("Error", "Unable to update account detail at the moment.");
        }
    }

    public void DeleteAcc(ActionEvent event) throws Exception {
        if(txtUsername.getText().equals(UserSession.getUserName())){
            if(adminModel.DeleteAcc(txtUsername.getText())){
                sceneController.openScene(btnDelete, "ui/Login.fxml");
                UserSession.cleanUserSession();
            }else{
                sceneController.showError("Error", "Unable to delete this account.");
            }
        }else{
            if(adminModel.DeleteAcc(txtUsername.getText())){
                UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), true);
                sceneController.openScene(btnDelete, "ui/admin/AdminProfile.fxml");
            }else{
                sceneController.showError("Error", "Unable to delete this account.");
            }
        }
    }

    public void Cancel(ActionEvent event) throws Exception {
        sceneController.openScene(btnCancel, "ui/admin/AdminProfile.fxml");
    }
}
