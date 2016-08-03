package com.tacademy.samplegraphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tacademy on 2016-08-03.
 */
public class CustomGraphicsView extends View {

    Paint mPaint;

    public CustomGraphicsView(Context context) {
        this(context, null);
    }

    public CustomGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        initPoint();
        initPath();
    }

    Path path;
    private void initPath() {
        path = new Path();
        path.moveTo(50, 50);
        path.lineTo(0, 0);
        path.lineTo(100, 0);
        path.lineTo(150, 50);
        path.lineTo(100, 100);
        path.lineTo(0, 100);
        path.addCircle(300, 300, 100, Path.Direction.CCW);
    }

    float[] points;
    private static final float START = 0, END = 1200, INTERVAL = 30;

    private void initPoint() {
        int count = (int)((END - START) / INTERVAL) + 1;
        points = new float[count * 2 * 2];

        for (int i = 0; i < count; i++) {
            points[i * 4 + 0] = START;
            points[i * 4 + 1] = START + i * INTERVAL;
            points[i * 4 + 2] = END - i * INTERVAL;
            points[i * 4 + 3] = START;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.LTGRAY);

        canvas.save();
        canvas.translate(x, y);

        //drawLineAndPoint(canvas);
        //drawRect(canvas);
        //drawCircle(canvas);
        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawPath(path, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(150, 150, 150, mPaint);

        RectF arc = new RectF(400, 0, 700, 300);
        mPaint.setColor(Color.RED);
        canvas.drawArc(arc, 45, 90, false, mPaint);

        mPaint.setColor(Color.CYAN);
        RectF oval = new RectF(0, 400, 600, 700);
        canvas.drawOval(oval, mPaint);
    }

    private void drawRect(Canvas canvas) {
        RectF rect = new RectF(0, 0, 300, 300);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rect, mPaint);

        RectF rRect = new RectF(0, 400, 300, 700);
        mPaint.setColor(Color.GREEN);
        canvas.drawRoundRect(rRect, 30, 50, mPaint);
    }

    private void drawLineAndPoint(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        canvas.drawLines(points, mPaint);
        mPaint.setAntiAlias(false);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        canvas.drawPoints(points, mPaint);
    }


    float x = 0, y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN :
                return true;
            case MotionEvent.ACTION_UP :
                x = event.getX();
                y = event.getY();
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
