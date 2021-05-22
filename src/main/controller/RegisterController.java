package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.Main;
import main.model.RegisterModel;
import main.model.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public RegisterModel RegisterModel = new RegisterModel();
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
    private ComboBox txtQuestion;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;

    @FXML
    private void setupQuestion() {
        txtQuestion.getItems().add("What was your childhood nickname?");
        txtQuestion.getItems().add("In what city did you meet your spouse/significant other?");
        txtQuestion.getItems().add("What street did you live on in third grade?");
        txtQuestion.getItems().add("What was the last name of your first grade teacher?");
        txtQuestion.getItems().add("How old were you when you started your first job?");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        setupQuestion();
    }

    public void Register(ActionEvent event) throws IOException {
        if (RegisterModel.isRegistered(txtFirstname.getText(), txtLastname.getText(), txtRole.getText(), txtUsername.getText(), txtPassword.getText(), String.valueOf(txtQuestion.getValue()), txtAnswer.getText())){
            userSession.getInstance(txtUsername.getText(), txtPassword.getText());
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            GoToHomePage(stage);
        }else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("There is a problem when registering your account. Please try again...");
            errorAlert.showAndWait();
        }
    }

    public void btnCancel(ActionEvent event)throws Exception{
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        Cancel(stage);
    }

    public void Cancel(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/Login.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public void GoToHomePage(Stage window) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("ui/UserProfile.fxml"));
        Scene scene =  new Scene(root);
        window.setScene(scene);
        window.show();
    }
}
