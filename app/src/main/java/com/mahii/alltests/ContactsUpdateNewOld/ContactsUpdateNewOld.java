package com.mahii.alltests.ContactsUpdateNewOld;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mahii.alltests.R;

import java.util.ArrayList;

public class ContactsUpdateNewOld extends AppCompatActivity {

    ArrayList<ContactModel> mainContactModels;
    ArrayList<ContactModel> dbContactModel;
    ArrayList<ContactModel> needToUpload;
    SQLiteHelper helper;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(ContactsUpdateNewOld.this);
        dialog.setMessage("Please wait");

        helper = new SQLiteHelper(ContactsUpdateNewOld.this
        );
        helper.open();

        dbContactModel = new ArrayList<>();
        needToUpload = new ArrayList<>();

        mainContactModels = helper.getContacts();

        //insertToDb();

        newContactData();

        Log.e("mainContactModels", "" + mainContactModels.size());
        Log.e("dbContactModels", "" + dbContactModel.size());

        // Loop arrayList2 items
        for (ContactModel person2 : dbContactModel) {

            // Loop arrayList1 items
            boolean found = false;
            for (ContactModel person1 : mainContactModels) {
                if (person2.getNumber().equals(person1.getNumber())) {
                    found = true;
                }
            }
            if (!found) {
                ContactModel contactModel2 = new ContactModel();
                contactModel2.setName(person2.getName());
                contactModel2.setName(person2.getNumber());
                needToUpload.add(contactModel2);
            }
        }

        /*Log.e("needToUpload", "" + needToUpload.size());

        TelephoneNumberCanonicalizer german = new TelephoneNumberCanonicalizer(getCountryDialCode());
        Log.e("usersCountryISOCode", "" + german.canonicalize("8866469250"));*/

        /* Using Phone Util Library */
        String swissNumberStr = "8866046925";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        TelephonyManager telephonyMngr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryId = telephonyMngr.getSimCountryIso().toUpperCase();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(swissNumberStr, countryId);
            phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
            Log.e("Number", phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164));

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        /* Using Array from strings file */
        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse("+919876543210", "in");
            int countryCode = numberProto.getCountryCode();
            Log.e("CountryCode", "" + countryCode);

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

    }

    public void insertToDb() {
        dialog.show();
        try {
            ContentResolver cr = ContactsUpdateNewOld.this.getContentResolver(); //Activity/Application android.content.Context
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            assert cursor != null;
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        assert pCur != null;
                        while (pCur.moveToNext()) {

                            ContactModel contactModel = new ContactModel();

                            String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                            contactModel.setName(contactName);
                            contactModel.setNumber(contactNumber);
                            dbContactModel.add(contactModel);
                        }
                        pCur.close();
                    }

                } while (cursor.moveToNext());
            }

            for (int i = 0; i < dbContactModel.size(); i++) {
                helper.insertContacts(dbContactModel.get(i).getName(), dbContactModel.get(i).getNumber());
            }

        } catch (Exception ex) {
            Log.e("Contacts", ex.getMessage());
        }
        dialog.dismiss();
    }

    public void newContactData() {
        try {
            ContentResolver cr = ContactsUpdateNewOld.this.getContentResolver(); //Activity/Application android.content.Context
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            assert cursor != null;
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        assert pCur != null;
                        while (pCur.moveToNext()) {

                            ContactModel contactModel = new ContactModel();

                            String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                            contactModel.setName(contactName);
                            contactModel.setNumber(contactNumber);
                            dbContactModel.add(contactModel);
                        }
                        pCur.close();
                    }

                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            Log.e("Contacts", ex.getMessage());
        }
    }

    public String getCountryDialCode() {

        String contryId = null;
        String contryDialCode = null;

        TelephonyManager telephonyMngr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        contryId = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrCountryCode = getResources().getStringArray(R.array.DialingCountryCode);

        for (int i = 0; i < arrCountryCode.length; i++) {

            String[] arrDial = arrCountryCode[i].split(",");

            if (arrDial[1].trim().equals(contryId.trim())) {
                contryDialCode = arrDial[0];
                break;
            }
        }
        return contryDialCode;
    }

}
