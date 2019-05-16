package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

/**
 * user control, can exchange position
 */

public class SpaceShip extends Planet {
    private boolean collide = false; // if spaceship is collided
    private int nuclearNo = 0; // the available number of nuclear

    // double laser
    private boolean single = true; // label if it is a single laser
    private int doubleTime = 0; // the times of using double laser
    private int maxDoubleTime = 140; // the max number of using double laser

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

    // Lanch laser
    public void laser(GameView gameView){
        //如果战斗机被撞击了或销毁了，那么不会发射子弹
        // if spaceship is hitted or detryed then do not trigger laser
        if(collide || isDestroyed()){
            return;
        }

        float x = getX() + getWidth() / 2;
        float y = getY() - 5;
        if(single){
            // single shot mode trigger yellow bullet
            Bitmap yellowBulletBitmap = gameView.getYellowBulletBitmap();
            Laser yellowBullet = new Laser(yellowBulletBitmap);
            yellowBullet.moveTo(x, y);
            gameView.addPlanet(yellowBullet);
        }
        else{
            // double shop mode trigger blue bullet
            float offset = getWidth() / 4;
            float leftX = x - offset;
            float rightX = x + offset;
            Bitmap blueBulletBitmap = gameView.getBlueBulletBitmap();

            Laser leftBlueBullet = new Laser(blueBulletBitmap);
            leftBlueBullet.moveTo(leftX, y);
            gameView.addPlanet(leftBlueBullet);

            Laser rightBlueBullet = new Laser(blueBulletBitmap);
            rightBlueBullet.moveTo(rightX, y);
            gameView.addPlanet(rightBlueBullet);

            doubleTime++;
            if(doubleTime >= maxDoubleTime){
                single = true;
                doubleTime = 0;
            }
        }
    }

    protected void afterDeploy(Canvas canvas, Paint paint, GameView gameView){
        if(isDestroyed()){
            return;
        }

        // check if spaceship will be shotted before hit
        if(!collide){
            List<NewStar> enemies = gameView.getAliveEnemyPlanes();
            for(NewStar enemyPlane : enemies){
                Point p = getCollidePointWithOther(enemyPlane);
                if(p != null){
                    // p is hit point with star, if p is not null then spaceship is hitted by star
                    explode(gameView);
                    break;
                }
            }
        }

        // if beginFlashFrame is 0, means not in the flash mode
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
                        // if the flash time superior max falsh time, then destroy spaceship
                        destroy();
                    }
                }
            }
        }

        // if not hitted check if get the credit
        if(!collide){
            // check if get nuclear credit
            List<NuclearCredit> nuclearCredit = gameView.getAliveBombAwards();
            for(NuclearCredit nc : nuclearCredit){
                Point p = getCollidePointWithOther(nc);
                if(p != null){
                    nuclearNo++;
                    nc.destroy();
                }
            }

            // check if get laser credit
            List<LaserCredit> laserCredit = gameView.getAliveBulletAwards();
            for(LaserCredit lc : laserCredit){
                Point p = getCollidePointWithOther(lc);
                if(p != null){
                    lc.destroy();
                    single = false;
                    doubleTime = 0;
                }
            }
        }
    }

    // Spaceship is collided
    private void explode(GameView gameView){
        if(!collide){
            collide = true;
            setVisibility(false);
            float centerX = getX() + getWidth() / 2;
            float centerY = getY() + getHeight() / 2;
            Bombing bombing = new Bombing(gameView.getExplosionBitmap());
            bombing.centerTo(centerX, centerY);
            gameView.addPlanet(bombing);
            beginFlashFrame = getTimes() + bombing.getTime();
        }
    }

    // Get the times of nuclear
    public int getNuclearNo(){
        return nuclearNo;
    }

    // spaceship uses nuclear
    public void nuclear(GameView gameView){
        if(collide || isDestroyed()){
            return;
        }

        if(nuclearNo > 0){
            List<NewStar> enemyPlanes = gameView.getAliveEnemyPlanes();
            for(NewStar enemyPlane : enemyPlanes){
                enemyPlane.explode(gameView);
            }
            nuclearNo--;
        }
    }

    public boolean isCollide(){
        return collide;
    }

    public void setNotCollide(){
        collide = false;
    }
}