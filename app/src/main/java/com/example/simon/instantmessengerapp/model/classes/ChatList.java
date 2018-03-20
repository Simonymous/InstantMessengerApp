package com.example.simon.instantmessengerapp.model.classes;

import com.example.simon.instantmessengerapp.model.interfaces.Group;
import com.example.simon.instantmessengerapp.model.classes.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatList {

    ArrayList<Chat> chatList;

    private ChatList() {
        List<Chat> chats = new ArrayList<>();
    }

    public ArrayList<Chat> getChatList() {
        return chatList;
    }

    public Chat getChatByName(String name) {
        for (Chat c : chatList) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    public Chat getChatById(String id) {
        for (Chat c : chatList) {
            if (c.getStringId().equals(id)) return c;
        }
        return null;
    }

    public Chat getChatById(int id) {
        for (Chat c : chatList) {
            if (c.getStringId().equals(Integer.toString(id))) return c;
        }
        return null;
    }

    public void removeChat(int id) {
        Chat chat = null;
        for (Chat c : chatList) {
            if (c.getId() == id) {
                chat = c;
            }
        }
        chatList.remove(chat);
        //Aktualisieren
    }

    public void updateChat(Group group) {
        if (!contains(group.getGroupId())) {
            chatList.add(new Chat(group));
            return;
        }
        Chat c = getChatById(group.getGroupId());
        c.setName(group.getGroupName());
        //Aktualisieren
    }

    public void addChat(Chat chat) {
        chatList.add(chat);
    }

    public boolean contains(int id) {
        for (Chat c : chatList) {
            if (c.getId() == id) return true;
        }
        return false;
    }
}
