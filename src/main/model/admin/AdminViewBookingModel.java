package main.model.admin;

import main.SQLConnection;
import main.object.BookingObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AdminViewBookingModel {
    ArrayList<BookingObject> bookingObject = new ArrayList<>();
    Connection connection;

    public AdminViewBookingModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public ArrayList<BookingObject> getAllBookings () throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Booking";
        try {
            preparedStatement = connection.prepareStatement(query);
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

    public boolean DenyBooking(String seat, Date bookingDate){
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

    public boolean ApproveBooking(String seat, Date bookingDate){
        boolean success = false;
        java.sql.Date sqlDate = new java.sql.Date(bookingDate.getTime());
        String sql = "UPDATE Booking SET status = ? WHERE ( seat = ? AND bookingDate = ? )";
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

    public boolean GenerateBookingtoCsv(String fileName) throws IOException, SQLException {
        boolean success = false;
        String status = "";
        String checkIn = "";
        String covidLocked = "";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        fileWriter.write("BookingDate, Seat, OwnerName, Status, CheckIn, COVIDLocked");
        String query = "SELECT * FROM Booking";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String bookingDate = resultSet.getString("bookingDate");
                String seat = resultSet.getString("seat");
                String ownerName = resultSet.getString("ownerName");
                boolean boolStatus = resultSet.getBoolean("status");
                boolean boolCheckIn = resultSet.getBoolean("checkIn");
                boolean boolCovidLocked = resultSet.getBoolean("covidLocked");
                if(boolStatus){ status = "Approved"; }
                else { status = "Not Approved"; }
                if(boolCheckIn){ checkIn = "Yes"; }
                else{ checkIn = "No"; }
                if(boolCovidLocked){ covidLocked = "Yes"; }
                else { covidLocked = "No"; }
                String line = String.format("%s,%s,%s,%s,%s,%s", bookingDate, seat, ownerName, status, checkIn, covidLocked);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null && resultSet != null) {
                fileWriter.close();
                preparedStatement.close();
                resultSet.close();
            }
        }
        return success;
    }

    public boolean isLocked(String seat, Date bookingDate, String ownerName, boolean lock) {
        boolean Success = false;
        try {
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into Booking (bookingDate, seat, ownerName, status, checkIn, covidLocked) values ('"+bookingDate+"','"+seat+"','"+ownerName+"','"+false+"','"+false+"','"+lock+"') ");
            Success = status > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Success;
    }

    public boolean seatNotEmpty(String seat, Date bookingDate) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Booking where seat = ? and bookingDate= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, seat);
            preparedStatement.setString(2, String.valueOf(bookingDate));
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                found = !resultSet.getString("ownerName").equals("COVID_Locked");
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

}
