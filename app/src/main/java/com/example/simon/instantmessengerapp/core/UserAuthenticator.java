package com.example.simon.instantmessengerapp.core;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;

import com.example.simon.instantmessengerapp.core.rest.services.UserRestClientImpl;
import com.example.simon.instantmessengerapp.database.DatabaseHelper;
import com.example.simon.instantmessengerapp.model.OwnUser;
import com.example.simon.instantmessengerapp.model.interfaces.User;
import com.example.simon.instantmessengerapp.model.classes.UserImpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jersey.repackaged.com.google.common.hash.Hashing;

//import com.google.common.hash.Hashing;


/**
 * Created by simon on 22.03.18.
 */

public class UserAuthenticator {
    private UserRestClientImpl urci;

    public UserAuthenticator() {
        urci = UserRestClientImpl.getInstance();
    }

    public boolean authenticateUser(String username, String password) {
        User user = urci.getTheUserByName(username);
        //Log.i("authetiii","user" + user.getUsername());
        if (user == null) {
            return false;
        } else {
            String hashedPassword = null;//Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString(); //TODO: Hashing!!
            try {
                hashedPassword = SHA256(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            }
            if (user.getPassword().equals(hashedPassword)) {
                if (!OwnUser.getInstance().isCreated())
                OwnUser.getInstance().createUser(user.getUserId(), user.getUsername(), user.getPassword());
                return true;
            } else {
                if (!OwnUser.getInstance().isCreated())
                    OwnUser.getInstance().createUser(user.getUserId(), user.getUsername(), user.getPassword());
                return true;
            }
        }
    }

    public static String SHA256(String text) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(text.getBytes());
        byte[] digest = md.digest();

        return Base64.encodeToString(digest, Base64.DEFAULT);
    }

    public boolean doesUserExist(String name) {
        User user = urci.getTheUserByName(name);

        return user != null;
    }
}
