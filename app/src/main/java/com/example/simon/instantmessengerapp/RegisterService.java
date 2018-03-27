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
 * Created by simon on 27.03.18.
 */



public class RegisterService  extends IntentService{

    public static final String ACTION_LOGIN = "com.example.simon.instantmessengerapp.action.LOGIN";
    public static final String PARAM_LOGIN_NAME = "com.example.simon.instantmessengerapp.extra.LOGIN_NAME";
    public static final String PARAM_LOGIN_PASSWORD = "com.example.simon.instantmessengerapp.extra.LOGIN_PASSWORD";

    public RegisterService(){
        super("RegisterService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //TODO: Get funtction (Check or Register)
        if(intent != null){
            final String action = intent.getAction();
            if (ACTION_LOGIN.equals(intent.getAction())) {
                final String name = intent.getStringExtra(PARAM_LOGIN_NAME);
                final String password = intent.getStringExtra(PARAM_LOGIN_PASSWORD);
                //final String function = intent.getStringExtra(PARAM_FUNCTION);
                //if (function = 1) --> handle .....
            }
        }
    }

    private void handleActionRegister(String name, String password, Intent intent) {


    }

    private void handleActionCheck(String name, String password, Intent intent) {

    }
}
