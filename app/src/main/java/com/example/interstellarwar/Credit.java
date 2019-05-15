package src.main.java.com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Credit extends Star {


    public Credit(Bitmap bm){
        super(bm);
        setSpeed(7);
    }

    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){
        // purely overide finishDeploy in Star
    }
}
