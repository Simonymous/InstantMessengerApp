package com.example.simon.instantmessengerapp.core;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.simon.instantmessengerapp.database.DatabaseHelper;
import com.example.simon.instantmessengerapp.model.classes.GroupImpl;
import com.example.simon.instantmessengerapp.model.interfaces.Group;
import com.example.simon.instantmessengerapp.model.interfaces.Message;

/**
 * Created by simon on 22.03.18.
 */

public class Updater {
    //TODO: User/Group Restobjekte anlegen
    public Updater() {
        //TODO: User/Group Rest Objekte instanzieren
    }

    public void addGroup(View view, Group g) {
        //TODO: Gruppe an rest übergeben und returnen
        Group serverGroup = new GroupImpl();
        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
        ContentValues groupValues = new ContentValues();
        groupValues.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, serverGroup.getGroupName());
        groupValues.put(DatabaseHelper.GROUP_ID_FIELD_NAME, serverGroup.getGroupId());

        db.insert(DatabaseHelper.GROUP_TABLE_NAME, null, groupValues);
        db.close();
    }

    public void editGroup(View view, Group g) {
        //TODO: Gruppe an rest übergeben und returnen
        Group serverGroup = new GroupImpl();
        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
        ContentValues groupValues = new ContentValues();
        groupValues.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, serverGroup.getGroupName());
        groupValues.put(DatabaseHelper.GROUP_ID_FIELD_NAME, serverGroup.getGroupId());

        db.insert(DatabaseHelper.GROUP_TABLE_NAME, null, groupValues);
        db.close();
    }

    public void deleteGroup(View view, Group g) {
        //TODO: Gruppe an rest übergeben und lokal löschen
    }

    public void sendMessage(View view, Message m) {
        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, m.getContent());
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, m.getGroup());
        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, m.getUser());
        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);
        db.close();
    }


}
