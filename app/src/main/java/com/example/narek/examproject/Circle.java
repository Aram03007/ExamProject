package com.example.narek.examproject;

/**
 * Created by Narek on 3/23/16.
 */
public class Circle {

    private float centerX;
    private float centerY;
    private int color;
    private float radius;

    public Circle(float centerX, float centerY, float radius, int color) {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
    }

    public float getRadius() {
        return radius;
    }

    public int getColor() {

        return color;
    }

    public float getCenterX() {
        return centerX;
    }


    public float getCenterY() {
        return centerY;
    }




}

