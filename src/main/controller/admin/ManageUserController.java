package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.controller.SceneController;
import main.model.admin.AdminModel;
import main.object.UserObject;
import main.session.TempUserSession;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageUserController implements Initializable {
    SceneController sceneController = new SceneController();
    AdminModel adminModel = new AdminModel();
    UserSession userSession;
    TempUserSession tempUserSession;
    ArrayList<UserObject> userObject = new ArrayList<>();

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<UserObject> userListTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<UserObject, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstName.setStyle( "-fx-alignment: CENTER-LEFT;");

        TableColumn<UserObject, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastName.setStyle( "-fx-alignment: CENTER-LEFT;");

        TableColumn<UserObject, String> role = new TableColumn<>("Role");
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        role.setStyle( "-fx-alignment: CENTER-LEFT;");

        TableColumn<UserObject, String> admin = new TableColumn<>("Admin");
        admin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        admin.setStyle( "-fx-alignment: CENTER-LEFT;");

        try {
            if(UserSession.getAdmin()){
                userObject = adminModel.getAllUsers();
                userListTable.getColumns().addAll(firstName, lastName, role, admin);
                addButtonToTable();

                for (UserObject object : userObject) {
                    userListTable.getItems().add(object);
                }
            }else {
                sceneController.showError("Access Denied", "Sorry, you don't have access to this page");
                sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addButtonToTable() {
        TableColumn<UserObject, Void> action = new TableColumn("Action");
        action.setStyle( "-fx-alignment: CENTER;");
        action.setPrefWidth(90);

        Callback<TableColumn<UserObject, Void>, TableCell<UserObject, Void>> cellFactory = new Callback<TableColumn<UserObject, Void>, TableCell<UserObject, Void>>() {
            @Override
            public TableCell<UserObject, Void> call(final TableColumn<UserObject, Void> param) {
                final TableCell<UserObject, Void> cell = new TableCell<UserObject, Void>() {
                    private final Button btn = new Button("View");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                UserObject editUserObject = (UserObject) getTableRow().getItem();
                                tempUserSession = new TempUserSession(editUserObject.getUserName(), editUserObject.getAdminAsBool());
                                sceneController.openScene(btn, "ui/admin/EditDeleteUser.fxml");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        action.setCellFactory(cellFactory);
        userListTable.getColumns().add(action);
    }

    public void AddUser(ActionEvent event) throws IOException {
        sceneController.openScene(btnAdd, "ui/admin/CreateNewUser.fxml");
    }

    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
    }

}
