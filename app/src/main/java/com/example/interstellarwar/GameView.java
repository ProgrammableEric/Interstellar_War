package com.example.interstellarwar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class GameView extends View {

    private long score = 0;//total grades for the reuslt

    public GameView(Context context) {
        super(context);
        beginView();
    }

    public GameView(Context context, AttributeSet attrs) {

    }

    public GameView(Context context, AttributeSet attrs, int default) {

    }

    private void beginView(){

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
        return touchAction;
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
        return state;
    }

    public float getResult(){
        return result;
    }

    public Bitmap getYellowBulletBitmap(){
        return bmps.get(2);
    }

    public Bitmap getBlueBulletBitmap(){
        return bmps.get(3);
    }

    public Bitmap getExplosionBitmap(){
        return bmps.get(1);
    }

    public List<RivalFlights> getAliveEnemyPlanes(){
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
