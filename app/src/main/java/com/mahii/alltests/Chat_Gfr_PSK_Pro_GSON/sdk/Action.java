package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk;

@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class Action {

    private String routeId;
    private String actionId;
    public Details details;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
