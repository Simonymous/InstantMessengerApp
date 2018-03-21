package com.example.simon.instantmessengerapp.database.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.simon.instantmessengerapp.R;

/**
 * Created by Christian on 20.03.2018.
 */

public class GroupCursorAdapter extends CursorAdapter {
    public GroupCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvName = (TextView) view.findViewById(R.id.groupName);
        TextView tvId = (TextView) view.findViewById(R.id.groupId);
        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        // Populate fields with extracted properties
        tvName.setText(name);
        tvId.setText(id);
    }
}