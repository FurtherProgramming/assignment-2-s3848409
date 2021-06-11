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

    //check for account with same user name and id
    public boolean accountExist(int id, String username) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employee where id = ? or username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
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

    //register an account and return a boolean
    public boolean isRegistered(int id, String firstName, String lastName, String role, String userName, String password, boolean admin, String question, String answer) {
        boolean Success = false;
        try {
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into Employee (id, firstname, lastname, role, username, password, admin, question, answer) values ('"+id+"','"+firstName+"','"+lastName+"','"+role+"','"+userName+"','"+password+"','"+admin+"','"+question+"','"+answer+"') ");
            Success = status > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Success;
    }

}
