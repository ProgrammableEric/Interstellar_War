package com.example.interstellarwar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.Settings;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameView extends View {

    //set class field
    private SpaceShip spaceShip = null;
    // initial the planets and to add planets
    private List<Planet> planets = new ArrayList<Planet>();
    private List<Planet> toAddPlanets = new ArrayList<Planet>();
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private Paint paint = new Paint();
    private TextPaint textPaint = new TextPaint();
    // start=1  pause=2  over=3  destroyed=4
    private int status = 4;
    private long globalCount = 0;
    private Integer score = 0;
    private Integer highestscore = 0;
    private Rect continueRect = new Rect();
    //duration between up and down action
    private static final int singleDuration = 200;
    // double click means short duration between two single clicks
    private static final int doubleDuration = 300;
    private long lastSingleClickTime = -1;
    private long tDownTime = -1;
    private long tUpTime = -1;
    private float tX = -1;
    private float tY = -1;

    public GameView(Context context) {
        super(context);
        final TypedArray a = getContext().obtainStyledAttributes(null, R.styleable.GameView, 0, 0);
        a.recycle();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GameView, 0, 0);
        a.recycle();
    }

    public GameView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GameView, style, 0);
        a.recycle();
    }

    public void startSetting(int[] bmIds){
        remove();
        for(int Id : bmIds){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Id);
            bitmaps.add(bitmap);
        }
        spaceShip = new SpaceShip(bitmaps.get(0));
        status = 1;
        postInvalidate();
    }

    public float getDensity(){
        return getResources().getDisplayMetrics().density;
    }

    public void pause(){
        status = 2;
    }

    public List<Star> getStars(){
        List<Star> planetList = new ArrayList<Star>();
        for(Planet p : planets){
            if(!p.isDestroyed() && p instanceof Star){
                Star s = (Star) p;
                planetList.add(s);
            }
        }
        return planetList;
    }

    public List<Laser> getLasers(){
        List<Laser> ll = new ArrayList<Laser>();
        for(Planet p : planets){
            if(!p.isDestroyed() && p instanceof Laser){
                Laser l = (Laser) p;
                ll.add(l);
            }
        }
        return ll;
    }


    // Draw all the game views
    protected void onDraw(Canvas canvas){
        boolean checkSingleClick = false;
        if(lastSingleClickTime > 0){
            long duration = System.currentTimeMillis() - lastSingleClickTime;
            if(duration >= doubleDuration){
                checkSingleClick = true;
                lastSingleClickTime = -1;
                tDownTime = -1;
                tUpTime = -1;
            }
        }
        if(checkSingleClick){
            if(status == 1){
                RectF recF = new RectF();
                Bitmap bm = bitmaps.get(9);
                recF.left = canvas.getWidth()/2-bm.getWidth()/2;
                recF.top = canvas.getHeight()*0.9f;
                recF.right = recF.left + bm.getWidth();
                recF.bottom = recF.top + bm.getHeight();
                if(recF.contains(tX, tY)){
                    status = 2;
                }
            }else if(status == 2){
                if(continueRect.contains((int)tX, (int)tY)){
                    status = 1;
                    postInvalidate();
                }}
        }

        super.onDraw(canvas);

        if(status == 1){
            drawGaming(canvas);
        }else if(status == 2){
            for(Planet p : planets){
                p.onDeploy(canvas, paint, this);
            }
            if(spaceShip != null){
                spaceShip.beforeDeploy(canvas, paint, this);
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#FFFFF0"));
            Rect rect1 = new Rect(300, 600, 750, 800);
            canvas.drawRect(rect1, paint);
            textPaint.setTextSize(60);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("scores: "+score, 530, 730, textPaint);
            Rect rect2 = new Rect(300,800,750,900);
            canvas.drawRect(rect2, paint);
            canvas.drawText("continue", 530, 830, textPaint);
            continueRect = new Rect(rect2);
            if(lastSingleClickTime > 0){
                postInvalidate();
            }
        }else if(status == 3){
            if (highestscore < score) {
                highestscore = score;
                // use a highest score to save the highest score in recent games if the user to choose the quit button.
            }
            new AlertDialog.Builder(getContext()).setTitle("Scores: "+ score).setMessage(
                    // use a dialog to show the scores and choices.
                    "The game is over ！").setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // restart the game.
                    globalCount = 0;
                    score = 0;
                    if(spaceShip != null){
                        spaceShip.destroy();
                    }
                    spaceShip = null;
                    for(Planet p : planets){
                        p.destroy();
                    }
                    planets.clear();
                    spaceShip = new SpaceShip(bitmaps.get(0));
                    status = 1;
                    postInvalidate();
                }
            }).setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // turn to scores activity, and send the highest score to it.
                    Intent intent = new Intent(getContext(), ScoresActivity.class);
                    intent.putExtra("Score", highestscore);
                    getContext().startActivity(intent);
                }
            }).show();
        }
    }

    private void drawGaming(Canvas canvas){
        RectF recF = new RectF();
        Bitmap bm = bitmaps.get(9);
        recF.left = canvas.getWidth()/2-bm.getWidth()/2;
        recF.top = canvas.getHeight()*0.9f;
        recF.right = recF.left + bm.getWidth();
        recF.bottom = recF.top + bm.getHeight();
        canvas.drawBitmap(bm, recF.left, recF.top, paint);
        //locate spaceship at center of the bottom of canvas
        if(globalCount == 0){
            spaceShip.centerTo(canvas.getWidth()*0.2f, canvas.getHeight()*0.8f);
        }
        // add toadd planets
        if(toAddPlanets.size() > 0){
            planets.addAll(toAddPlanets);
            toAddPlanets.clear();
        }

        Iterator<Planet> iterator = planets.iterator();
        while (iterator.hasNext()){
            Planet p = iterator.next();
            if(p.isDestroyed()){
                p.destroy();
            }
        }
        if(globalCount %70==0){
            int index = (int)Math.floor(100*Math.random());
            if(index%2==0){
                Planet p = new Mercury(bitmaps.get(4));
                float pW = p.getWidth();
                float pH = p.getHeight();
                float x = (float)((canvas.getWidth() - pW)*Math.random());
                float y = - pH;
                p.setX(x);
                p.setY(y);
                toAddPlanets.add(p);
            }else{
                Planet q = new Jupiter(bitmaps.get(6));
                float qW = q.getWidth();
                float qH = q.getHeight();
                float qx = (float)((canvas.getWidth() - qW)*Math.random());
                float qy = - qH;
                q.setX(qx);
                q.setY(qy);
                toAddPlanets.add(q);
            }
        }
        globalCount++;
        //draw all the items
        Iterator<Planet> iterator2 = planets.iterator();
        while (iterator2.hasNext()){
            Planet p = iterator2.next();
            if(!p.isDestroyed()){
                p.Deploy(canvas, paint, this);
            }
            if(p.isDestroyed()){
                p.destroy();
            }
        }
        if(spaceShip != null){
            spaceShip.Deploy(canvas, paint, this);
            if(spaceShip.isDestroyed()){
                status = 3;
            }
            postInvalidate();
        }

    }


    // deal with all touches actions
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        tX = event.getX();
        tY = event.getY();
        //1-move;  2-singleclick; 3-doubleclick
        int tType = -1;
        switch (action){
            case MotionEvent.ACTION_MOVE: long finishTime = System.currentTimeMillis() - tDownTime;
                if(finishTime > singleDuration){
                    tType = 1;
                }
                break;
            case MotionEvent.ACTION_DOWN: tDownTime = System.currentTimeMillis();break;
            case MotionEvent.ACTION_UP: tUpTime = System.currentTimeMillis();
                long finishDuration = tUpTime - tDownTime;
                if(finishDuration <= singleDuration){
                    long twoClicksDuration = tUpTime - lastSingleClickTime;
                    if(twoClicksDuration<=doubleDuration){
                        tType = 3;
                        lastSingleClickTime = -1;
                        tUpTime = -1;
                    }else{
                        lastSingleClickTime = tUpTime;
                    }
                }break;
        }
        // start:1   pause:2   over:3  destroy:4
        if(status == 1){
            if(tType == 1){
                if(spaceShip != null){
                    spaceShip.centerTo(tX, tY);
                }
            }else if(tType == 3){
                status=2;
                postInvalidate();
            }
        }else if(status == 2){
            if(lastSingleClickTime > 0){
                postInvalidate();
            }
        }else if(status == 3){
            if(lastSingleClickTime > 0){
                postInvalidate();
            }
        }
        return true;
    }


    public void remove(){
        status = 4;
        globalCount = 0;
        score = 0;
        if(spaceShip != null){
            spaceShip.destroy();
        }
        spaceShip = null;
        for(Planet p : planets){
            p.destroy();
        }
        planets.clear();
        // clear bitmaps
        for(Bitmap bitmap : bitmaps){
            bitmap.recycle();
        }
        bitmaps.clear();

    }


    public void addPlanet(Planet planet){
        toAddPlanets.add(planet);
    }

    public void changeScore(int grade){
        score += grade;
    }


    public Bitmap getLaserBitmap(){
        return bitmaps.get(2);
    }


}
