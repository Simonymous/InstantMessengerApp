package com.example.simon.instantmessengerapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.os.ResultReceiver;



import com.example.simon.instantmessengerapp.core.rest.services.GroupRestClientImpl;
import com.example.simon.instantmessengerapp.database.DatabaseHelper;
import com.example.simon.instantmessengerapp.database.adapter.MessageCursorAdapter;
import com.example.simon.instantmessengerapp.model.OwnUser;
import com.example.simon.instantmessengerapp.model.classes.MessageImpl;

public class ChatViewActivity extends AppCompatActivity {
    private ListView messageListView;
    private MessageCursorAdapter messageCursorAdapter;
    private EditText messageEt;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupIdCl");

        setContentView(R.layout.activity_chat_view);

        Intent intentA = new Intent(this, ChatViewService.class);
        intentA.putExtra("receiver", new Receiver(new Handler()));
        intentA.putExtra("groupID", groupId);
        intentA.setAction(ChatViewService.ACTION_POLLING);
        startService(intentA);

        messageListView = (ListView) findViewById(R.id.chatListview);
        messageEt = (EditText) findViewById(R.id.chatText);

        populateListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateListView();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, PostMessageService.class);



        intent.setAction(PostMessageService.ACTION_POST_MESSAGE);
        intent.putExtra("content", messageEt.getText().toString());
        intent.putExtra("group", groupId);
        startService(intent);


        //grcl.postMessage(msg);
//        SQLiteDatabase db = new DatabaseHelper(view.getContext()).getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.MESSAGE_CONTENT_FIELD_NAME, messageEt.getText().toString());
//        values.put(DatabaseHelper.MESSAGE_GROUP_FIELD_NAME, Integer.parseInt(groupId));
//        values.put(DatabaseHelper.MESSAGE_SENDER_FIELD_NAME, 1); //TODO: Do for own ID
//        db.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);
//        db.close();
        //populateListView();

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
