package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.controller.SceneController;
import main.session.UserSession;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    SceneController sceneController = new SceneController();
    UserSession user;

    @FXML
    private Label adminName;
    @FXML
    private Button btnManageBooking;
    @FXML
    private Button btnManageAcc;
    @FXML
    private Button btnUpdateAcc;
    @FXML
    private Button btnSignOut;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            adminName.setText(UserSession.getUserName() + " (Admin)");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void ManageBooking(ActionEvent event) throws Exception {
        sceneController.openScene(btnManageBooking, "ui/admin/ManageBooking.fxml");
    }

    public void ManageAcc(ActionEvent event) throws Exception {
        sceneController.openScene(btnManageAcc, "ui/admin/ManageUser.fxml");
    }

    public void UpdateAccount(ActionEvent event) throws Exception {
        sceneController.openScene(btnUpdateAcc, "ui/admin/UpdateAccountAdmin.fxml");
    }

    public void SignOut(ActionEvent event) throws Exception {
        UserSession.cleanUserSession();
        sceneController.openScene(btnSignOut, "ui/Login.fxml");
    }
}
