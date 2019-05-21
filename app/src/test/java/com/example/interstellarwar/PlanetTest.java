package com.example.interstellarwar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import static android.support.test.InstrumentationRegistry.getContext;
import android.content.Context;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PlanetTest {

    private int[] bitmapIds = {
            R.drawable.spaceship,
            R.drawable.explosion,
            R.drawable.laser,
            R.drawable.laser,
            R.drawable.mercury,
            R.drawable.mars,
            R.drawable.jupiter,
            R.drawable.bomb_award,
            R.drawable.bullet_award,
            R.drawable.pause,
            R.drawable.bomb
    };

    @Test
    public void TestPositioning(){

        int bmid_mercury = bitmapIds[4];
        Bitmap bitmap_mercury = BitmapFactory.decodeResource(, bmid_mercury);
        Planet mercury1 = new Planet(bitmap_mercury);
        assertEquals("Planet initial x position is not 0", mercury1.getX() == 0);
        assertEquals("Planet initial x position is not 0", mercury1.getY() == 0);
    }


}
