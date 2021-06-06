package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.controller.SceneController;
import main.model.admin.AdminModel;

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

    public void Create(ActionEvent event) {

    }

    public void Cancel(ActionEvent event) throws IOException {
        sceneController.openScene(btnCancel, "ui/admin/ManageUser.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneController.getQuestion(secretQuestion);
    }
}
