package main.model;

public final class UserSession {
    private static UserSession instance;
    private static String userName;
    private static String password;
    private static boolean admin;

    private UserSession(String userName, String password, boolean admin) {
        UserSession.userName = userName;
        UserSession.password = password;
        UserSession.admin = admin;
    }

    public static UserSession getInstance(String userName, String password, boolean admin) {
        if(instance == null) {
            instance = new UserSession(userName, password, admin);
        }else{
            UserSession.userName = userName;
            UserSession.password = password;
            UserSession.admin = admin;
        }
        return instance;
    }

    public static String getUserName() { return userName; }

    public static String getPassword() {
        return password;
    }

    public static boolean getAdmin() { return admin; }

    public static void cleanUserSession() {
        userName = "";
        password = "";
        admin = false;
    }

}