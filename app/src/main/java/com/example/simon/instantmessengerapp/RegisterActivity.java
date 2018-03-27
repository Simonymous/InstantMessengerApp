package com.example.simon.instantmessengerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;

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
        Intent intent = new Intent(this, RegisterService.class);
        intent.putExtra(RegisterService.PARAM_REGISTER_NAME, registerName.getText().toString());
        intent.putExtra(RegisterService.PARAM_REGISTER_PASSWORD, registerPassword.getText().toString());
        intent.putExtra(RegisterService.PARAM_REGISTER_FUNCTION, "register");
        intent.putExtra("receiver", new Receiver(new Handler()));
        intent.setAction(RegisterService.ACTION_REGISTER);
        startService(intent);
    }

    public void handleCheck(View view) {
        Intent intent = new Intent(this, RegisterService.class);
        intent.putExtra(RegisterService.PARAM_REGISTER_NAME, registerName.getText().toString());
        intent.putExtra(RegisterService.PARAM_REGISTER_PASSWORD, registerPassword.getText().toString());
        intent.putExtra(RegisterService.PARAM_REGISTER_FUNCTION, "check");
        intent.putExtra("receiver", new Receiver(new Handler()));
        intent.setAction(RegisterService.ACTION_REGISTER);
        startService(intent);
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

    private void returnToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
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
//                boolean success = resultData.getBoolean("success");
//                if(success) startActivit();
//                else showFailedLogin(resultData.getString("error"));
                if(resultData.getBoolean("success")){
                    if(resultData.getString("function").equals("register")){

                    }
                    if(resultData.getString("function").equals("check")){

                    }
                }
            }
        }
    }
}
