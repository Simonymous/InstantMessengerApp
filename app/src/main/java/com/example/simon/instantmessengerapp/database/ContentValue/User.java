package com.example.simon.instantmessengerapp.database.ContentValue;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Christian on 20.03.2018.
 */

@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField
    private String name;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }
}
