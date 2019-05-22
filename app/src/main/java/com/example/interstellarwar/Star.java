package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.List;

/**
 * rivalry stars, including two types, Jupiter and Mercury
 */

public class Star extends Planet {
    private int resistance;//how the planet is resistant
    private int grade;// how many grades can get after destroying one planet

    public Star(Bitmap bm) {
        super(bm);
    }

    public void setResistance(int res) {
        this.resistance = res;
    }

    public int getResistance(){
        return resistance;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade(){
        return grade;
    }

    @Override
    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){
        super.finishDeploy(canvas, paint, gameView);

        // check if was shot after deploying
        if(!isDestroyed()){
            // check if was shot after star was deployed
            List<Laser> lasers = gameView.getLasers();
            for(Laser laser : lasers){
                // check if star hit laser
                Point p = getCollidedBitmapPos(laser);
                if(p != null){
                    // if has intersection, laser hit star
                    laser.destroy();
                    resistance--;
                    if(resistance <= 0){
                        // no resistance, implement bomb
                        bomb(gameView);
                        return;
                    }
                }
            }
        }
    }


    public void bomb(GameView gameView){
        gameView.changeScore(grade);
        destroy();
    }
}
