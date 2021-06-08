package main.session;

public final class TempUserSession {
    private static String userName;
    private static boolean admin;

    public TempUserSession(String userName, boolean admin) {
        TempUserSession.userName = userName;
        TempUserSession.admin = admin;
    }

    public static String getUserName() { return userName; }

    public static boolean getAdmin() { return admin; }

    public static void cleanUserSession() {
        userName = null;
        admin = false;
    }

}
