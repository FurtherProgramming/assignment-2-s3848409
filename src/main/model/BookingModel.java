package main.model;

import main.SQLConnection;

import java.sql.*;

public class BookingModel {
    Connection connection;

    public BookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        } catch(Exception e){
            return false;
        }
    }

    public boolean bookingExist(String owner, Date bookingDate) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Booking where ownerName = ? and bookingDate= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);
            preparedStatement.setString(2, String.valueOf(bookingDate));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                found = true;
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

    public boolean isBooked(String seat, Date bookingDate, String ownerName) {
        boolean Success = false;
        try {
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into Booking (bookingDate, seat, ownerName, status, checkIn, covidLocked) values ('"+bookingDate+"','"+seat+"','"+ownerName+"','"+false+"','"+false+"','"+false+"') ");
            Success = status > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Success;
    }
}
