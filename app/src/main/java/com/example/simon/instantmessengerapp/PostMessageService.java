package com.example.simon.instantmessengerapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.simon.instantmessengerapp.core.rest.services.GroupRestClientImpl;
import com.example.simon.instantmessengerapp.model.OwnUser;
import com.example.simon.instantmessengerapp.model.classes.MessageImpl;

/**
 * Created by Manuel on 27.03.2018.
 */

public class PostMessageService extends IntentService{
    public static final String ACTION_POST_MESSAGE = "com.example.simon.instantmessengerapp.action.POST_MESSAGE";
    public String groupId;
    public PostMessageService(){
        super("PostMessageService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        groupId = intent.getStringExtra("groupID");
        Log.i("aaaaa", "handle intent");
        if(intent != null){
            Log.i("aaaaa", "intent not null");
            final String action = intent.getAction();
            if(ACTION_POST_MESSAGE.equals(intent.getAction())){
                handleActionPostMessage(intent);
            }
        }
    }

    private void handleActionPostMessage(Intent intent) {
        Log.i("eeeee", "handle post message");
        MessageImpl msg = new MessageImpl();
        msg.setContent(intent.getStringExtra("content"));
        msg.setGroup(Integer.parseInt(intent.getStringExtra("group")));
        msg.setUser(OwnUser.getInstance().getUserId());
        GroupRestClientImpl.getInstance().postMessage(msg);
    }
}
