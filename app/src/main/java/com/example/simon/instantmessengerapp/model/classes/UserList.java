package com.example.simon.instantmessengerapp.model.classes;

import com.example.simon.instantmessengerapp.model.interfaces.User;

import java.util.ArrayList;

public class UserList {

    private static UserList userList = null;
    ArrayList<User> serverUsers;

    private UserList() {
    }

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    public void addUser(User u) {
            serverUsers.add(u);
    }

    public void deleteUser(User u) {
        serverUsers.remove(u);
    }

    public void deleteUser(int id) {
        for (User u : serverUsers) {
            if (u.getUserId() == id) {
                deleteUser(u);
            }
        }
    }

    public User getUser(int id) {
        for (User u : serverUsers) {
            if (u.getUserId() == id) return u;
        }
        return null;
    }

    public ArrayList<User> getServerUsers() {
        return serverUsers;
    }

    public boolean containsUser(User u) {
        return serverUsers.contains(u);
    }

    public boolean containsUser(int id) {
        for (User u : serverUsers) {
            if (u.getUserId() == id) {
                return true;
            }
        }
        return false;
    }
}
