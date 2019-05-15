package com.example.interstellarwar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class GameView extends View {

    //set class field
    private Paint paint;
    private TextPaint textPaint;
    private SpaceShip spaceShip = null;

    //private List<Sprite> sprites = new ArrayList<Sprite>();
    //private List<Sprite> spritesNeedAdded = new ArrayList<Sprite>();

    //    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    //    private float density = getResources().getDisplayMetrics().density;//屏幕密度
    //    public static final int STATUS_GAME_STARTED = 1;//游戏开始
    //    public static final int STATUS_GAME_PAUSED = 2;//游戏暂停
    //    public static final int STATUS_GAME_OVER = 3;//游戏结束
    //    public static final int STATUS_GAME_DESTROYED = 4;//游戏销毁
    //    private int status = STATUS_GAME_DESTROYED;//初始为销毁状态
    //    private long frame = 0;//总共绘制的帧数
    //    private long score = 0;//总得分
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
    //    private static final int singleClickDurationTime = 200;
    //    //一次双击事件由两个点击事件合成，两个单击事件之间小于300毫秒，我们就认为发生了一次双击事件
    //    private static final int doubleClickDurationTime = 300;
    //    private long lastSingleClickTime = -1;//上次发生单击的时刻
    //    private long touchDownTime = -1;//触点按下的时刻
    //    private long touchUpTime = -1;//触点弹起的时刻
    //    private float touchX = -1;//触点的x坐标
    //    private float touchY = -1;//触点的y坐标


    private long score = 0;//total grades for the reuslt

    public GameView(Context context) {
        super(context);
        beginView();
    }

    public GameView(Context context, AttributeSet attrs) {

    }

    public GameView(Context context, AttributeSet attrs, int default) {

    }

    private void beginView(AttributeSet attribs, int style){
        final TypedArray a = getContext().obtainStyledAttributes(attribs, R.styleable.GameView, style, 0);
        a.recycle();
        // set appearances
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xff000000);
        fontSize = textPaint.getTextSize();
//        fontSize *= density;
//        fontSize2 *= density;
//        textPaint.setTextSize(fontSize);
//        borderSize *= density;

    }

    public void startSeting(int[] bmIds){

    }

    private void setAfterViewsReady(){

    }

    private void restart(){

    }

    public void pause(){

    }

    private void setGameState(){

    }

    private long caculateScores(){
        return score;
    }


    // Draw all the game views

    protected void onDraw(Canvas canvas){

    }

    private void drawGaming(Canvas canvas){

    }

    private void drawPausing(Canvas canvas){

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
        return  false;
    }

    private int setTouch(MotionEvent event){
        return 0;
    }

    private boolean checkSingleClick(){
        return false;
    }

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
