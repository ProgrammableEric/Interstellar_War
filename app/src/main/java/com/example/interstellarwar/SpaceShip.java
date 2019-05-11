package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * user control, can exchange position
 */

public class SpaceShip extends Planet {
    private boolean collide = false; // if spaceship is collided
    private int nuclearNo = 0; // the available number of nuclear

    // double laser
    private boolean single = true; // label if it is a single laser
    private int doubleTime = 0; // the times of using double laser
    private int maxDoubleTime = 140; // the max number of using double laser

    // flash
    private long beginFlashFrame = 0; //the frame that starting flash
    private int flashTime = 0; // flash times
    private int flashFrequency = 16; //when flashing, the visibility of transforming to spaceship in each 16 frames
    private int maxFlashTime = 10; // max flash times

    public SpaceShip(Bitmap bm){
        super(bm);
    }

    @Override
    protected void beforeDeploy(Canvas canvas, Paint paint, GameView gameView) {
    }

    // Confirm spaceship is in the Canvas
    private void validatePosition(Canvas canvas){
    }

    // Lanch laser
    public void laser(GameView gameView){
    }

    protected void afterDeploy(Canvas canvas, Paint paint, GameView gameView){
    }

    // Spaceship is collided
    private void explode(GameView gameView){
    }

    // Get the times of nuclear
    public int getNuclearNo(){
        return nuclearNo;
    }

    // spaceship uses nuclear
    public void nuclear(GameView gameView){
    }

    public boolean isCollide(){
        return collide;
    }

    public void setNotCollide(){
        collide = false;
    }
}