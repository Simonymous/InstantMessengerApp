package com.example.simon.instantmessengerapp;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;

import com.example.simon.instantmessengerapp.core.UserAuthenticator;
import com.example.simon.instantmessengerapp.core.rest.services.UserRestClientImpl;

/**
 * Created by Manuel on 27.03.2018.
 */

public class LoginService extends IntentService{
    public static final String ACTION_LOGIN = "com.example.simon.instantmessengerapp.action.LOGIN";
    public static final String PARAM_LOGIN_NAME = "com.example.simon.instantmessengerapp.extra.LOGIN_NAME";
    public static final String PARAM_LOGIN_PASSWORD = "com.example.simon.instantmessengerapp.extra.LOGIN_PASSWORD";

    public LoginService(){
        super("LoginService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            final String action = intent.getAction();
            if (ACTION_LOGIN.equals(intent.getAction())) {
                final String name = intent.getStringExtra(PARAM_LOGIN_NAME);
                final String password = intent.getStringExtra(PARAM_LOGIN_PASSWORD);
                handleActionLogin(name, password, intent);
            }
        }
    }


    @SuppressLint("RestrictedApi")
    private void handleActionLogin(String username, String password, Intent intent) {
        UserRestClientImpl urcl = UserRestClientImpl.getInstance();
        ResultReceiver rc = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        if (urcl.getUserByName(username) != null) {
            UserAuthenticator a = new UserAuthenticator();
            if (a.authenticateUser(username, password)) {

                bundle.putBoolean("success", true);

            } else {
                bundle.putBoolean("success", false);
                bundle.putString("error", "Nutzername oder Passwort falsch!");
            }
        } else {
            bundle.putBoolean("success", false);
            bundle.putString("error", "Nutzername existiert nicht!");
        }
        rc.send(1, bundle);
    }
}
