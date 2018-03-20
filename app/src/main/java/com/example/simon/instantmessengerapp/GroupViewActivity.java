package com.example.simon.instantmessengerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simon.instantmessengerapp.model.interfaces.classes.Chat;
import com.example.simon.instantmessengerapp.model.interfaces.classes.ChatList;

import java.sql.SQLException;
import java.util.ArrayList;

public class GroupViewActivity extends AppCompatActivity implements OnItemClickListener {
    private ListView groupList;
    private ArrayAdapter<String> groupAdapter;
    public ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(this, AddChatActivity.class);
                //startActivity(intent);
            }
        });

        groupList = (ListView) findViewById(R.id.groupListView);
        populateListView();

        groupList.setOnItemClickListener(this);

    }

    public void addChat(String name) {
        items.add(name);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            //int todoID_val = Integer.parseInt(todoID.getText().toString()); Get Click View

            // Intent modify_intent = new Intent(getApplicationContext(), EditTodoActivity.class); Open Chat
            //startActivity(modify_intent); //Starte chat
        } catch (NumberFormatException ex) {
            //Print Error
        }
    }

    public void populateListView() {
        items = new ArrayList<>();

        items.add("Test 1");
        items.add("Test 2");
        items.add("Test 3");

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        groupList.setAdapter(itemsAdapter);

    }

}
