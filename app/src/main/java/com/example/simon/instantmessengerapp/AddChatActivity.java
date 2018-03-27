package com.example.simon.instantmessengerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.simon.instantmessengerapp.core.rest.services.GroupRestClientImpl;
import com.example.simon.instantmessengerapp.model.classes.GroupImpl;

public class AddChatActivity extends AppCompatActivity {
    EditText groupName;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            String[] args = new String[] {
                    intent.getStringExtra("groupIdNCl")
            };
            groupName.setText(args[1]);
        }

        setContentView(R.layout.activity_add_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        groupName = (EditText) findViewById(R.id.editText4);
        setSupportActionBar(toolbar);
        addButton = findViewById(R.id.addChatButton);

        //TODO: Show Users for adding

    }


    public void addGroup(View view) {
        GroupRestClientImpl grcl = GroupRestClientImpl.getInstance();
        grcl.addGroup(new GroupImpl(groupName.getText().toString()));
        //TODO: Add Members to Group
//        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
//        ContentValues groupValues = new ContentValues();
//        groupValues.put(DatabaseHelper.GROUP_NAME_FIELD_NAME, groupName.getText().toString());
//        db.insert(DatabaseHelper.GROUP_TABLE_NAME, null, groupValues);
//        db.close();
//        finish();
    }
}
