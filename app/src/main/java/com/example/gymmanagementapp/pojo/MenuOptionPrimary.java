package com.example.gymmanagementapp.pojo;

import com.example.gymmanagementapp.R;

public class MenuOptionPrimary {
    private String title;
    private String subTitle;
    private int icon;
    private String image;

    public MenuOptionPrimary(String title, String subTitle, int icon) {
        this.title = title;
        this.subTitle = subTitle;
        this.icon = icon;
        this.image = "";
    }

    public MenuOptionPrimary(String title, String subTitle, String image) {
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
        this.icon = R.drawable.icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
