package com.example.simon.instantmessengerapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simon.instantmessengerapp.database.adapter.GroupCursorAdapter;
import com.example.simon.instantmessengerapp.database.DatabaseHelper;
import com.example.simon.instantmessengerapp.gcmClasses.GcmRegister;
import com.example.simon.instantmessengerapp.model.OwnUser;

public class GroupViewActivity extends AppCompatActivity implements OnClickListener,OnItemClickListener,OnItemLongClickListener {
    private ListView groupListView;
    private FloatingActionButton fab;
    private GroupCursorAdapter groupCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        groupListView = (ListView) findViewById(R.id.groupListView);

        Intent intent = new Intent(this, GroupViewService.class);
        intent.setAction(GroupViewService.ACTION_POLLING);
        intent.putExtra("receiver", new Receiver(new Handler()));
        startService(intent);

        //Intent i= new Intent(getApplicationContext(), GcmRegister.class);
        //initTestValues();
        //testDb();
        populateListView();

        groupListView.setOnItemClickListener(this);
        groupListView.setOnItemLongClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, AddChatActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        populateListView();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
             TextView clickedItemGroupId = (TextView) view.findViewById(R.id.groupId); //Get Name of clicked group

             Intent modify_intent = new Intent(getApplicationContext(), ChatViewActivity.class);
             modify_intent.putExtra("groupIdCl",(String)clickedItemGroupId.getText()); //Deliver group Name to new activity
             startActivity(modify_intent);
        } catch (NumberFormatException ex) {
            //Print Error
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view, int i, long l) {
        try {
            TextView longClickedItemGroupId = (TextView) view.findViewById(R.id.groupId); //Get Id of clicked group
            TextView longClickedItemGroupName = (TextView) view.findViewById(R.id.groupName); //Get Name of clicked group

            String[] args = new String[] {
                    (String)longClickedItemGroupId.getText(),
                    (String)longClickedItemGroupName.getText()
            };
            Intent modify_intent = new Intent(getApplicationContext(), AddChatActivity.class);
            //modify_intent.putExtra("groupIdNCl",args); //Deliver group Name+Id to new activity
            startActivity(modify_intent);
        } catch (NumberFormatException ex) {
            //Print Error
        }
      return true;
    }

    private void populateListView() {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor result  = db.query(DatabaseHelper.GROUP_TABLE_NAME, null, null, null, null, null, null, null);
        groupCursorAdapter = new GroupCursorAdapter(this, result);
        groupListView.setAdapter(groupCursorAdapter);
        //result.close();
        db.close();
    }

    public void initTestValues(){
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        ContentValues values;

        //User
        values = new ContentValues();
        values.put(DatabaseHelper.USER_NAME_FIELD_NAME, "Hans");
        db.insert(DatabaseHelper.USER_TABLE_NAME, null, values);
        values.put(DatabaseHelper.USER_NAME_FIELD_NAME, "Peter");
        db.insert(DatabaseHelper.USER_TABLE_NAME, null, values);
        values.put(DatabaseHelper.USER_NAME_FIELD_NAME, "Bernd");
        db.insert(DatabaseHelper.USER_TABLE_NAME, null, values);

        //Group
        values = new ContentValues();
        values.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, "Gruppe1");
        db.insert(DatabaseHelper.GROUP_TABLE_NAME, null, values);
        values.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, "Gruppe2");
        db.insert(DatabaseHelper.GROUP_TABLE_NAME, null, values);
        values.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, "Gruppe3");
        db.insert(DatabaseHelper.GROUP_TABLE_NAME, null, values);

        //Messages for Gruppe 1
        values = new ContentValues();
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, "Hallo wie gehts?");
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, 1);
        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, 1);
        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, "Gut");
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, 1);
        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, 2);
        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);

        //Messages for Gruppe 2
        values = new ContentValues();
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, "Hallo wie gehts in 2?");
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, 2);
        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, 1);
        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, "Auch Gut");
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, 2);
        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, 2);
        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);


        db.close();
    }

    public void testDb() {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor result;

        result  = db.query(DatabaseHelper.USER_TABLE_NAME, null, null, null, null, null, null, null);
        if(result.moveToFirst()) {
            int nameIndex = result.getColumnIndex(DatabaseHelper.USER_NAME_FIELD_NAME);
            do {
                Log.i("UserName", result.getString(nameIndex));
            } while (result.moveToNext());
        }
        result.close();

        result  = db.query(DatabaseHelper.GROUP_TABLE_NAME, null, null, null, null, null, null, null);
        if(result.moveToFirst()) {
            int nameIndex = result.getColumnIndex(DatabaseHelper.GROUP_NAME_FIELD_NAME);
            do {
                Log.i("GruppenName", result.getString(nameIndex));
            } while (result.moveToNext());
        }
        result.close();

        result  = db.query(DatabaseHelper.MESSAGE_TABLE_NAME, null, null, null, null, null, null, null);
        if(result.moveToFirst()) {
            int contentIndex = result.getColumnIndex(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME);
            do {
                Log.i("Content", result.getString(contentIndex));
            } while (result.moveToNext());
        }
        result.close();

        db.close();
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
                if(success) populateListView();
                //else showFailedLogin(resultData.getString("error"));
            }
        }
    }

}
