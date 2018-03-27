package com.example.simon.instantmessengerapp;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.example.simon.instantmessengerapp.core.Updater;
import com.example.simon.instantmessengerapp.core.rest.services.GroupRestClientImpl;
import com.example.simon.instantmessengerapp.core.rest.services.UserRestClientImpl;
import com.example.simon.instantmessengerapp.database.DatabaseHelper;
import com.example.simon.instantmessengerapp.model.OwnUser;
import com.example.simon.instantmessengerapp.model.classes.MessageImpl;
import com.example.simon.instantmessengerapp.model.interfaces.Group;
import com.example.simon.instantmessengerapp.model.interfaces.Message;

import java.util.List;

/**
 * Created by simon on 27.03.18.
 */

public class ChatViewService extends IntentService{

    public static final String ACTION_POLLING = "com.example.simon.instantmessengerapp.action.POLLING";
    public String groupId;
    public ChatViewService(){
        super("ChatViewService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        groupId = intent.getStringExtra("groupID");
        Log.i("aaaaa", "handle intent");
        if(intent != null){
            Log.i("aaaaa", "intent not null");
            final String action = intent.getAction();
            if (ACTION_POLLING.equals(intent.getAction())) {
                Log.i("aaaaa", "action equals");
                handleActionPolling(intent);
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void handleActionPolling(Intent intent) {
        ResultReceiver rs = intent.getParcelableExtra("receiver");
        while(true){
            UserRestClientImpl urci = UserRestClientImpl.getInstance();
            GroupRestClientImpl grci = GroupRestClientImpl.getInstance();
            List<Message> messagesOfGroup;
            messagesOfGroup= grci.getMessagesOfGroup(groupId,"");
            for(Message m : messagesOfGroup){
                SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
                String whereClause = DatabaseHelper.MESSAGE_ID_FIELD_NAME+" = ?";
                String[] whereArgs = new String []{
                        Long.toString(m.getMessageId())
                };
                Cursor result  = db.query(DatabaseHelper.MESSAGE_TABLE_NAME, null, whereClause, whereArgs, null, null, null, null);
                if (result.getCount() == 0){
                    //Group group = grci.getGroupById(i.toString());
                    new Updater().addLocalMessage(getApplicationContext(), m);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", true);
                    rs.send(1, bundle);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

