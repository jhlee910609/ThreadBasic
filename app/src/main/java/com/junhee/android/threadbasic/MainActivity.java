package com.junhee.android.threadbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnTest, btnBasic, btnRain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBasic = (Button) findViewById(R.id.btnBasic);
        btnTest = (Button) findViewById(R.id.btnTest);
        btnRain = (Button) findViewById(R.id.btnRain);

        btnBasic.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnRain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.btnBasic:
                intent = new Intent(this, ThreadBasicActivity.class);
                break;

            case R.id.btnTest:
                intent = new Intent(this, TestActivity.class);
                break;

            case R.id.btnRain:
                intent = new Intent(this, RainActivity.class);
                break;
        }
        startActivity(intent);
    }
}

