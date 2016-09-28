package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions;

import android.util.Log;

import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk.Action;
import com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.utils.StringUtil;

public class ActionFactory {

    private final Action action;

    public ActionFactory(Action action) {
        this.action = action;
    }

    public IAction build() {
        try {
            String className = this.getFullActionName();
            Log.d("ActionFactory", "className = " + className);
            return (IAction) Class.forName(className).getConstructor().newInstance();
        } catch (Exception e) {
            Log.d("Action", e.toString());
            return new UnknownAction();
        }
    }

    private String getFullActionName() {
        String classPrefix = "com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.actions.";
        if (null == this.action) return classPrefix + "UnknownAction";
        if (null == this.action.getActionId()) return classPrefix + "UnknownAction";
        return classPrefix + StringUtil.toTitleCase(this.action.getActionId()).replace(" ", "").replace("_", "") + "Action";
    }
}
