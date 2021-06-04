package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminModel {
    Connection connection;
    UserSession userSession;

    public AdminModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public Map<String, String> getAdminDetail(String user, String pass) throws SQLException {
        Map<String, String> adminObject = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ? and password= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                adminObject.put("firstname", resultSet.getString("firstname"));
                adminObject.put("lastname", resultSet.getString("lastname"));
                adminObject.put("role", resultSet.getString("role"));
                adminObject.put("username", resultSet.getString("username"));
                adminObject.put("password", resultSet.getString("password"));
                if (Integer.parseInt(resultSet.getString("admin")) == 1){
                    adminObject.put("admin", "true");
                }else{
                    adminObject.put("admin", "false");
                }
                adminObject.put("question", resultSet.getString("question"));
                adminObject.put("answer", resultSet.getString("answer"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return adminObject;
    }

    public boolean UpdateDetail(String firstName, String lastName, String role, String userName, String password, Boolean admin, String question, String answer) throws SQLException {
        boolean Success = false;
        String user = UserSession.getUserName();
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
            statement.setString(9, user);

            statement.executeUpdate();
            Success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Success;
    }

    public boolean DeleteAcc(String userName){
        boolean Success = false;
        String sql = "DELETE FROM Employee WHERE username = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.executeUpdate();
            Success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Success;
    }
}
