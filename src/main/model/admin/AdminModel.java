package main.model.admin;

import main.SQLConnection;
import main.object.UserObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminModel {
    ArrayList<UserObject> userObject = new ArrayList<>();
    Connection connection;

    public AdminModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    //get an account detail from db and add to hash map and return it
    public Map<String, String> getAccountDetail(String userName) throws SQLException {
        Map<String, String> accountObject = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountObject.put("id", resultSet.getString("id"));
                accountObject.put("firstname", resultSet.getString("firstname"));
                accountObject.put("lastname", resultSet.getString("lastname"));
                accountObject.put("role", resultSet.getString("role"));
                accountObject.put("username", resultSet.getString("username"));
                accountObject.put("password", resultSet.getString("password"));
                accountObject.put("admin", resultSet.getString("admin"));
                accountObject.put("question", resultSet.getString("question"));
                accountObject.put("answer", resultSet.getString("answer"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return accountObject;
    }

    //update query for specific user or admin
    public boolean UpdateDetail(String thisUser, int id, String firstName, String lastName, String role, String userName, String password, Boolean admin, String question, String answer) throws SQLException {
        boolean Success = false;
        String sql = "UPDATE Employee SET id = ? , "
                + "firstname = ?, "
                + "lastname = ?, "
                + "role = ?, "
                + "username = ?, "
                + "password = ?, "
                + "admin = ?, "
                + "question = ?, "
                + "answer = ? "
                + "WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, role);
            statement.setString(5, userName);
            statement.setString(6, password);
            statement.setBoolean(7, admin);
            statement.setString(8, question);
            statement.setString(9, answer);
            statement.setString(10, thisUser);
            statement.executeUpdate();
            Success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Success;
    }

    //get all user from database and return as arraylist
    public ArrayList<UserObject> getAllUsers () throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Employee";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userObject.add(new UserObject(
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("username"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("admin")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return userObject;
    }

    //delete account using user name
    public boolean DeleteAcc(String userName){
        boolean success = false;
        String sql = "DELETE FROM Employee WHERE username = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    //generate all users to csv using buffered writer
    public boolean GenerateUsertoCsv(String fileName) throws IOException, SQLException {
        boolean success = false;
        String admin = "";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        fileWriter.write("EmployeeID, FirstName, LastName, UserName, Role, Admin");
        String query = "SELECT * FROM Employee";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String userName = resultSet.getString("username");
                String role = resultSet.getString("role");
                boolean boolAdmin = resultSet.getBoolean("admin");
                if(boolAdmin){
                    admin = "Admin";
                }else {
                    admin = "User";
                }
                String line = String.format("%s,%s,%s,%s,%s,%s", id, firstName, lastName, userName, role, admin);
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

}
