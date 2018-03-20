package com.example.simon.instantmessengerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simon.instantmessengerapp.database.adapter.GroupCursorAdapter;
import com.example.simon.instantmessengerapp.database.DatabaseHelper;

public class GroupViewActivity extends AppCompatActivity implements OnClickListener,OnItemClickListener {
    private ListView groupListView;
    private ArrayAdapter<String> groupAdapter;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        groupListView = (ListView) findViewById(R.id.groupListView);

        initTestValues();
        testDb();
        populateListView();

        groupListView.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, AddChatActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            //TextView groupID = (TextView) view.findViewById(R.id.view_todo_id); Get Id of clicked Group
            // int groupID_val = Integer.parseInt(groupID.getText().toString()); parse it to Itenger

             Intent modify_intent = new Intent(getApplicationContext(), ChatViewActivity.class);
             //modify_intent.putExtra(groupID_val); //Deliver group id to new activity
             startActivity(modify_intent);
        } catch (NumberFormatException ex) {
            //Print Error
        }
    }

    private void populateListView() {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor result  = db.query(DatabaseHelper.GROUP_TABLE_NAME, null, null, null, null, null, null, null);
        ListView lvGroups = (ListView) findViewById(R.id.groupListView);
        GroupCursorAdapter groupCursorAdapter = new GroupCursorAdapter(this, result);
        lvGroups.setAdapter(groupCursorAdapter);
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

        //Messages
        values = new ContentValues();
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, "Hallo wie gehts?");
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, 1);
        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, 1);
        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);
        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, "Gut");
        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, 1);
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

}
