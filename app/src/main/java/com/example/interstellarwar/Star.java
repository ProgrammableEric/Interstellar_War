package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Planets that can be move straightly
 */

public class Star extends Planet {

    private float speed = 2; // the pixel number of each movement, go down for positive

    public Star(Bitmap bm){
        super(bm);
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    @Override

    //// move speed along the y axis
    protected void beforeDeploy(Canvas canvas, Paint paint, GameView gameView) {
        if(!isDestroyed()){
            int index = (int)Math.floor(10*Math.random());
            move(speed, speed * gameView.getDensity());
//            if(index%2==0){
//                move(0, speed * gameView.getDensity());
//            }else{
//                move(0, speed * gameView.getDensity());
//            }

        }
    }

    //check if planet exceeds the range of Canvas, if yes, then destroy planet
    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){
        if(!isDestroyed()){
            RectF canvasRecF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            RectF spriteRecF = getPlanet();
            if(!RectF.intersects(canvasRecF, spriteRecF)){
                destroy();
            }
        }
    }
}