package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import main.controller.SceneController;
import main.model.admin.AdminModel;
import main.object.BookingObject;

import java.io.IOException;

public class AdminManageUserController {
    SceneController sceneController = new SceneController();
    AdminModel adminModel = new AdminModel();

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<BookingObject> userListTable;


    public void AddUser(ActionEvent event) throws IOException {
        sceneController.openScene(btnAdd, "ui/admin/CreateNewUser.fxml");
    }

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
    }
}
