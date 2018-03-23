package com.example.simon.instantmessengerapp.gcmClasses;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Christian on 23.03.2018.
 */

public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("body");

        assert message != null;
        if(message.equals("newMessages")){
            //getMessages() aufrufen
        }
    }
}
