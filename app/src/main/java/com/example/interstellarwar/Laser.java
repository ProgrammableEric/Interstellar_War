package com.example.interstellarwar;

import android.graphics.Bitmap;

public class Laser extends Star {

    public Laser(Bitmap bm){
        super(bm);
        setSpeed(-10); // negative means go up
    }
}
