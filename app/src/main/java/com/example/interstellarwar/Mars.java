package com.example.interstellarwar;

import android.graphics.Bitmap;

/**
 * the medium star with medium quality and medium resistance
 */
public class Mars extends NewStar {

    public Mars(Bitmap bm){
        super(bm);
        setResistance(4); // the resistance of Mars is 4, that is, it will be destroyed after 4 laser attack
        setGrade(6000); // the grades for destroy Mars is 6000
    }

}