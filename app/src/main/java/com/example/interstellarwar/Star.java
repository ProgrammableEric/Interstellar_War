package com.example.interstellarwar;

import android.graphics.Bitmap;

/**
 * Planets that can be move straightly
 */

public class Star extends Planet {

    private float speed; // the pixel number of each movement, go down for positive

    public Star(Bitmap bitmap){
        super(bitmap);
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    @Override
    protected void beforeDeploy() {
    }

    protected void finishDeploy(){
    }
}