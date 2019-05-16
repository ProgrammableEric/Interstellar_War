package com.example.interstellarwar;

import android.graphics.Bitmap;

/**
 * the largest star with large quality and high resistance
 */

public class Jupiter extends NewStar {

    public Jupiter(Bitmap bm){
        super(bm);
        setResistance(10); // the resistance of Jupiter is 10, that is, it will be destroyed after 10 laser attack
        setGrade(30000); // the grades for destroy Jupiter is 30000
    }

}