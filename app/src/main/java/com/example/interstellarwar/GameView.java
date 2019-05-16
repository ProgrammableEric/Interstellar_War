package com.example.interstellarwar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameView extends View {

    //set class field
    private Paint paint;
    private TextPaint textPaint;
    private SpaceShip spaceShip = null;

    // initial the planets and to add planets
    private List<Planet> planets = new ArrayList<Planet>();
    private List<Planet> toAddPlanets = new ArrayList<Planet>();

    //0:spaceship
    //1:bombing
    //2:laser
    //4:mercury
    //5:mars
    //6:jupiter
    //7:nuclearCredit
    //9:pause1
    //10:pause2
    //11:nuclear
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private float density = getResources().getDisplayMetrics().density;
//    public static final int STATUS_GAME_STARTED = 1;
//    public static final int STATUS_GAME_PAUSED = 2;
//    public static final int STATUS_GAME_OVER = 3;
//    public static final int STATUS_GAME_DESTROYED = 4;
    // start=1  pause=2  over=3  destroyed=4
    private int status = 4;
    private long frame = 0;
    private long score = 0;
    private float fontSize = 12;
    private float fontSize2 = 20;
    private float borderSize = 2;
    private Rect continueRect = new Rect();
    //
    //    //触摸事件相关的变量
    //    private static final int TOUCH_MOVE = 1;//移动
    //    private static final int TOUCH_SINGLE_CLICK = 2;//单击
    //    private static final int TOUCH_DOUBLE_CLICK = 3;//双击
    //    //一次单击事件由DOWN和UP两个事件合成，假设从down到up间隔小于200毫秒，我们就认为发生了一次单击事件
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
        //beginView(null,0);
        paint = new Paint();
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.ANTI_ALIAS_FLAG);
        fontSize = textPaint.getTextSize();
        textPaint.setTextSize(15);
    }

//    public GameView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        beginView(attrs, 0);
//    }
//
//    public GameView(Context context, AttributeSet attrs, int style) {
//        super(context, attrs, style);
//        beginView(attrs, style);
//
//    }

//    private void beginView(AttributeSet attribs, int style){
//        final TypedArray a = getContext().obtainStyledAttributes(attribs, R.styleable.GameView, style, 0);
//        a.recycle();
//        // initial setting appearances
//        paint = new Paint();
//        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.ANTI_ALIAS_FLAG);
//        fontSize = textPaint.getTextSize();
//        textPaint.setTextSize(15);
//
//    }

    public void startSeting(int[] bmIds){
        remove();
        for(int Id : bmIds){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Id);
            bitmaps.add(bitmap);
        }
        spaceShip = new SpaceShip(bitmaps.get(0));
        status = 1;
        postInvalidate();
    }


    private void restart(){
        status = 4;
        frame = 0;
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

    public void pause(){
        status = 2;
    }

    private void setGameState(){
        status = 1;
        postInvalidate();
    }

    private long caculateScores(){
        return score;
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
                Bitmap pauseBitmap = bitmaps.get(9);
                RectF recF = new RectF();
                recF.left = 15 * density;
                recF.top = 15 * density;
                recF.right = recF.left + pauseBitmap.getWidth();
                recF.bottom = recF.top + pauseBitmap.getHeight();
                if(recF.contains(tX, tY)){
                    status = 2;
                }
            }else if(status == 2){
                if(continueRect.contains((int)tX, (int)tY)){
                    status = 1;
                    postInvalidate();
                }
            }else if(status == 3){
                if(continueRect.contains((int)tX, (int)tY)){
                    status = 4;
                    frame = 0;
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
            }
        }

        super.onDraw(canvas);

        if(status == 1){
            drawGaming(canvas);
        }else if(status == 2){
            drawPausing(canvas);
        }else if(status == 3){
            drawOver(canvas);
        }
    }

    private void drawGaming(Canvas canvas){
        //drawScoreAndBombs(canvas);
        //locate spaceship at center of the bottom of canvas
        if(frame == 0){
            spaceShip.centerTo(canvas.getWidth() / 2, canvas.getHeight() - spaceShip.getHeight() / 2);
        }

        // add toadd planets
        if(toAddPlanets.size() > 0){
            planets.addAll(toAddPlanets);
            toAddPlanets.clear();
        }

        //检查战斗机跑到子弹前面的情况
//        destroyBulletsFrontOfCombatAircraft();

        Iterator<Planet> iterator = planets.iterator();
        while (iterator.hasNext()){
            Planet p = iterator.next();
            if(p.isDestroyed()){
                iterator.remove();
            }
        }

        //每隔30帧随机添加Sprite
//        if(frame % 50 == 0){
//            addPlanets(canvas.getWidth());
//        }
        frame++;

        //draw all the items
        Iterator<Planet> iterator2 = planets.iterator();
        while (iterator2.hasNext()){
            Planet p = iterator2.next();
            if(!p.isDestroyed()){
                p.Deploy(canvas, paint, this);
            }
            if(p.isDestroyed()){
                iterator.remove();
            }
        }
        if(spaceShip != null){
            spaceShip.Deploy(canvas, paint, this);
            if(spaceShip.isDestroyed()){
                status = 4;
            }
            postInvalidate();
        }

    }

    private void drawPausing(Canvas canvas){
        //drawScoreAndBombs(canvas);
        for(Planet p : planets){
            p.onDeploy(canvas, paint, this);
        }
        if(spaceShip != null){
            spaceShip.beforeDeploy(canvas, paint, this);
        }
        drawScoreResults(canvas, "Continue");
        if(lastSingleClickTime > 0){
            postInvalidate();
        }
    }

    private void drawOver(Canvas canvas){
        drawScoreResults(canvas, "Restart");
        if(lastSingleClickTime > 0){
            postInvalidate();
        }
    }

    private void drawScoreResults(Canvas canvas, String operation){
        int cW = canvas.getWidth();
        int cH = canvas.getHeight();
//        float originalFontSize = textPaint.getTextSize();
//        Paint.Align originalFontAlign = textPaint.getTextAlign();
//        int originalColor = paint.getColor();
//        Paint.Style originalStyle = paint.getStyle();
        /*
        W = 360
        w1 = 20
        w2 = 320
        buttonWidth = 140
        buttonHeight = 42
        H = 558
        h1 = 150
        h2 = 60
        h3 = 124
        h4 = 76
        */
        int w1 = (int)(0.05 * cW);
        int w2 = cW - 2 * w1;
        int buttonWidth = (int)(0.25 * cW);

        int h1= (int)(0.2 * cH);
        int h2 = (int)(0.1 * cH);
        int h3 = (int)(0.2 * cH);
        int h4 = (int)(0.1 * cH);
        int buttonHeight = (int)(0.1 * cH);

        canvas.translate(w1, h1);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFD7DDDE);
        Rect rect1 = new Rect(0, 0, w2, cH - 2 * h1);
        canvas.drawRect(rect1, paint);
        textPaint.setTextSize(16);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("final scores: ", w2 / 2, (h2 - 16) / 2 + 16, textPaint);

        String allScore = String.valueOf(caculateScores());
        canvas.drawText(allScore, w2 / 2, (h3 - 16) / 2 + 16, textPaint);

        Rect rect2 = new Rect();
        rect2.left = (w2 - buttonWidth) / 2;
        rect2.right = w2 - rect2.left;
        rect2.top = (h4 - buttonHeight) / 2;
        rect2.bottom = h4 - rect2.top;
        canvas.drawRect(rect2, paint);
        canvas.translate(0, rect2.top);
        canvas.drawText(operation, w2 / 2, (buttonHeight - 16) / 2 + 16, textPaint);
        continueRect = new Rect(rect2);
        continueRect.left = w1 + rect2.left;
        continueRect.right = continueRect.left + buttonWidth;
        continueRect.top = h1 + h2 + h3 + rect2.top;
        continueRect.bottom = continueRect.top + buttonHeight;

//        textPaint.setTextSize(originalFontSize);
//        textPaint.setTextAlign(originalFontAlign);
//        paint.setColor(originalColor);
//        paint.setStyle(originalStyle);

    }

    private void drawScoreAndBombs(Canvas canvas){

        // try to hide this
        Bitmap pauseBm;
        if(status==1){
            pauseBm = bitmaps.get(9);
        }else{
            pauseBm = bitmaps.get(10);
        }
        RectF recF = new RectF();
        recF.left = 15 * density;
        recF.top = 15 * density;
        recF.right = recF.left + pauseBm.getWidth();
        recF.bottom = recF.top + pauseBm.getHeight();
        float pauseLeft = recF.left;
        float pauseTop = recF.top;
        canvas.drawBitmap(pauseBm, pauseLeft, pauseTop, paint);
        float scoreLeft = pauseLeft + pauseBm.getWidth() + 20 * density;
        float scoreTop = fontSize + pauseTop + pauseBm.getHeight() / 2 - fontSize / 2;
        canvas.drawText(score + "", scoreLeft, scoreTop, textPaint);

        //绘制左下角
        if(spaceShip != null && !spaceShip.isDestroyed()){
            int num = spaceShip.getNuclearNo();
            if(num > 0){
                //绘制左下角的炸弹
                Bitmap bombBitmap = bitmaps.get(11);
                float bombTop = canvas.getHeight() - bombBitmap.getHeight();
                canvas.drawBitmap(bombBitmap, 0, bombTop, paint);
                //绘制左下角的炸弹数量
                float bombCountLeft = bombBitmap.getWidth() + 10 * density;
                float bombCountTop = fontSize + bombTop + bombBitmap.getHeight() / 2 - fontSize / 2;
                canvas.drawText("X " + num, bombCountLeft, bombCountTop, textPaint);
            }
        }

    }

    // when this aircraft is in front of the bullets
    private void destroyBulletsFront(){

    }


    private void addPlanets(int Width){
        Planet p = null;
        int speed = 4;
        //callTime表示createRandomSprites方法被调用的次数
        int callTime = Math.round(frame / 50);
        if((callTime + 1) % 25 == 0){
            //发送道具奖品
            if((callTime + 1) % 50 == 0){
                p = new LaserCredit(bitmaps.get(8));
            }
        }
        else{
            //发送敌机
            int[] nums = {0,0,0,0,0,1,0,0,1,0,0,0,0,1,1,1,1,1,1,0};
            int index = (int)Math.floor(nums.length*Math.random());
            int type = nums[index];
            if(type == 0){
                p = new Mercury(bitmaps.get(4));
            }
            else {
                p = new Jupiter(bitmaps.get(5));
                speed=2;
            }

        }

        if(p != null){
            float pW = p.getWidth();
            float pH = p.getHeight();
            float x = (float)((Width - pW)*Math.random());
            float y = - pH;
            p.setX(x);
            p.setY(y);
            if(p instanceof NewStar){
                NewStar nStar = (NewStar) p;
                nStar.setSpeed(speed);
            }
            toAddPlanets.add(p);
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
                    }}break;
            default:lastSingleClickTime = tUpTime;break;
        }
        // start:1   pause:2   over:3  destroy:4
        if(status == 1){
            if(tType == 1){
                if(spaceShip != null){
                    spaceShip.centerTo(tX, tY);
                }
            }else if(tType == 3){
                if(status == 1){
                    if(spaceShip != null){
                        spaceShip.laser(this);
                    }
                }
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



    ///////////////////////////////

    public void remove(){
        status = 4;
        frame = 0;
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

    public float getDensity(){
        return density;
    }

    public int getState(){
        return status;
    }

    public float getResult(){
        return 0f;
    }

    public Bitmap getYellowBulletBitmap(){
        return bitmaps.get(2);
    }

    public Bitmap getBlueBulletBitmap(){
        return bitmaps.get(3);
    }

    public Bitmap getExplosionBitmap(){
        return bitmaps.get(1);
    }

    public List<Planet> getAliveEnemyPlanes(){
        List<Planet> planetList = new ArrayList<Planet>();
        for(Planet p : planets){
            if(!p.isDestroyed() && p instanceof Star){
                Star s = (Star) p;
                planetList.add(s);
            }
        }
        return planetList;
    }

    public List<NuclearCredit> getAliveBombAwards(){
        return null;
    }

    public List<LaserCredit> getAliveBulletAwards(){
        return null;
    }

    public List<Laser> getAliveBullets(){
        List<Laser> ll = new ArrayList<Laser>();
        for(Planet p : planets){
            if(!p.isDestroyed() && p instanceof Laser){
                Laser l = (Laser) p;
                ll.add(l);
            }
        }
        return ll;
    }
    }


}
