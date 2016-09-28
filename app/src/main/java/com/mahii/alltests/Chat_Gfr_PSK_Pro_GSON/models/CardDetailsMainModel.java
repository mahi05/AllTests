package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.models;

import java.util.ArrayList;

public class CardDetailsMainModel {

    private String title;
    private String subtitle;
    private String text;
    private ArrayList<CardDetailsImagesModel> images;
    private ArrayList<CardDetailsButtonsModel> buttons;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<CardDetailsImagesModel> getImages() {
        return images;
    }

    public void setImages(ArrayList<CardDetailsImagesModel> images) {
        this.images = images;
    }

    public ArrayList<CardDetailsButtonsModel> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<CardDetailsButtonsModel> buttons) {
        this.buttons = buttons;
    }
}
