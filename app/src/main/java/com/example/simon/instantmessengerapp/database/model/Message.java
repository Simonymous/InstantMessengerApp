package com.example.simon.instantmessengerapp.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Christian on 20.03.2018.
 */

@DatabaseTable(tableName = "message")
public class Message {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField
    private String content;
    @DatabaseField
    private int group;
    @DatabaseField
    private int sender;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public Message() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return content;
    }

    public int getID() {
        return id;
    }
}
