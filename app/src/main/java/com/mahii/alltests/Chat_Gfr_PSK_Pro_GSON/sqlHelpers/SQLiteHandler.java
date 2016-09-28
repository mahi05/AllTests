package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sqlHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "psk_go.db";
    private static final int DATABASE_VERSION = 1;

    SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table chat_history(" +
                "id integer primary key autoincrement, " +
                "user_email text, " +
                "message_type text, " +
                "message_from integer, " +
                "message_text text, " +
                "message_image text, " +
                "show_card text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists chat_history");
        onCreate(db);
    }

}
