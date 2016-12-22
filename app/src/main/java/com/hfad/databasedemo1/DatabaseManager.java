package com.hfad.databasedemo1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mine on 12/17/2016.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String  DB_NAME="customerDb";
    public static final String  TABLENAME="CUSTOMERTABLE";
    private static final String  DBNAME="";
    private static final int  VERSION=1;

    protected SQLiteDatabase database;
    public DatabaseManager(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        database=db;
        db.execSQL("CREATE TABLE CUSTOMERTABLE(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "CONTACT TEXT," +
                "EMAIL TEXT," +
                "DOB NUMERIC" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
