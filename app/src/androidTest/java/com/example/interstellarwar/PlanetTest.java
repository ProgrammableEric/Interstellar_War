package com.example.interstellarwar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertTrue;

public class PlanetTest  {

    Context context = getContext();

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
        Bitmap bitmap_mercury = BitmapFactory.decodeResource(getResources(), bmid_mercury);
        Planet mercury1 = new Planet(bitmap_mercury);
        assertTrue("Planet initial x position is not 0", mercury1.getX() == 0);
        assertTrue("Planet initial x position is not 0", mercury1.getY() == 0);
    }
}
