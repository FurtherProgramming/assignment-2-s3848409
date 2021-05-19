package main.model;

public final class UserSession {

    private static UserSession instance;
    private static String userName;
    private static String password;

    private UserSession(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static UserSession getInstance(String userName, String password) {
        if(instance == null) {
            instance = new UserSession(userName, password);
        }else{
            instance.userName = userName;
            instance.password = password;
        }
        return instance;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPrivileges() {
        return password;
    }

    public static void cleanUserSession() {
        userName = "";
        password = "";
    }

}