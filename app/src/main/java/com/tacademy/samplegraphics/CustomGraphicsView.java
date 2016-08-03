package com.tacademy.samplegraphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
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

        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        canvas.drawLines(points, mPaint);
        mPaint.setAntiAlias(false);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        canvas.drawPoints(points, mPaint);
    }
}
