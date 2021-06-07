package main.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Callback;
import main.controller.SceneController;
import main.model.user.UserModel;
import main.model.user.UserViewBookingModel;
import main.object.BookingObject;
import main.session.BookingSession;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
    private TableView<BookingObject> bookingViewTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<BookingObject, Date> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        date.setStyle( "-fx-alignment: CENTER;");
        date.setPrefWidth(90);

        TableColumn<BookingObject, String> seat = new TableColumn<>("Seat");
        seat.setCellValueFactory(new PropertyValueFactory<>("bookingSeat"));
        seat.setStyle( "-fx-alignment: CENTER;");
        seat.setPrefWidth(50);

        TableColumn<BookingObject, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        status.setStyle( "-fx-alignment: CENTER;");
        status.setPrefWidth(90);

        TableColumn<BookingObject, String> checkIn = new TableColumn<>("Check-In");
        checkIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkIn.setStyle( "-fx-alignment: CENTER;");
        checkIn.setPrefWidth(70);

        try {
            userObject = userModel.getUserDetail(UserSession.getUserName(), UserSession.getPassword());
            bookingObject = userViewBookingModel.getBookings(userObject.get("firstname") + " " + userObject.get("lastname"));
            bookingViewTable.getColumns().addAll(date, seat, status, checkIn);
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
        action.setPrefWidth(85);

        Callback<TableColumn<BookingObject, Void>, TableCell<BookingObject, Void>> cellFactory = new Callback<TableColumn<BookingObject, Void>, TableCell<BookingObject, Void>>() {
            @Override
            public TableCell<BookingObject, Void> call(final TableColumn<BookingObject, Void> param) {
                final TableCell<BookingObject, Void> cell = new TableCell<BookingObject, Void>() {
                    private final Button btn = new Button("View");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                BookingObject bookingItem = (BookingObject) getTableRow().getItem();
                                BookingSession bookingSession = new BookingSession(bookingItem.getBookingSeat(), bookingItem.getBookingDate(), bookingItem.getBookingOwner(), bookingItem.getBookingStatusAsBool(), bookingItem.getBookingCheckInAsBool());
                                sceneController.openScene(btn, "ui/user/UserBookingItem.fxml");
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
        sceneController.openScene(btnBack, "ui/user/UserProfile.fxml");
    }
}
