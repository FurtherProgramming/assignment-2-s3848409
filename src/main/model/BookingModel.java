package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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

    public boolean isBooked(String seat, LocalDate bookingDate, String ownerName) {
        connection = SQLConnection.connect();
        boolean Success = false;
        try {
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into Booking (bookingDate, seat, ownerName, confirmation) values ('"+bookingDate+"','"+seat+"','"+ownerName+"','"+false+"') ");
            if (status > 0) {
                Success = true;
            } else {
                Success = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Success;
    }
}
