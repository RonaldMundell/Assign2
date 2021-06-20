package com.example;

public class Rectangle {
    private String name;
    private String height;
    private String width;
    private String bgcolor;
    private String id;

    public String getName() {
        return this.name;
    }
    public String getBgcolor() {
        return this.bgcolor;
    }
    public String getHeight() {
        return this.height;
    }
    public String getWidth() {
        return this.width;
    }
    public String getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public void setWidth(String width) {
        this.width = width;
    }
    public void setId(String id) {
        this.id = id;
    }
}