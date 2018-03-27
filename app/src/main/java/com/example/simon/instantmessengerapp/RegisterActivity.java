package com.example.simon.instantmessengerapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon.instantmessengerapp.core.UserAuthenticator;
import com.example.simon.instantmessengerapp.core.rest.interfaces.UserRestClient;
import com.example.simon.instantmessengerapp.core.rest.services.UserRestClientImpl;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName;
    private EditText registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void handleRegister(View view) {
        //Intent.extra........ TODO: Move to Service
        //urcl.addUser(registerName.getText().toString(),registerPassword.getText().toString());
        finish();
    }

    public void handleCheck(View view) {
        UserAuthenticator ua = new UserAuthenticator(); //TODO: Move to Service
        //Intent.extra........
//      if(ua.doesUserExist(registerName.getText().toString())) {
//          showCheckToast("Nutzername nicht verfügbar!");
//      } else {
//          showCheckToast("Nutzername verfügbar!");
//      }
    }

    public void handleCancel(View view) {
      finish();
    }

    private void showCheckToast(String m) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, m, duration);
        toast.show();
    }
}
