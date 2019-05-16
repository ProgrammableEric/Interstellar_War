package com.example.interstellarwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
