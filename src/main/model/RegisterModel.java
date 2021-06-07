package main.model;

import main.SQLConnection;
import java.sql.*;

public class RegisterModel {
    Connection connection;

    public RegisterModel(){
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

    public boolean isRegistered(String firstName, String lastName, String role, String userName, String password, boolean admin, String question, String answer) {
        boolean Success = false;
        try {
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into Employee (firstname, lastname, role, username, password, admin, question, answer) values ('"+firstName+"','"+lastName+"','"+role+"','"+userName+"','"+password+"','"+admin+"','"+question+"','"+answer+"') ");
            Success = status > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Success;
    }

}
