package com.example.interstellarwar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * author: Yuyuan Liang
 */

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "726dd8d3f9685b2c34d8e7170c9b2d3e");
        //initial bmob

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        String username = sp.getString("username", "");
        // check the local file if there already is a username

        if (username.isEmpty()) {
            // if not, show a input dialog
            Toast toast = Toast.makeText(getApplicationContext(), "no user name", Toast.LENGTH_SHORT);
            toast.show();
            showInputDialog();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Hi!, "+ username+" login succeed", Toast.LENGTH_SHORT);
            toast.show();
            // if yes, show the username in the center of layout
            tv = (TextView) findViewById(R.id.textView2);
            tv.setText(username);
            tv.setGravity(Gravity.CENTER);
        }
    }

    private void showInputDialog() {
        // show a new input dialog to regist a new user
        final EditText editText = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(MainActivity.this);
        inputDialog.setCancelable(false);
        inputDialog.setTitle("Please enter your name").setView(editText);
        inputDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String username = editText.getText().toString();

                        if (username.isEmpty()) { // check the input if it's empty
                            Toast toast = Toast.makeText(getApplicationContext(), "Can not enter empty name!", Toast.LENGTH_SHORT);
                            toast.show();
                            showInputDialog();
                        } else {
                            // used a query to find the username in database, if the name exited, repeat registing
                            BmobQuery<GameScore> query = new BmobQuery<GameScore>();
                            query.addWhereEqualTo("playerName", username);
                            query.setLimit(1);
                            query.findObjects(new FindListener<GameScore>() {
                                @Override
                                public void done(List<GameScore> list, BmobException e) {
                                    if (e == null) {
                                        if (list.isEmpty()) {
                                            // if the username is not existed, upload a new user
                                            GameScore gs = new GameScore();
                                            gs.setPlayerName(username);
                                            gs.setScore(0);
                                            // set the score as 0
                                            gs.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String objectid, BmobException e) {
                                                    if (e == null) {

                                                        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                                                        //at the same time, save the user information in local file.
                                                        SharedPreferences.Editor editor = sp.edit();
                                                        editor.putString("username", username);
                                                        editor.putString("score", "0");
                                                        editor.putString("userid", objectid);
                                                        // save the objectid to update info after
                                                        editor.commit();
                                                        Toast toast = Toast.makeText(getApplicationContext(), objectid+" Create User success：" + username, Toast.LENGTH_SHORT);
                                                        toast.show();
                                                    } else {
                                                        Log.i("bmob", "fail：" + e.getMessage() + "," + e.getErrorCode());
                                                    }
                                                }
                                            });


                                            tv = (TextView) findViewById(R.id.textView2);
                                            tv.setText(username);
                                            tv.setGravity(Gravity.CENTER);

                                        } else {
                                            Toast.makeText(MainActivity.this, "This username already exists", Toast.LENGTH_LONG).show();
                                            showInputDialog();
                                        }
                                    } else {
                                        Log.i("bmob", "fail：" + e.getMessage() + "," + e.getErrorCode());
                                    }
                                }
                            });
                        }
                    }
                }).show();
    }

    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.button){
            startGame();
        }
    }

    public void startGame(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void clean(View v) {
        //clean the user info in local file, and restart the main activity.
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", "");
        editor.putString("score", "0");
        editor.putString("userid", "");
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
