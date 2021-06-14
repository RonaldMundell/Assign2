package com.example;

public class Rectangle {
    private String name;
    private String height;
    private String width;
    private String bgcolor;

    public String getName() {
        return this.name;
    }
    public String getBGcolor() {
        return this.bgcolor;
    }
    public String getHeight() {
        return this.height;
    }
    public String getWidth() {
        return this.width;
    }

    public void setName(String n) {
        this.name = n;
    }
    public void setBGcolor(String bgc) {
        this.bgcolor = bgc;
    }
    public void setHeight(String h) {
        this.height = h;
    }
    public void setWidth(String w) {
        this.width = w;
    }
}