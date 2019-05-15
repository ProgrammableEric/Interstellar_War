package src.main.java.com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bombing extends Planet {

    private int pieaces = 10;

    public Bombing(Bitmap bm){
        super(bm);
    }

    public float getWidth() {
        Bitmap bm = getBitmap();
        if(bm != null){
            return bm.getWidth() / pieaces;
        }
        return 0;
    }

    public Rect getBitmapPos() {
        return null;
    }

    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView) {

    }

    public int getTime(){
        return 0;
    }

}
