package com.junhee.android.threadbasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ThreadBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_basic);

        // ===================== [ 쓰레드 만들기 1 ] =======================
        // 1. 쓰레드 생성
        Thread thread = new Thread() {
            @Override
            public void run() {
                Log.i("Thread Test", "=================[Hello Thread!]");
            }
        };
        // 2. 쓰레드 실행
        thread.start(); // run(); 함수를 실행시켜줌
//
//        new Thread(){
//            @Override
//            public void run() {
//                Log.i("Thread Test", "=================[Hello Thread!]");
//            }
//        };
//        // 2. 쓰레드 실행
//        thread.start(); // run(); 함수를 실행시켜줌
//    }

        // ===================== [ 쓰레드 만들기 2 ] =======================
        // implenment Runnable

        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                Log.i("Thread test", "Hello Thread!");
            }
        };
        // 쓰레드 객체로 맏들어 준 후, start();를 할 수 있음
        new Thread(thread2).start();

        // ================== [ 쓰레드 만들기 3 ] ==================
        CustomThread thread3 = new CustomThread();
        thread3.start();

        // ==========[ 쓰레드 안에 러너블 심기 ] ==================
        Thread thread4 = new Thread(new CustomRunnable());
        thread4.start();

    }
}


class CustomThread extends Thread {
    @Override
    public void run() {
        Log.i("Thread test", "Hello CustomThread");
    }
}

class CustomRunnable implements Runnable {

    @Override
    public void run() {
        Log.i("Thread test", "Hello CustomRunnable");
    }
}
