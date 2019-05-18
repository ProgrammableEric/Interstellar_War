package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.List;

public class NewStar extends Star {
    private int resistance = 1;//how the planet is resistant
    private int grade = 0;// how many grades can get after destroying one planet

    public NewStar(Bitmap bm) {
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

        // check if was shotted after deploying
        if(!isDestroyed()){
            // check if was shotted after star was deployed
            List<Laser> lasers = gameView.getAliveBullets();
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
        // create bombing effect
//        float centerX = getX() + getWidth() / 2;
//        float centerY = getY() + getHeight() / 2;
//        Bitmap bitmap = gameView.getExplosionBitmap();
//        Bombing bombing = new Bombing(bitmap);
//        bombing.centerTo(centerX, centerY);
//        gameView.addPlanet(bombing);

        // after bombing, add grades to GameView and destroy star
        gameView.changeScore(grade);
        destroy();
    }
}
