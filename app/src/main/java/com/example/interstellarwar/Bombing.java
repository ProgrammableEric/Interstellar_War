package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.interstellarwar.GameView;

public class Bombing extends Planet {

    private int pieces = 10; //bombing has 10 pieces
    private int start = 0; // start from the 0-th pieces
    private int bombFrequency = 2; // each piece deploy 2 times

    public Bombing(Bitmap bm){
        super(bm);
    }

    public float getWidth() {
        Bitmap bm = getBitmap();
        if(bm != null){
            return bm.getWidth() / pieces;
        }
        return 0;
    }

    public Rect getBitmapPos() {
        Rect rect = super.getBitmapPos();
        int left = (int)(start * getWidth());
        rect.offsetTo(left, 0);
        return rect;
    }

    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView) {
        if(!isDestroyed()){
            if(getTimes() % bombFrequency == 0){
                // deploy the next piece
                start++;
                if(start >= pieces){
                    // all the pieces are deployed then destroy the bombing effect
                    destroy();
                }
            }
        }
    }

    public int getTime(){
        return pieces * bombFrequency;
    }

}
