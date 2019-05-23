package com.example.interstellarwar;

import android.graphics.Bitmap;

/**
 * the large star with large quality and high resistance
 *
 * author: Wenjuan Liao, Xiaoquan Hu
 */

public class Jupiter extends Star {

    public Jupiter(Bitmap bm){
        super(bm);
        setResistance(10); // the resistance of Jupiter is 10, that is, it will be destroyed after 10 laser attack
        setGrade(30000); // the grades for destroy Jupiter is 30000
    }

}