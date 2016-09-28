package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class StringUtil {

    public static final String PREFS_NAME = "mahi_gfr";
    public static final String LOGGED_IN_USER_EMAIL = "loged_in_user_email";
    public static final String TEXT_TYPE = "text";
    public static final String IMAGE_TYPE = "image";
    public static final String CARD_TYPE = "card";

    /**
     * Makes the first character of every word uppercase and the rest lowercase
     * @param text The text to change
     * @return String
     */
    public static String toTitleCase(String text){
        if(text.length() == 0){
            return text;
        }
        String[] parts = text.split(" ");
        String finalString = "";
        for (String part : parts){
            finalString = finalString + toProperCase(part) + " ";
        }
        return finalString;
    }

    /**
     * Makes first character uppercase - the rest lowercase
     * @param text The text to change
     * @return String
     */
    private static String toProperCase(String text) {
        return text.substring(0, 1).toUpperCase() +
                text.substring(1).toLowerCase();
    }

    // string preference
    public static boolean setStringPreference(Activity activity, String key, String value) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getStringPreference(Activity activity, String key) {
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

}
