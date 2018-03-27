package com.example.simon.instantmessengerapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

    private void handleActionLogin(String username, String password, Intent intent) {
        Log.i("ttt","handle login");
        UserRestClientImpl urcl = new UserRestClientImpl();
        if (urcl.getUserByName(username) != null) {
            Log.i("ttt","username not null");
            UserAuthenticator a = new UserAuthenticator();
            if (a.authenticateUser(username, password)) {
                Log.i("ttt","user is authetificated");
                ResultReceiver rc = (ResultReceiver) intent.getParcelableExtra("receiver");
                Bundle bundle = new Bundle();
                bundle.putBoolean("success", true);
                rc.send(1, bundle);
//                Intent intent = new Intent(, GroupViewActivity.class);
//                startActivity(intent);
            } else {
                Log.i("ttt","password falsch");
                showFailedLogin("Nutzername oder Passwort falsch!");
            }
        } else {
            showFailedLogin("Nutzer existiert nicht!");

        }
    }

    public void showFailedLogin(String m) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, m, duration);
        toast.show();
    }
}
