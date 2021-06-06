package main.object;

public class UserObject {
    private UserObject user;
    private String firstName;
    private String lastName;
    private String userName;
    private String role;
    private boolean admin;

    public UserObject(String firstName, String lastName, String userName, String role, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
        this.admin = admin;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getUserName() { return userName; }

    public String getRole() {
        return role;
    }

    public Boolean getAdminAsBool() {
        return admin;
    }

    public String getAdmin() {
        if(admin){
            return "Admin";
        }else{
            return "User";
        }
    }

    public void cleanUserSession() {
        firstName = null;
        lastName = null;
        userName = null;
        role = null;
        admin = false;
    }

}
