package com.example.interstellarwar;

import android.graphics.Bitmap;

/**
 * the basic class for creating rival planets
 */
public class Planet {
    private boolean visible; //if visible to user
    private float x; //x-coordinate
    private float y; //y-coordinate
    private float Offset; //offset for collide
    private Bitmap bm;
    private boolean destroyed; //if planet is destroyed
    private int times; //the deploy times

    public Planet(Bitmap bm){
        this.bm = bm;
    }

    public Bitmap getBitmap() {
        return bm;
    }

    // Set planet to be visible
    public void setVisibility(){
    }

    // If planet is visible
    public boolean getVisibility(){
    }

    // Set the x-coordinate of planet
    public void setX(float x){
    }

    // Get the x-coordinates of planet
    public float getX(){
    }

    // Set the y-coordinate of planet
    public void setY(float y){
    }

    // Get the y-coordinates of planet
    public float getY(){
    }

    // Get the width of planet
    public float getWidth(){
    }

    // Get the height of planet
    public float getHeight(){
    }

    // Move planet by relative position
    public void move(float offsetX, float offsetY){
    }

    // Move planet to fix position
    public void moveTo(float x, float y){
    }

    // Fix the center of planet to a position
    public void centerTo(float centerX, float centerY){
    }

    // Get the relative position of bounding rectangle of planet
    public getPlanet(){
    }

    // Get the absolute position of bounding rectangle of planet
    public getBitmapPos (){
    }

    // Get the relative position of bounding rectangle of planet after colliding
    public getCollidedPlanet{
    }

    // Get the absolute position of bounding rectangle of planet after colliding
    public getCollidedBitmapPos(Planet p){
    }

    // Deploy the planet
    public final void Deploy(){
    }

    // Planet before deploy
    protected void beforeDeploy(){}

    // Planet on deploy
    public void onDeploy(){
    }

    // Planet after deploy
    protected void finishDeploy(){}

    // Destroy a planet
    public void destroy(){
    }

    // Planet is detroyed
    public isDestroyed(){
    }

    public getTimes(){
    }
}