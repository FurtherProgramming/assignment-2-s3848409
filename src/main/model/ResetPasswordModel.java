package main.model;

import main.SQLConnection;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordModel {
    UserSession userSession;
    Connection connection;
    String secretQuestion;

    public ResetPasswordModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public Boolean userFound(String user) throws SQLException {
        boolean userFound = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userSession = UserSession.getInstance(user, "", false);
                userFound = true;
            }
        } catch (Exception e) {
            userFound = false;
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return userFound;
    }

    public String getSecretQuestion() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UserSession.getUserName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                secretQuestion = resultSet.getString("question");
            }
        } catch (Exception e) {
            secretQuestion = "null";
            e.printStackTrace();
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return secretQuestion;
    }

    public Boolean compareAnswer(String answer) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select answer from employee where question = ? and answer = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, secretQuestion);
            preparedStatement.setString(2, answer);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            return false;
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
    }

    public String getNewPassword() throws SQLException {
        String username = UserSession.getUserName();
        String newPassword = generatePassword();
        String sql = "UPDATE Employee SET password = ?" + "WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newPassword;
    }

    public String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&()[{]}<>/?";
        return RandomStringUtils.random( 10, characters );
    }
}
