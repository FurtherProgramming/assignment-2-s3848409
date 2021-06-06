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
    private Button btnSaveChanges;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            sceneController.getQuestion(secretQuestion);
            userObject = userModel.getUserDetail(UserSession.getUserName(), UserSession.getPassword());
            txtFirstName.setText(userObject.get("firstname"));
            txtLastName.setText(userObject.get("lastname"));
            txtUsername.setText(userObject.get("username"));
            txtPassword.setText(userObject.get("password"));
            txtRole.setText(userObject.get("role"));
            secretQuestion.setPromptText(userObject.get("question"));
            secretQuestion.setValue(String.valueOf(userObject.get("question")));
            txtAnswer.setText(userObject.get("answer"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SaveChanges(ActionEvent event) throws Exception {
        if(userModel.UpdateDetail(txtFirstName.getText(), txtLastName.getText(), txtRole.getText(), txtUsername.getText(), txtPassword.getText(), String.valueOf(secretQuestion.getValue()), txtAnswer.getText())){
            UserSession.getInstance(txtUsername.getText(), txtPassword.getText(), false);
            sceneController.showInfo("Success", "Your account detail has been changed.", btnSaveChanges, "ui/user/UserProfile.fxml");
        }else{
            sceneController.showError("Error", "Unable to update your account details.");
        }
    }

    public void Cancel(ActionEvent event) throws Exception {
        sceneController.openScene(btnCancel, "ui/user/UserProfile.fxml");
    }
}
