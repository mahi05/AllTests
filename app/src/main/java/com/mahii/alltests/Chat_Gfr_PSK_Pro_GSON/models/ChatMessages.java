package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.models;

public class ChatMessages {

    private String message_type;
    private boolean message_from;
    private String message_text;
    private String message_image;
    private String show_card;

    public ChatMessages() {
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public boolean isMessage_from() {
        return message_from;
    }

    public void setMessage_from(boolean message_from) {
        this.message_from = message_from;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getMessage_image() {
        return message_image;
    }

    public void setMessage_image(String message_image) {
        this.message_image = message_image;
    }

    public String getShow_card() {
        return show_card;
    }

    public void setShow_card(String show_card) {
        this.show_card = show_card;
    }
}
