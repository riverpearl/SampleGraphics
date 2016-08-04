package com.tacademy.samplegraphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    SurfaceView surfView;
    SurfaceHolder mHolder;
    Paint mPaint = new Paint();

    boolean isRunning = false;
    float startX = 0, endX = 300, startY = 300, endY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);

        surfView = (SurfaceView)findViewById(R.id.surfaceView);
        surfView.getHolder().addCallback(this);
        isRunning = true;
        new Thread(drawRunnable).start();
    }

    Runnable drawRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                if (mHolder != null) {
                    Canvas cv = null;

                    try {
                        cv = mHolder.lockCanvas();
                        draw(cv);
                    } catch (Exception e) {

                    } finally {
                        if (cv != null)
                            mHolder.unlockCanvasAndPost(cv);
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void draw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);

        canvas.drawLine(startX, startY, endX, endY, mPaint);

        startY -= 5;
        if (startY < 0) startY = 300;

        endX -= 5;
        if (endX < 0) endX = 300;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mHolder = surfaceHolder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mHolder = null;
    }
}
