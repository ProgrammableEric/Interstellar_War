package com.example.interstellarwar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    private int status = 4;//destroyed
    private long frame = 0;
    private long score = 0;
    private float fontSize = 12;
    private float fontSize2 = 20;
    private float borderSize = 2;
    //    private Rect continueRect = new Rect();//"继续"、"重新开始"按钮的Rect
    //
    //    //触摸事件相关的变量
    //    private static final int TOUCH_MOVE = 1;//移动
    //    private static final int TOUCH_SINGLE_CLICK = 2;//单击
    //    private static final int TOUCH_DOUBLE_CLICK = 3;//双击
    //    //一次单击事件由DOWN和UP两个事件合成，假设从down到up间隔小于200毫秒，我们就认为发生了一次单击事件
    private static final int singleDuration = 200;
    //    //一次双击事件由两个点击事件合成，两个单击事件之间小于300毫秒，我们就认为发生了一次双击事件
    private static final int doubleDuration = 300;
    private long lastSingleClickTime = -1;
    private long tDownTime = -1;
    private long tUpTime = -1;
    private float tX = -1;
    private float tY = -1;


    private long score = 0;//total grades for the reuslt

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
        //destroy();
        for(int Id : bmIds){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Id);
            bitmaps.add(bitmap);
        }
        spaceShip = new SpaceShip(bitmaps.get(0));
        status = 1;
        postInvalidate();
    }
    

    private void restart(){
        destroyNotRecyleBitmaps();
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
                if(isClickPause(x, y)){
                    pause();
                }
            }else if(status == 2){
                if(isClickContinueButton(x, y)){
                    resume();
                }
            }else if(status == 3){
                if(isClickRestartButton(x, y)){
                    restart();
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
        drawScoreAndBombs(canvas);

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

        //在绘制之前先移除掉已经被destroyed的Sprite
        removeDestroyedSprites();

        //每隔30帧随机添加Sprite
        if(frame % 50 == 0){
            createRandomSprites(canvas.getWidth());
        }
        frame++;

        //遍历sprites，绘制敌机、子弹、奖励、爆炸效果
        Iterator<Planet> iterator = planets.iterator();
        while (iterator.hasNext()){
            Planet p = iterator.next();

            if(!p.isDestroyed()){
                //在Sprite的draw方法内有可能会调用destroy方法
                p.draw(canvas, paint, this);
            }

            //我们此处要判断Sprite在执行了draw方法后是否被destroy掉了
            if(p.isDestroyed()){
                //如果Sprite被销毁了，那么从Sprites中将其移除
                iterator.remove();
            }
        }

        if(spaceShip != null){
            //最后绘制战斗机
            spaceShip.draw(canvas, paint, this);
            if(spaceShip.isDestroyed()){
                //如果战斗机被击中销毁了，那么游戏结束
                status = 4;
            }
            //通过调用postInvalidate()方法使得View持续渲染，实现动态效果
            postInvalidate();
        }

    }

    private void drawPausing(Canvas canvas){
        drawScoreAndBombs(canvas);

        //调用Sprite的onDraw方法，而非draw方法，这样就能渲染静态的Sprite，而不让Sprite改变位置
        for(Planet p : planets){
            p.onDeploy(canvas, paint, this);
        }
        if(spaceShip != null){
            spaceShip.beforeDeploy(canvas, paint, this);
        }

        //绘制Dialog，显示得分
        drawScoreDialog(canvas, "continue");

        if(lastSingleClickTime > 0){
            postInvalidate();
        }

    }

    private void drawOver(Canvas canvas){

    }

    private void drawScoreResults(Canvas canvas, String operation){

    }

    private void drawScoreAndBombs(Canvas canvas){

    }

    // when this aircraft is in front of the bullets
    private void destroyBulletsFront(){

    }

    // remove the rivals
    private void removePlanets(){

    }

    private void deployPlanets(int Width){

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


//    private boolean checkSingleClick(){
//        return false;
//    }

    private void onSingleClick(float x, float y){

    }

    private boolean PauseClick(float x, float y){
        return false;
    }

    // continue game when game is being paused
    private boolean ContinueGame(float x, float y){
        return false;
    }

    private boolean RestartGame(float x, float y){
        return false;
    }

    // getBitMaps when pausing
    private RectF getBitmapDstRecF(){
        return null;
    }


    ///////////////////////////////
    // remove the bitmaps which are cant be resued
    private void removeBitmaps(){
    }


    public void remove(){

    }


    ////////////////
    public void addPlanet(Planet planet){

    }

    public void changeScore(int grade){
        // score += grade
    }

    public int getState(){
        return 0;
    }

    public float getResult(){
        return 0f;
    }

    public Bitmap getYellowBulletBitmap(){
        return null;
    }

    public Bitmap getBlueBulletBitmap(){
        return null;
    }

    public Bitmap getExplosionBitmap(){
        return null;
    }

    public List<NewStar> getAliveEnemyPlanes(){
        return null;
    }

    public List<NuclearCredit> getAliveBombAwards(){
        return null;
    }

    public List<LaserCredit> getAliveBulletAwards(){
        return null;
    }

    public List<Laser> getAliveBullets(){
        return null;
    }


}
