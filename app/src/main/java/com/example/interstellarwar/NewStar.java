package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

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
        //super.finishDeploy(canvas, paint, gameView);
    }


    public void bomb(GameView gameView){

    }
}
