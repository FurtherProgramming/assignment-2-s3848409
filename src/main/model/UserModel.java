package main.model;

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

    public boolean UpdateDetail(String firstName, String lastName, String role, String userName, String password, String question, String answer) throws SQLException {
        boolean Success = false;
        String user = UserSession.getUserName();
        String sql = "UPDATE Employee SET firstname = ? , "
                + "lastname = ?, "
                + "role = ?, "
                + "username = ?, "
                + "password = ?, "
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
            statement.setString(6, question);
            statement.setString(7, answer);
            statement.setString(8, user);

            statement.executeUpdate();
            Success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Success;
    }

}
