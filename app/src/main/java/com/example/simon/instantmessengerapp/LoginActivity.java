package com.example.simon.instantmessengerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon.instantmessengerapp.core.UserAuthenticator;
import com.example.simon.instantmessengerapp.core.rest.services.UserRestClientImpl;
import com.example.simon.instantmessengerapp.model.classes.GroupImpl;
import com.example.simon.instantmessengerapp.model.classes.GroupList;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.net.Authenticator;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    public static final int RC_PLAY_SERVICES = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        checkPlayServicesAvailable();
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServicesAvailable();
    }

    /** Called when the user taps the Login button */
    public void startLogin(View view) {
        Intent intent = new Intent(this, LoginService.class);
        intent.putExtra(LoginService.PARAM_LOGIN_NAME, username.getText().toString());
        intent.putExtra(LoginService.PARAM_LOGIN_PASSWORD, password.getText().toString());
        intent.putExtra("receiver", new Receiver(new Handler()));
        intent.setAction(LoginService.ACTION_LOGIN);
        startService(intent);
    }

    public void startRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Checks if Play Services are available
    private void checkPlayServicesAvailable() {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int resultCode = availability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (availability.isUserResolvableError(resultCode)) {
                // Show dialog to resolve the error.
                availability.getErrorDialog(this, resultCode, RC_PLAY_SERVICES).show();
            } else {
                // Unresolvable error
                Toast.makeText(this, "Google Play Services error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showFailedLogin(String m) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, m, duration);
        toast.show();
    }

    private void startActivit() {
        Intent intent = new Intent(this, GroupViewActivity.class);
        startActivity(intent);
    }

    private class Receiver extends ResultReceiver {
        @SuppressLint("RestrictedApi")
        public Receiver(Handler handler) {
            super(handler);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == 1) {
                boolean success = resultData.getBoolean("success");
                startActivit();
            }
        }
    }

}
