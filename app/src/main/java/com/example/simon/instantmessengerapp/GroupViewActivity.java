package com.example.simon.instantmessengerapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simon.instantmessengerapp.model.classes.GroupImpl;
import com.example.simon.instantmessengerapp.model.classes.GroupList;

import java.util.ArrayList;

public class GroupViewActivity extends AppCompatActivity implements OnItemClickListener {
    private ListView groupListView;
    private ArrayAdapter<String> groupAdapter;

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

        groupListView = (ListView) findViewById(R.id.groupListView);
        populateListView();

        groupListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            GroupList.getInstance().addGroup(new GroupImpl("Test234"));
            Toast.makeText(getApplicationContext(), "Hallo", Toast.LENGTH_SHORT).show();

            //int todoID_val = Integer.parseInt(todoID.getText().toString()); Get Click View

            // Intent modify_intent = new Intent(getApplicationContext(), EditTodoActivity.class); Open Chat
            //startActivity(modify_intent); //Starte chat
        } catch (NumberFormatException ex) {
            //Print Error
        }
    }

    public void populateListView() {
        GroupList groupList = GroupList.getInstance();

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupList);
        groupListView.setAdapter(itemsAdapter);

    }

}
