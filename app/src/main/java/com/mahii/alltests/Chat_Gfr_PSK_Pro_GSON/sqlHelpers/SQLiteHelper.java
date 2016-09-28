package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sqlHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.models.ChatMessages;

import java.util.ArrayList;

public class SQLiteHelper {

    public SQLiteDatabase database;
    private SQLiteHandler dbHandler;

    public SQLiteHelper(Context context) {
        dbHandler = new SQLiteHandler(context);
    }

    public void open() {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public int insertData(
            String user_email,
            String message_type,
            int message_from,
            String message_text,
            String message_image,
            String show_card) {

        if (null != database) {
            ContentValues cv = new ContentValues();
            cv.put("user_email", user_email);
            cv.put("message_type", message_type);
            cv.put("message_from", message_from);
            cv.put("message_text", message_text);
            cv.put("message_image", message_image);
            cv.put("show_card", show_card);
            if (database.insert("chat_history", null, cv) > 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public ArrayList<ChatMessages> getChatMessagesNew() {

        ArrayList<ChatMessages> chatMessages = new ArrayList<>();

        Cursor c = database.rawQuery("select * from chat_history ", null);

        while (c.moveToNext()) {

            ChatMessages chatModel = new ChatMessages();

            chatModel.setMessage_type(c.getString(2));

            if (c.getInt(3) == 1) {
                chatModel.setMessage_from(true);
            } else {
                chatModel.setMessage_from(false);
            }

            chatModel.setMessage_text(c.getString(4));
            chatModel.setMessage_image(c.getString(5));
            chatModel.setShow_card(c.getString(6));

            chatMessages.add(chatModel);
        }
        return chatMessages;
    }

}