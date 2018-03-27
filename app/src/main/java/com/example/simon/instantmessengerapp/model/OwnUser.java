package com.example.simon.instantmessengerapp.model;

/**
 * Created by Manuel on 27.03.2018.
 */

public class OwnUser {
    private static OwnUser instance = null;

    private int userId;
    private String username;
    private String password;
    private boolean isCreated = false;

    private OwnUser() {

    }

    /**
     * create the User whose logged in
     *
     * @param id userID
     * @param n  userName
     * @param pw userPassword
     */
    public void createUser(int id, String n, String pw) {
        if (!isCreated) {
            userId = id;
            username = n;
            password = pw;
            isCreated = true;
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getUserStringId() {
        return String.format("%d", userId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCreated() {
        return isCreated;
    }

    /**
     * gets the instance of the Singelton Object
     *
     * @return the instance
     */
    public static OwnUser getInstance() {
        if (instance == null) {
            instance = new OwnUser();
        }
        return instance;
    }
}
