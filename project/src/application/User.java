package application;

public class User {
    private double id;
    private String name;
    private String password;

    protected User(double id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    // Need to implement login / logout logic
    public boolean logout() {
        return false;
    }
    public boolean login(String passwordAttempt) {
        return this.password.equals(passwordAttempt);
    }

    // Setter methods take in password for verification
    public boolean setId(String password, double newId) {
        if (this.password.equals(password)) {
            this.id = newId;
            return true;
        } else {
            return false;
        }
    }
    public boolean setName(String password, String name) {
        if (this.password.equals(password)) {
            this.name = name;
            return true;
        } else {
            return false;
        }
    }

    public boolean setPassword(String password, String newPassword) {
        if (this.password.equals(password)) {
        	this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }

    // Getter methods
    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
