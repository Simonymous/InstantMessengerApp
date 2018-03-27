package com.example.simon.instantmessengerapp;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;

import com.example.simon.instantmessengerapp.core.UserAuthenticator;
import com.example.simon.instantmessengerapp.core.rest.interfaces.UserRestClient;
import com.example.simon.instantmessengerapp.core.rest.services.UserRestClientImpl;
import com.example.simon.instantmessengerapp.model.interfaces.User;

/**
 * Created by simon on 27.03.18.
 */



public class RegisterService  extends IntentService{

    public static final String ACTION_REGISTER = "com.example.simon.instantmessengerapp.action.REGISTER";
    public static final String PARAM_REGISTER_NAME = "com.example.simon.instantmessengerapp.extra.REGISTER_NAME";
    public static final String PARAM_REGISTER_PASSWORD = "com.example.simon.instantmessengerapp.extra.REGISTER_PASSWORD";
    public static final String PARAM_REGISTER_FUNCTION = "com.example.simon.instantmessengerapp.extra.REGISTER_FUNCTION";

    public RegisterService(){
        super("RegisterService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //TODO: Get funtction (Check or Register)
        if(intent != null){
            final String action = intent.getAction();
            if (ACTION_REGISTER.equals(intent.getAction())) {
                final String name = intent.getStringExtra(PARAM_REGISTER_NAME);
                final String password = intent.getStringExtra(PARAM_REGISTER_PASSWORD);
                final String function = intent.getStringExtra(PARAM_REGISTER_FUNCTION);
                if (function.equals("check")) {
                    handleActionCheck(name, password, intent);
                } else {
                    handleActionRegister(name, password, intent);
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void handleActionRegister(String name, String password, Intent intent) {
        UserRestClientImpl urci = new UserRestClientImpl();
        ResultReceiver rc = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        if(urci.getUserByName(name) != null) {
            User user = urci.addUser(name,password);
            //TODO: ADD User to local db for ID and....
            bundle.putBoolean("success",true);
            bundle.putString("function","register");

        } else {
            bundle.putBoolean("error",false);
            bundle.putString("error","Nutzername nicht verfügbar");
        }
        rc.send(1, bundle);

    }

    @SuppressLint("RestrictedApi")
    private void handleActionCheck(String name, String password, Intent intent) {
        UserRestClientImpl urci = new UserRestClientImpl();
        ResultReceiver rc = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        if (urci.getUserByName(name) != null) {
          bundle.putBoolean("success",true);
          bundle.putString("function","check");
        } else {
            bundle.putBoolean("error",false);
            bundle.putString("error","Nutzername nicht verfügbar");
        }
        rc.send(1, bundle);
    }
}
