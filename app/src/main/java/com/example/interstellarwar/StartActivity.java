package com.example.interstellarwar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        gameView = findViewById(R.id.gameView);
        //0:combatAircraft
        //1:explosion
        //2:yellowBullet
        //3:blueBullet
        //4:smallEnemyPlane
        //5:middleEnemyPlane
        //6:bigEnemyPlane
        //7:bombAward
        //8:bulletAward
        //9:pause1
        //10:pause2
        //11:bomb
        int[] bitmapIds = {
                R.drawable.spaceship,
                R.drawable.explosion,
                R.drawable.yellow_bullet,
                R.drawable.blue_bullet,
                R.drawable.mercury,
                R.drawable.mars,
                R.drawable.jupiter,
                R.drawable.bomb_award,
                R.drawable.bullet_award,
                R.drawable.pause1,
                R.drawable.pause2,
                R.drawable.bomb
        };
        gameView.startSeting(bitmapIds);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gameView != null){
            gameView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(gameView != null){
            gameView.remove();
        }
        gameView = null;
    }
}
