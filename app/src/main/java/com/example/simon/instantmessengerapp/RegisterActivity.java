package com.example.simon.instantmessengerapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void handleRegister(View view) {
      //Register User
        finish();
    }

    public void handleCheck(View view) {
      if(true) {
          showCheckToast("Nutzername verfügbar!");
      } else {
          showCheckToast("Nutzername nicht verfügbar!");
      }
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
