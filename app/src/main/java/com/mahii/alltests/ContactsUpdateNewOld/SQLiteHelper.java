package com.mahii.alltests.ContactsUpdateNewOld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SQLiteHelper {

    public SQLiteDatabase database;
    public SQLiteHandler dbHandler;

    public SQLiteHelper(Context context) {
        dbHandler = new SQLiteHandler(context);
    }

    public void open() {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public int insertContacts(String name, String number) {

        if (null != database) {
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("number", number);
            if (database.insert("contactList", null, cv) > 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public ArrayList<ContactModel> getContacts() {

        ArrayList<ContactModel> contactModels = new ArrayList<>();

        Cursor c = database.rawQuery("SELECT * FROM contactList", null);

        while (c.moveToNext()) {

            ContactModel findContactsModel = new ContactModel();

            findContactsModel.setName(c.getString(1));
            findContactsModel.setNumber(c.getString(2));

            contactModels.add(findContactsModel);
        }

        return contactModels;
    }

    public void deleteAll() {
        database.execSQL("delete from country_list");
    }

}