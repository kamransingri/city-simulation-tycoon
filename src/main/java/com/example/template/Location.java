package com.example.template;

public class Location {
    private int x;
    private int y;
    private String color;
    public Location(int x, int y, String color){
        this.x = x;
        this.y = y;
        this.color = color;

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getColor() {
        return color;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
