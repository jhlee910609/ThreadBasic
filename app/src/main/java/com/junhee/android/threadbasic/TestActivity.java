package com.junhee.android.threadbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class TestActivity extends AppCompatActivity {

    int count =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 1. 버튼을 클리하면 1부터 10만까지 출력하는 함수 실행
        findViewById(R.id.btnRun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new LogThread().start();
            }
        });

        // 2, Thread 에서 1부터 10만까지 출력하는 함수를 실행
        new LogThread().start();

        // 3. 위의 Thread 클래스의 실행순서를 1번과 바꿔서 실행
    }


    public void print100T(String caller) {
        // 중요하게 순서대로 찍어야 한다면 시스템 프린트 아웃으로 함
        // 로그 자체가 서버 스레드에서 찍음
        // 시스템 프린트 아웃은 메인 스레드에서 찍음
        for (int i = 0; i < 100; i++) {
            Log.i("Thread test", caller + " : " + "Current Number ==============  [ " + i + " ]");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class LogThread extends Thread{
        @Override
        public void run() {
            count ++;
            print100T("Sub Thread" + count);
            }
        }
    }

