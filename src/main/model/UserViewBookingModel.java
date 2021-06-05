package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserViewBookingModel {
    ArrayList<BookingObject> bookingObject = new ArrayList<>();
    Connection connection;
    UserSession userSession;

    public UserViewBookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public ArrayList<BookingObject> getBookings (String owner) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Booking WHERE ownerName = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookingObject.add(new BookingObject(resultSet.getString("seat"), resultSet.getString("bookingDate"), resultSet.getString("ownerName"), resultSet.getBoolean("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return bookingObject;
    }

}
