package main.model.user;

import main.SQLConnection;
import main.object.BookingObject;
import main.session.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

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
                bookingObject.add(new BookingObject(resultSet.getString("seat"),
                        resultSet.getString("bookingDate"),
                        resultSet.getString("ownerName"),
                        resultSet.getBoolean("status"),
                        resultSet.getBoolean("checkIn"),
                        resultSet.getString("covidLocked")));
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

    public boolean CancelBooking(String seat, Date bookingDate){
        boolean success = false;
        java.sql.Date sqlDate = new java.sql.Date(bookingDate.getTime());
        String sql = "DELETE FROM Booking WHERE ( seat = ? AND bookingDate = ? )";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, seat);
            statement.setString(2, String.valueOf(sqlDate));
            statement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public boolean bookingApproved(String seat, Date bookingDate) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Booking where seat = ? and bookingDate = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, seat);
            preparedStatement.setString(2, String.valueOf(bookingDate));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getBoolean("status")){
                    found = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return found;
    }

    public boolean CheckInBooking(String seat, Date bookingDate){
        boolean success = false;
        java.sql.Date sqlDate = new java.sql.Date(bookingDate.getTime());
        String sql = "UPDATE Booking SET checkIn = ? WHERE ( seat = ? AND bookingDate = ? )";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.setString(2, seat);
            statement.setString(3, String.valueOf(sqlDate));
            statement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public ArrayList<BookingObject> getAllBookingsOnDate (Date bookingDate) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Booking WHERE bookingDate = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(bookingDate));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookingObject.add(new BookingObject(resultSet.getString("seat"),
                        resultSet.getString("bookingDate"),
                        resultSet.getString("ownerName"),
                        resultSet.getBoolean("status"),
                        resultSet.getBoolean("checkIn"),
                        resultSet.getString("covidLocked")));
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
