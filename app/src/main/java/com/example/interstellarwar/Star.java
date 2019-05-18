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
    private boolean right = true;
    private static int total = 0;
    private int num = 0;


    public Star(Bitmap bm){
        super(bm);
        total++;
        this.num=total;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    @Override

    // laser move speed along the y axis
    protected void beforeDeploy(Canvas canvas, Paint paint, GameView gameView) {
        if(!isDestroyed()){
            if(this instanceof Laser){
                move(0, speed * gameView.getDensity());
            }else{
                if(num%2==0){
                    RectF planetRecF = getPlanet();
                    if(planetRecF.right>=canvas.getWidth()){
                        right=false;
                    }
                    if(planetRecF.left<=0){
                        right=true;
                    }
                    if(right){
                        move(3, speed * gameView.getDensity());
                    }else{
                        move(-3, speed * gameView.getDensity());
                    }
                }else{
                    RectF planetRecF = getPlanet();
                    if(planetRecF.right>=canvas.getWidth()){
                        right=false;
                    }
                    if(planetRecF.left<=0){
                        right=true;
                    }
                    if(right){
                        move(-3, speed * gameView.getDensity());
                    }else{
                        move(3, speed * gameView.getDensity());
                    }
                }

            }

        }
    }

    //check if planet exceeds the range of Canvas, if yes, then destroy planet
    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){
        if(!isDestroyed()){
            RectF canvasRecF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            RectF planetRecF = getPlanet();
            if(!RectF.intersects(canvasRecF, planetRecF)){
                destroy();
            }
        }
    }
}