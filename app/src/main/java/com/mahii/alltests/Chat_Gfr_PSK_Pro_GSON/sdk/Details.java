package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk;

import java.util.ArrayList;

public class Details {

    private String text;
    private String url;
    private String image;
    private ArrayList<Cards> cards;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }
}
