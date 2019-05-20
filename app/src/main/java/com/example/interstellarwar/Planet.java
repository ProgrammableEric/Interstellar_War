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

    private float speed = 2; // the pixel number of each movement, go down for positive
    private boolean right = true;
    private boolean left = true;
    private static int total = 0;
    private int num;

    public Planet(Bitmap bm){
        this.bm = bm;
        total++;
        this.num = total;
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

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
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

    // Get the relative position of bounding rectangle of planet after colliding
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

    // Planet before deploy, laser move speed along the y axis
    protected void beforeDeploy(Canvas canvas, Paint paint, GameView gameView){
        if(!isDestroyed()){
            if(this instanceof Laser){
                move(0, speed * gameView.getDensity());
            }else{
                if(num%2==0){
                    RectF planetRecF = getPlanet();
                    if(planetRecF.right>=canvas.getWidth()){
                        right=false;
                    }
                    if(planetRecF.left<=0){
                        right=true;
                    }
                    if(right){
                        move(3, speed * gameView.getDensity());
                    }else{
                        move(-3, speed * gameView.getDensity());
                    }
                }else{
                    RectF planetRecF = getPlanet();
                    if(planetRecF.right>=canvas.getWidth()){
                        right=true;
                    }
                    if(planetRecF.left<=0){
                        left=false;
                    }
                    if(left){
                        move(-3, speed * gameView.getDensity());
                    }else{
                        move(3, speed * gameView.getDensity());
                    }
                }

            }

        }
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

    // Planet after deploy, check if planet exceeds the range of Canvas, if yes, then destroy planet
    protected void finishDeploy(Canvas canvas, Paint paint, GameView gameView){
        if(!isDestroyed()){
            RectF canvasRecF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            RectF planetRecF = getPlanet();
            if(!RectF.intersects(canvasRecF, planetRecF)){
                destroy();
            }
        }
    }

    // Destroy a planet
    public void destroy(){
        bm = null;
        destroyed = true;
    }

    // Planet is destroyed
    public boolean isDestroyed(){
        return destroyed;
    }

    public int getTimes(){
        return times;
    }
}