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
import main.model.admin.AdminViewBookingModel;
import main.object.BookingObject;
import main.session.BookingSession;
import main.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminBookingListController implements Initializable {
    //instantiate objects for usage later
    SceneController sceneController = new SceneController();
    AdminViewBookingModel adminViewBookingModel = new AdminViewBookingModel();
    UserSession userSession;
    ArrayList<BookingObject> bookingObject = new ArrayList<>();

    @FXML
    private Button btnBack;
    @FXML
    private TableView<BookingObject> bookingListTable;

    //initiate table view, start by creating columns, then retrieve object from backend, and fill data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<BookingObject, String> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        date.setStyle( "-fx-alignment: CENTER;");
        date.setPrefWidth(100);

        TableColumn<BookingObject, String> seat = new TableColumn<>("Seat");
        seat.setCellValueFactory(new PropertyValueFactory<>("bookingSeat"));
        seat.setStyle( "-fx-alignment: CENTER;");
        seat.setPrefWidth(45);

        TableColumn<BookingObject, String> owner = new TableColumn<>("Owner");
        owner.setCellValueFactory(new PropertyValueFactory<>("bookingOwner"));
        owner.setStyle( "-fx-alignment: CENTER;");
        owner.setPrefWidth(120);

        TableColumn<BookingObject, String> status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        status.setStyle( "-fx-alignment: CENTER;");
        status.setPrefWidth(100);

        try {
            if(UserSession.getAdmin()){
                //retrieve all bookings
                bookingObject = adminViewBookingModel.getAllBookings();
                bookingListTable.getColumns().addAll(date, seat, owner, status);
                addButtonToTable();
                //fill data to table
                for (BookingObject object : bookingObject) {
                    bookingListTable.getItems().add(object);
                }
            }else {
                sceneController.showError("Access Denied", "Sorry, you don't have access to this page");
                sceneController.openScene(btnBack, "ui/admin/AdminProfile.fxml");
            }

        } catch (SQLException | IOException e) {
            sceneController.showError("Something went wrong", e.getMessage());
        }
    }

    //create action button to table column
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
                                //get table row and item and create a booking session using that info so we can use it in the next scene
                                BookingObject bookingItem = (BookingObject) getTableRow().getItem();
                                BookingSession bookingSession = new BookingSession(bookingItem.getBookingSeat(), bookingItem.getBookingDate(), bookingItem.getBookingOwner(), bookingItem.getBookingStatusAsBool(), bookingItem.getBookingCheckInAsBool());
                                sceneController.openScene(btn, "ui/admin/AdminBookingItem.fxml");
                            } catch (IOException e) {
                                sceneController.showError("Something went wrong", e.getMessage());
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
        bookingListTable.getColumns().add(action);
    }

    //back to manage booking scene
    public void Back(ActionEvent event) throws IOException {
        sceneController.openScene(btnBack, "ui/admin/ManageBooking.fxml");
    }


}
