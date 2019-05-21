package com.example.interstellarwar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ScoresActivity extends AppCompatActivity {
    private Integer score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        score = getIntent().getIntExtra("Score",0);


        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        String userscore = sp.getString("score", "");
        String userid = sp.getString("userid", "");
        Toast toast = Toast.makeText(getApplicationContext(), "userid is " + userid, Toast.LENGTH_SHORT);
        toast.show();
        if (!userscore.isEmpty()) {
            Integer uscore = Integer.parseInt(userscore);

            if (uscore < score) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("score", Integer.toString(score));
                editor.commit();
                GameScore gs = new GameScore();
                gs.setScore(score);
                gs.update(userid, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast toast = Toast.makeText(getApplicationContext(), "Update score success!", Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(),"Update fail ：" + e.getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        }

    }

    public void Restart(View v) {
        int viewId = v.getId();
        if(viewId == R.id.button2){
            startGame();
        }
    }

    public void startGame(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
