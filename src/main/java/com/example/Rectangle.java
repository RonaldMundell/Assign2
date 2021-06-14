package com.example;

public class Rectangle {
    private String name;
    private int height;
    private int width;
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

     public String setName(String n) {
        this.name = n;
    }
    public String setBGcolor(String bgc) {
        this.bgcolor = bgc;
    }
    public String setHeight(int h) {
        this.height = h;
    }
    public String setWidth(int w) {
        this.width = w;
    }
}