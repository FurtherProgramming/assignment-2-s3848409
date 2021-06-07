package main.model.admin;

import main.SQLConnection;
import main.object.BookingObject;
import main.object.UserObject;
import main.session.TempUserSession;
import main.session.UserSession;
import org.omg.CORBA.Environment;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
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

    public boolean UpdateDetail(String thisUser, String firstName, String lastName, String role, String userName, String password, Boolean admin, String question, String answer) throws SQLException {
        boolean Success = false;

        String sql = "UPDATE Employee SET firstname = ? , "
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
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, role);
            statement.setString(4, userName);
            statement.setString(5, password);
            statement.setBoolean(6, admin);
            statement.setString(7, question);
            statement.setString(8, answer);
            statement.setString(9, thisUser);
            statement.executeUpdate();
            Success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Success;
    }

    public ArrayList<UserObject> getAllUsers () throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Employee";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userObject.add(new UserObject(resultSet.getString("firstname"),
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

    public boolean GenerateUsertoCsv(String fileName) throws IOException, SQLException {
        boolean success = false;
        String admin = "";
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        fileWriter.write("FirstName, LastName, UserName, Role, Admin");
        String query = "SELECT * FROM Employee";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
                String line = String.format("%s,%s,%s,%s,%s", firstName, lastName, userName, role, admin);
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
