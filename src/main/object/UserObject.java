package main.object;

public final class UserObject {
    private static UserObject user;
    private static String userName;
    private static String role;
    private static boolean admin;

    private UserObject(String userName, String role, boolean admin) {
        UserObject.userName = userName;
        UserObject.role = role;
        UserObject.admin = admin;
    }

    public static UserObject getUser(String userName, String role, boolean admin) {
        if(user == null) {
            user = new UserObject(userName, role, admin);
        }else{
            UserObject.userName = userName;
            UserObject.role = role;
            UserObject.admin = admin;
        }
        return user;
    }

    public static String getUserName() { return userName; }

    public static String getRole() {
        return role;
    }

    public static boolean getAdmin() { return admin; }

    public static void cleanUserSession() {
        userName = null;
        role = null;
        admin = false;
    }

}
