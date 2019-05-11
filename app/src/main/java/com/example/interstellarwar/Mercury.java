package com.example.interstellarwar;

import android.graphics.Bitmap;

public class Mercury extends Planets {
    public SmallEnemyPlane(Bitmap bm){
        super(bm);
        setResistance(1);//mercury is one
        setGrade(1000);//merrcury grade is 1000
    }
}
