package main.model.user;

import main.SQLConnection;
import main.session.UserSession;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserModel {
    Connection connection;
    UserSession userSession;

    public UserModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    //get user account detail and return as hash map
    public Map<String, String> getUserDetail(String user, String pass) throws SQLException {
        Map<String, String> userObject = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ? and password= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userObject.put("id", resultSet.getString("id"));
                userObject.put("firstname", resultSet.getString("firstname"));
                userObject.put("lastname", resultSet.getString("lastname"));
                userObject.put("username", resultSet.getString("username"));
                userObject.put("password", resultSet.getString("password"));
                userObject.put("role", resultSet.getString("role"));
                userObject.put("question", resultSet.getString("question"));
                userObject.put("answer", resultSet.getString("answer"));
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

    //check if user name already exists
    public boolean userNameExist(String username) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employee where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
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

    //check if id already exists
    public boolean idExist(int id) throws SQLException {
        boolean found = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employee where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
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

    //update account detail
    public boolean UpdateDetail(int id, String firstName, String lastName, String role, String userName, String password, String question, String answer) throws SQLException {
        boolean Success = false;
        String user = UserSession.getUserName();
        String sql = "UPDATE Employee SET id = ? , "
                + "firstname = ?, "
                + "lastname = ?, "
                + "role = ?, "
                + "username = ?, "
                + "password = ?, "
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
            statement.setString(7, question);
            statement.setString(8, answer);
            statement.setString(9, user);

            statement.executeUpdate();
            Success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Success;
    }

}
