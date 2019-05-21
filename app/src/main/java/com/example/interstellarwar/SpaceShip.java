package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.List;

/**
 * user control, can exchange position
 */

public class SpaceShip extends Planet {
    private boolean collide = false; // if spaceship is collided

    // flash
    private long beginFlashFrame = 0; //the frame that starting flash
    private int flashTime = 0; // flash times
    private int flashFrequency = 16; //when flashing, the visibility of transforming to spaceship in each 16 frames
    private int maxFlashTime = 10; // max flash times

    public SpaceShip(Bitmap bm){
        super(bm);
    }

    @Override
    protected void beforeDeploy(Canvas canvas, Paint paint, GameView gameView) {
        if(!isDestroyed()){
            // confirm star is in the range of Canvas
            validatePosition(canvas);

            // trigger laser every 7 times
            if(getTimes() % 7 == 0){
                laser(gameView);
            }
        }
    }

    // Confirm spaceship is in the Canvas
    private void validatePosition(Canvas canvas){
        if(getX() < 0){
            setX(0);
        }
        if(getY() < 0){
            setY(0);
        }
        RectF rectF = getPlanet();
        int canvasWidth = canvas.getWidth();
        if(rectF.right > canvasWidth){
            setX(canvasWidth - getWidth());
        }
        int canvasHeight = canvas.getHeight();
        if(rectF.bottom > canvasHeight){
            setY(canvasHeight - getHeight());
        }
    }

    // Launch laser
    public void laser(GameView gameView){
        // if spaceship is hit or destroyed then do not trigger laser
        if(collide || isDestroyed()){
            return;
        }

        float x = getX() + getWidth() / 2;
        float y = getY() - 500;
            Bitmap laserBitmap = gameView.getLaserBitmap();
            Laser laser = new Laser(laserBitmap);
            laser.moveTo(x, y);
            gameView.addPlanet(laser);
    }

    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){
        if(isDestroyed()){
            return;
        }

        // check if spaceship will be shot before hit
        if(!collide){
            List<Star> stars = gameView.getStars();
            for(Star star : stars){
                Point p = getCollidedBitmapPos(star);
                if(p != null){
                    // p is hit point with star, if p is not null then spaceship is hitted by star
                    explode(gameView);
                    break;
                }
            }
        }

        //if beginFlashFrame is 0, means not in the flash mode
        //if beginFlashFrame larger than 0, means start flashing from beginFlashFrame
        if(beginFlashFrame > 0){
            long frame = getTimes();
            // if current frame no less than beginFlashFrame means spaceship start to flash for destroying
            if(frame >= beginFlashFrame){
                if((frame - beginFlashFrame) % flashFrequency == 0){
                    boolean visible = getVisibility();
                    setVisibility(!visible);
                    flashTime++;
                    if(flashTime >= maxFlashTime){
                        // if the flash time superior max flash time, then destroy spaceship
                        destroy();
                    }
                }
            }
        }
    }

    // Spaceship is collided
    private void explode(GameView gameView){
        if(!collide){
            collide = true;
            setVisibility(false);
            beginFlashFrame = getTimes();
        }
    }
}