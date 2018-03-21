package com.example.simon.instantmessengerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.simon.instantmessengerapp.database.DatabaseHelper;
import com.example.simon.instantmessengerapp.database.adapter.MessageCursorAdapter;

public class ChatViewActivity extends AppCompatActivity {
    private ListView messageListView;
    private MessageCursorAdapter messageCursorAdapter;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupIdCl");

        setContentView(R.layout.activity_chat_view);

        messageListView = (ListView) findViewById(R.id.chatListview);

        populateListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateListView();
    }

    private void populateListView() {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        String whereClause = DatabaseHelper.MESSAGE_GROUP_FIELD_NAME+" = ?";
        String[] whereArgs = new String []{
                groupId
        };
        Cursor result  = db.query(DatabaseHelper.MESSAGE_TABLE_NAME, null, whereClause, whereArgs, null, null, null, null);
        messageCursorAdapter = new MessageCursorAdapter(this, result);
        messageListView.setAdapter(messageCursorAdapter);
        //result.close();
        db.close();
    }
}
