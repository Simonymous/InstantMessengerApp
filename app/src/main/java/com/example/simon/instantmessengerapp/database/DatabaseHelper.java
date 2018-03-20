package com.example.simon.instantmessengerapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Christian on 20.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "imdb";
    private static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_ID_FIELD_NAME = "id";
    public static final String USER_ID_FIELD_TYP = "INTEGER";
    public static final String USER_NAME_FIELD_NAME = "name";
    public static final String USER_NAME_FIELD_TYP = "TEXT";

    public static final String GROUP_TABLE_NAME = "chatGroup";
    public static final String GROUP_ID_FIELD_NAME = "id";
    public static final String GROUP_ID_FIELD_TYP = "INTEGER";
    public static final String GROUP_NAME_FIELD_NAME = "name";
    public static final String GROUP_NAME_FIELD_TYP = "TEXT";

    public static final String MESSAGE_TABLE_NAME = "message";
    public static final String MESSAGE_ID_FIELD_NAME = "id";
    public static final String MESSAGE_ID_FIELD_TYP = "INTEGER";
    public static final String MESSAGE_CONTENT_FIELD_NAME = "content";
    public static final String MESSAGE_CONTENT_FIELD_TYP = "TEXT";
    public static final String MESSAGE_GROUP_FIELD_NAME = "chatGroup";
    public static final String MESSAGE_GROUP_FIELD_TYP = "INTEGER";
    public static final String MESSAGE_SENDER_FIELD_NAME = "sender";
    public static final String MESSAGE_SENDER_FIELD_TYP = "INTEGER";

    /**
     * CREATE TABLE `user` (
     *`id`	INTEGER NOT NULL,
     *`name`	TEXT NOT NULL UNIQUE,
     *PRIMARY KEY(`id`)
     *);
     */
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + "( "
            + USER_ID_FIELD_NAME + " " + USER_ID_FIELD_TYP + " PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME_FIELD_NAME + " " + USER_NAME_FIELD_TYP + " NOT NULL UNIQUE "
            + " );";

    /**
     * CREATE TABLE `group` (
     *`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
     *`name`	TEXT NOT NULL
     *);
     */
    private static final String GROUP_TABLE_CREATE =
            "CREATE TABLE " + GROUP_TABLE_NAME + "( "
                    + GROUP_ID_FIELD_NAME + " " + GROUP_ID_FIELD_TYP + " PRIMARY KEY AUTOINCREMENT , "
                    + GROUP_NAME_FIELD_NAME + " " + GROUP_NAME_FIELD_TYP + " NOT NULL "
                    + " ); ";

    /**
     * CREATE TABLE `message` (
     *`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
     *`content`	TEXT NOT NULL,
     *`group`	INTEGER,
     *`sender`	INTEGER NOT NULL,
     *FOREIGN KEY(`group`) REFERENCES `group`(`id`),
     *FOREIGN KEY(`sender`) REFERENCES `user`(`id`)
     *);
     */
    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + "( "
                    + MESSAGE_ID_FIELD_NAME + " " + MESSAGE_ID_FIELD_TYP
                    + " PRIMARY KEY AUTOINCREMENT , "
                    + MESSAGE_CONTENT_FIELD_NAME + " " + MESSAGE_CONTENT_FIELD_TYP + " NOT NULL , "
                    + MESSAGE_GROUP_FIELD_NAME + " " + MESSAGE_GROUP_FIELD_TYP + " , "
                    + MESSAGE_SENDER_FIELD_NAME + " " + MESSAGE_SENDER_FIELD_TYP + " NOT NULL , "
                    + "FOREIGN KEY( " + MESSAGE_GROUP_FIELD_NAME + " ) " + " REFERENCES "
                    + GROUP_TABLE_NAME + "( " + GROUP_ID_FIELD_NAME + " ), "
                    + "FOREIGN KEY( " + MESSAGE_SENDER_FIELD_NAME + " ) " + " REFERENCES "
                    + USER_TABLE_NAME + "( " + USER_ID_FIELD_NAME + " ) "
                    + " );";

    public DatabaseHelper(Context context){
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try {
            db.execSQL(USER_TABLE_CREATE);
            db.execSQL(GROUP_TABLE_CREATE);
            db.execSQL(MESSAGE_TABLE_CREATE);
        } catch (SQLException ex){
            //TODO Fehlermeldungen auslagern
            Log.e("DatabaseHelper", "error creating tables", ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        //TODO implementieren
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        //TODO implementieren
    }
}
