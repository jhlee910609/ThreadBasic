package com.junhee.android.threadbasic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class RainActivity extends AppCompatActivity {

    FrameLayout ground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);

        ground = (FrameLayout) findViewById(R.id.stage);

        // 커스텀 뷰를 생성하고
        Stage stage = new Stage(getBaseContext());

        // 레이아웃에 담아주면 화면에 커스텀뷰의 내용이 출력된다.
        ground.addView(stage);
    }

    class Stage extends View{
        Paint paint;

        public Stage(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.BLUE);
        }

        // 화면에 로드되는 순간 호출되는 함수
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //              x좌표, y좌표, 반지름, 컬러 굵기 등
            canvas.drawCircle(100, 100, 30, paint);
        }
    }
}

