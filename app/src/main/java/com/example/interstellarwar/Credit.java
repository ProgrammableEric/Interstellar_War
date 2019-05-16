package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Credit extends Star {

    public static int STATUS_DOWN1 = 1;
    public static int STATUS_UP2 = 2;
    public static int STATUS_DOWN3 = 3;

    private int status = STATUS_DOWN1;


    public Credit(Bitmap bm){
        super(bm);
        setSpeed(7);
    }

    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){
        // purely override finishDeploy in Star
        if(!isDestroyed()){
            // change the direction or speed when a fixed number of deploying achieved
            int canvasHeight = canvas.getHeight();
            if(status != STATUS_DOWN3){
                float maxY = getY() + getHeight();
                if(status == STATUS_DOWN1){
                    // go down at first
                    if(maxY >= canvasHeight * 0.25){
                        // when get the min height then change direction to go up
                        setSpeed(-5);
                        status = STATUS_UP2;
                    }
                }
                else if(status == STATUS_UP2){
                    // go up at the second time
                    if(maxY+this.getSpeed() <= 0){
                        // when get the max height then change direction to go down
                        setSpeed(13);
                        status = STATUS_DOWN3;
                    }
                }
            }
            if(status == STATUS_DOWN3){
                if(getY() >= canvasHeight){
                    destroy();
                }
            }
        }
    }
}
