package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.util.Date;
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
