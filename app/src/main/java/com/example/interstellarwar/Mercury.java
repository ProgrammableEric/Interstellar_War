package com.example.interstellarwar;

import android.graphics.Bitmap;

/**
 * the small star with small quality and low resistance
 */

public class Mercury extends Star {
    public Mercury(Bitmap bm){
        super(bm);
        setResistance(1);//mercury is one
        setGrade(1000);//mercury grade is 1000
    }
}
