package com.example.simon.instantmessengerapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.instantmessengerapp.model.classes.GroupImpl;
import com.example.simon.instantmessengerapp.model.classes.GroupList;

import java.util.ArrayList;

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

    public void populateListView() {
        
    }

}
