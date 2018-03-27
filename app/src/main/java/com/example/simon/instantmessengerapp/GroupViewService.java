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
import com.example.simon.instantmessengerapp.model.interfaces.Group;

import java.util.List;

/**
 * Created by Manuel on 27.03.2018.
 */

public class GroupViewService extends IntentService{

    public static final String ACTION_POLLING = "com.example.simon.instantmessengerapp.action.POLLING";
    public static final String PARAM_LOGIN_NAME = "com.example.simon.instantmessengerapp.extra.LOGIN_NAME";
    public static final String PARAM_LOGIN_PASSWORD = "com.example.simon.instantmessengerapp.extra.LOGIN_PASSWORD";

    public GroupViewService(){
        super("GroupViewService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            final String action = intent.getAction();
            if (ACTION_POLLING.equals(intent.getAction())) {

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
            List<Integer> groups;
            groups = urci.getGroupsOfUser(Integer.toString(OwnUser.getInstance().getUserId()));
            Log.i("iii", "test");
            for(Integer i : groups){
                Log.i("iii", "group id: " + i.toString());
                SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
                String whereClause = DatabaseHelper.GROUP_ID_FIELD_NAME+" = ?";
                String[] whereArgs = new String []{
                        i.toString()
                };
                Cursor result  = db.query(DatabaseHelper.GROUP_TABLE_NAME, null, whereClause, whereArgs, null, null, null, null);
                if (result.getCount() == 0){
                    Log.i("iii", "result in null neue gruppe");
                    Group group = grci.getGroupById(i.toString());
                    Log.i("iii", "group name: " +group.getGroupName());
                    new Updater().addLocalGroup(getApplicationContext(), group);
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
