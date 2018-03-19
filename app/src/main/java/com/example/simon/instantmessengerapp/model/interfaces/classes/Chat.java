package com.example.simon.instantmessengerapp.model.interfaces.classes;

import com.example.simon.instantmessengerapp.model.interfaces.Group;
import com.example.simon.instantmessengerapp.model.interfaces.Message;

import java.util.ArrayList;

public class Chat {
    private int id;
    private String name;
    ArrayList<MessageImpl> messageList;

    public Chat(Group group) {
        id = group.getGroupId();
        name = group.getGroupName();
    }

    public boolean contains(long id) {
        for (Message cm : messageList) {
            if (cm.getMessageId() == id) return true;
        }
        return false;
    }

    public void addMessage(MessageImpl m) {
        messageList.add(m);
    }

    public ArrayList<MessageImpl> getMessageList() {
        return messageList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getStringId() {
        return String.format("%d", id);
    }

    @Override
    public String toString() {
        return name;
    }
}
