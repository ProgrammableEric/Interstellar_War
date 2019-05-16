package com.example.interstellarwar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * the basic class for creating rival planets
 */
public class Planet {
    private boolean visible = true; //if visible to user
    private float x = 0; //x-coordinate
    private float y = 0; //y-coordinate
    private float Offset = 0; //offset for collide
    private Bitmap bm;
    private boolean destroyed = false; //if planet is destroyed
    private int times = 0; //the deploy times

    public Planet(Bitmap bm){
        this.bm = bm;
    }

    public Bitmap getBitmap() {
        return bm;
    }

    // Set planet to be visible
    public void setVisibility(boolean visible){
        this.visible = visible;
    }

    // If planet is visible
    public boolean getVisibility(){
        return visible;
    }

    // Set the x-coordinate of planet
    public void setX(float x){
        this.x = x;
    }

    // Get the x-coordinates of planet
    public float getX(){
        return x;
    }

    // Set the y-coordinate of planet
    public void setY(float y){
        this.y = y;
    }

    // Get the y-coordinates of planet
    public float getY(){
        return y;
    }

    // Get the width of planet
    public float getWidth(){
        if(bm != null){
            return bm.getWidth();
        }
        return 0;
    }

    // Get the height of planet
    public float getHeight(){
        if(bm != null){
            return bm.getHeight();
        }
        return 0;
    }

    // Move planet by relative position
    public void move(float offsetX, float offsetY){
        x += offsetX;
        y += offsetY;
    }

    // Move planet to fix position
    public void moveTo(float x, float y){
        this.x = x;
        this.y = y;
    }

    // Fix the center of planet to a position
    public void centerTo(float centerX, float centerY){
        float width = getWidth();
        float height = getHeight();
        x = centerX - width / 2;
        y = centerY - height / 2;
    }

    // Get the relative position of bounding rectangle of planet
    public RectF getPlanet(){
        float left = x;
        float top = y;
        float right = left + getWidth();
        float bottom = top + getHeight();
        RectF planet = new RectF(left, top, right, bottom);
        return planet;
    }

    // Get the absolute position of bounding rectangle of planet
    public Rect getBitmapPos (){
        Rect bmPos = new Rect();
        bmPos.left = 0;
        bmPos.top = 0;
        bmPos.right = (int)getWidth();
        bmPos.bottom = (int)getHeight();
        return bmPos;
    }

    // Get the relative position of bounding rFectangle of planet after colliding
    public RectF getCollidedPlanet() {
        RectF planet = getPlanet();
        planet.left -= Offset;
        planet.right += Offset;
        planet.top -= Offset;
        planet.bottom += Offset;
        return planet;
    }

    // Get the absolute position of bounding rectangle of planet after colliding
    public Point getCollidedBitmapPos(Planet p){
        Point point = null;
        RectF planet1 = getCollidedPlanet();
        RectF planet2 = p.getCollidedPlanet();
        RectF planet = new RectF();
        boolean isIntersect = planet.setIntersect(planet1, planet2);
        if(isIntersect){
            point = new Point(Math.round(planet.centerX()), Math.round(planet.centerY()));
        }
        return point;
    }

    // Deploy the planet
    public final void Deploy(Canvas canvas, Paint paint, GameView gameView){
        times++;
        beforeDeploy(canvas, paint, gameView);
        onDeploy(canvas, paint, gameView);
        finishDeploy(canvas, paint, gameView);
    }

    // Planet before deploy
    protected void beforeDeploy(Canvas canvas, Paint paint, GameView gameView){
    }

    // Planet on deploy
    public void onDeploy(Canvas canvas, Paint paint, GameView gameView){
        if(!destroyed && this.bm != null && getVisibility()){
            //draw planet on the board
            Rect srcRef = getBitmapPos();
            RectF dstRecF = getPlanet();
            canvas.drawBitmap(bm, srcRef, dstRecF, paint);
        }
    }

    // Planet after deploy
    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){}

    // Destroy a planet
    public void destroy(){
        bm = null;
        destroyed = true;
    }

    // Planet is detroyed
    public boolean isDestroyed(){
        return destroyed;
    }

    public int getTimes(){
        return times;
    }
}