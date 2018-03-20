package com.example.simon.instantmessengerapp.model.classes;

import com.example.simon.instantmessengerapp.model.interfaces.Group;

import java.util.ArrayList;

public class GroupList {

    private static GroupList groupList = null;
    private ArrayList<Group> userGroups = new ArrayList<Group>();

    public ArrayList<Group> getUserGroups() {
        return userGroups;
    }

    private GroupList() {

    }

    public static GroupList getInstance() {
        if (groupList == null) {
            groupList = new GroupList();
        }
        return groupList;
    }

    public void addGroup(Group g) {
            userGroups.add(g);
    }

    public void deleteGroup(Group u) {
        userGroups.remove(u);
    }

    public void deleteGroup(int id) {
        for (Group g : userGroups) {
            if (g.getGroupId() == id) {
                deleteGroup(g);
            }
        }
    }

    public Group getGroup(int id) {
        for (Group g : userGroups) {
            if (g.getGroupId() == id) return g;
        }
        return null;
    }

    public ArrayList<String> getGroupNames() {
        ArrayList<String> names = new ArrayList<String>();

        if(userGroups.isEmpty()){
            names.add("Keine Gruppe");
            return names;
        }

        for(Group g : userGroups){
            names.add(g.getGroupName());
        }

        return names;
    }

    public boolean containsGroup(Group g) {
        return userGroups.contains(g);
    }

    public boolean containsGroup(int id) {
        for (Group g : userGroups) {
            if (g.getGroupId() == id) {
                return true;
            }
        }
        return false;
    }
}
