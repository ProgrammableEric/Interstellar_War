package com.example.interstellarwar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ScoresActivity extends AppCompatActivity {
    private Integer score = 0;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        score = getIntent().getIntExtra("Score",0);


        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        String userscore = sp.getString("score", "");
        String userid = sp.getString("userid", "");

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


        final Context context = getApplicationContext();
        BmobQuery<GameScore> query = new BmobQuery<GameScore>();
        query.order("-score");
        query.setLimit(10);
        query.findObjects(new FindListener<GameScore>() {
            @Override
            public void done(List<GameScore> list, BmobException e) {
                if (e == null) {

                    if (!list.isEmpty()) { //list the 10 highest scores  in list
                        ArrayList<String> scores = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            int rank = i+1;
                            scores.add("No "+rank+" :  "+list.get(i).getPlayerName()+ "    Scores :  "+ list.get(i).getScore());
                        }
                        listView = (ListView) findViewById(R.id.listview);
                        ListAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, scores);
                        // use a adapter to list the scores in arraylist
                        listView.setAdapter(adapter);
                    }

                } else {
                    Log.i("bmob", "fail：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

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
