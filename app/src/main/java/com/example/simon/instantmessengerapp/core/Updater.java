package com.example.simon.instantmessengerapp.core;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.simon.instantmessengerapp.core.rest.services.GroupRestClientImpl;
import com.example.simon.instantmessengerapp.core.rest.services.UserRestClientImpl;
import com.example.simon.instantmessengerapp.database.DatabaseHelper;
import com.example.simon.instantmessengerapp.model.classes.GroupImpl;
import com.example.simon.instantmessengerapp.model.interfaces.Group;
import com.example.simon.instantmessengerapp.model.interfaces.Message;

/**
 * Created by simon on 22.03.18.
 */

public class Updater {
    private GroupRestClientImpl grcl;
    private UserRestClientImpl urcl;
    public Updater() {
        grcl = new GroupRestClientImpl();
        urcl = new UserRestClientImpl();
    }

    public void addGroup(View view, Group g) {
        Group newGroup = grcl.addGroup(g);
        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
        ContentValues groupValues = new ContentValues();
        groupValues.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, newGroup.getGroupName());
        groupValues.put(DatabaseHelper.GROUP_ID_FIELD_NAME, newGroup.getGroupId());

        db.insert(DatabaseHelper.GROiUP_TABLE_NAME, null, groupValues);
        db.close();
    }

    public void changeGroup(View view, Group g) {
        grcl.changeGroup(Integer.toString(g.getGroupId()),g);
        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
        ContentValues groupValues = new ContentValues();
        //groupValues.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, serverGroup.getGroupName());
        //groupValues.put(DatabaseHelper.GROUP_ID_FIELD_NAME, serverGroup.getGroupId());
        // TODO: Change in local Database
        //db.insert(DatabaseHelper.GROUP_TABLE_NAME, null, groupValues);
        db.close();
    }

    public void deleteGroup(View view, Group g) {
        grcl.removeGroup(Integer.toString(g.getGroupId()));
        //TODO: Gruppe lokal löschen
    }

    public void sendMessage(View view, Message m) {
        grcl.postMessage(m);

        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, m.getContent());
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, m.getGroup());
        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, m.getUser());
        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);
        db.close();
    }


}
