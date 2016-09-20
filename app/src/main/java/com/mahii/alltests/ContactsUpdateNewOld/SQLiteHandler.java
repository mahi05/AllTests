package com.mahii.alltests.ContactsUpdateNewOld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "find_contact.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table contactList(" +
                "id integer primary key autoincrement, " +
                "name text, " +
                "number text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists contactList");
        onCreate(db);
    }

}