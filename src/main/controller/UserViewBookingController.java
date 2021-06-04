package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Callback;
import main.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class UserViewBookingController implements Initializable {
    SceneController sceneController = new SceneController();
    UserViewBookingModel userViewBookingModel = new UserViewBookingModel();
    UserModel userModel = new UserModel();
    UserSession userSession;
    Map<String, String> userObject = new HashMap<>();
    ArrayList<BookingObject> bookingObject = new ArrayList<>();

    @FXML
    private Button btnBack;
    @FXML
    private TableView bookingViewTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<String, BookingObject> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        date.setStyle( "-fx-alignment: CENTER;");

        TableColumn<String, BookingObject> seat = new TableColumn<>("Seat");
        seat.setCellValueFactory(new PropertyValueFactory<>("bookingSeat"));
        seat.setStyle( "-fx-alignment: CENTER;");

        TableColumn<String, BookingObject> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        status.setStyle( "-fx-alignment: CENTER;");

        try {
            userObject = userModel.getUserDetail(UserSession.getUserName(), UserSession.getPassword());
            bookingObject = userViewBookingModel.getBookings(userObject.get("firstname") + " " + userObject.get("lastname"));
            bookingViewTable.getColumns().addAll(date, seat, status);
            addButtonToTable();

            for (BookingObject object : bookingObject) {
                bookingViewTable.getItems().add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addButtonToTable() {
        TableColumn<BookingObject, Void> action = new TableColumn("Action");
        action.setStyle( "-fx-alignment: CENTER;");
        action.setPrefWidth(102);

        Callback<TableColumn<BookingObject, Void>, TableCell<BookingObject, Void>> cellFactory = new Callback<TableColumn<BookingObject, Void>, TableCell<BookingObject, Void>>() {
            @Override
            public TableCell<BookingObject, Void> call(final TableColumn<BookingObject, Void> param) {
                final TableCell<BookingObject, Void> cell = new TableCell<BookingObject, Void>() {
                    private final Button btn = new Button("View");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                sceneController.openScene(btn, "ui/UserBookingItem.fxml");
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
        bookingViewTable.getColumns().add(action);
    }

    public void Back(ActionEvent event) throws Exception {
        sceneController.openScene(btnBack, "ui/UserProfile.fxml");
    }
}
