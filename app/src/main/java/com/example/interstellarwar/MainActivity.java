package com.example.interstellarwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "726dd8d3f9685b2c34d8e7170c9b2d3e");
    }

    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.button){
            startGame();
        }
    }

    public void onClick2(View v) {
        final GameScore gs = new GameScore();
        gs.setPlayerName("LeoNo1");
        gs.setScore(9999999);
        gs.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Add data success：" + s, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Log.i("bmob","fail："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void startGame(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
