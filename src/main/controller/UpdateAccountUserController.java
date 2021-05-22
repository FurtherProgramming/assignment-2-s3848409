package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import main.model.UserModel;
import main.model.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UpdateAccountUserController implements Initializable {
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
    private ComboBox secretQuestion;
    @FXML
    private TextField txtAnswer;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            setupQuestion();
            userObject = userModel.getUserDetail(userSession.getUserName(), userSession.getPassword());
            String firstname = userObject.get("firstname");
            String lastname = userObject.get("lastname");
            String username = userObject.get("username");
            String password = userObject.get("password");
            String role = userObject.get("role");
            String question = userObject.get("question");
            String answer = userObject.get("answer");
            txtFirstName.setText(firstname);
            txtLastName.setText(lastname);
            txtUsername.setText(username);
            txtPassword.setText(password);
            txtRole.setText(role);
            secretQuestion.setPromptText(question);
            secretQuestion.setValue(String.valueOf(question));
            txtAnswer.setText(answer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupQuestion() {
        secretQuestion.getItems().add("What was your childhood nickname?");
        secretQuestion.getItems().add("In what city did you meet your spouse/significant other?");
        secretQuestion.getItems().add("What street did you live on in third grade?");
        secretQuestion.getItems().add("What was the last name of your first grade teacher?");
        secretQuestion.getItems().add("How old were you when you started your first job?");
    }

    public void SaveChanges(ActionEvent event) throws Exception {
        if(userModel.UpdateDetail(txtFirstName.getText(), txtLastName.getText(), txtRole.getText(), txtUsername.getText(), txtPassword.getText(), String.valueOf(secretQuestion.getValue()), txtAnswer.getText())){
            userSession.getInstance(txtUsername.getText(), txtPassword.getText());
            Stage stage = (Stage) btnSaveChanges.getScene().getWindow();
            GoToUserHome(stage);
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Unable to update your account detail.");
            errorAlert.showAndWait();
        }
    }

    public void Cancel(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        GoToUserHome(stage);
    }

    public void GoToUserHome(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserProfile.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

}
