package com.tacademy.samplegraphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
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

    GestureDetector mDetector;

    public CustomGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView, R.attr.customViewStyle, 0);
            Drawable d = ta.getDrawable(R.styleable.CustomView_image);
            if (d != null && d instanceof BitmapDrawable) {
                mBitmap = ((BitmapDrawable) d).getBitmap();
            }
            ta.recycle();
        }

        mPaint = new Paint();
        initPoint();
        initPath();
        initBitmap();

        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                matrix.postTranslate(-distanceX, -distanceY);
                invalidate();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                matrix.postScale(1.5f, 1.5f);
                invalidate();
                return true;
            }
        });
    }

    public void changeBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        requestLayout();
        invalidate();
    }

    Bitmap mBitmap;
    Matrix matrix;
    float[] meshVertices;

    private void initBitmap() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_0);
        matrix = new Matrix();
        matrix.reset();

        float dx = mBitmap.getWidth() / 4.0f;
        float height = mBitmap.getHeight();
        meshVertices = new float[] {
                0, 0,  dx , 50,  dx * 2 , 0 ,  dx * 3 , 50 , dx * 4 , 0,
                0, height, dx, height - 50, dx * 2, height, dx * 3, height - 50, dx * 4, height
        };
    }

    Path path, textPath, arrowPath;

    private void initPath() {
        path = new Path();
        path.moveTo(50, 50);
        path.lineTo(0, 0);
        path.lineTo(100, 0);
        path.lineTo(150, 50);
        path.lineTo(100, 100);
        path.lineTo(0, 100);
        path.addCircle(300, 300, 100, Path.Direction.CCW);

        textPath = new Path();
        textPath.addCircle(300, 300, 150, Path.Direction.CW);

        arrowPath = new Path();
        arrowPath.lineTo(-5, -5);
        arrowPath.lineTo(0, -5);
        arrowPath.lineTo(5, 0);
        arrowPath.lineTo(0, 5);
        arrowPath.lineTo(-5, 5);
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
        //drawPath(canvas);
        //drawText(canvas);
        //drawBitmap(canvas);
        //drawBitmapMesh(canvas);
        //drawPathEffect(canvas);
        //drawPathDashPathEffect(canvas);
        //drawColor(canvas);
        //drawShader(canvas);
        //drawColorFilter(canvas);
        drawSimpleBitmap(canvas);

        canvas.restore();
    }

    private void drawSimpleBitmap(Canvas canvas) {
        canvas.drawBitmap(mBitmap, matrix, mPaint);
    }

    private void drawColorFilter(Canvas canvas) {
        mPaint.setColorFilter(null);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        ColorFilter filter = new LightingColorFilter(0x808080, 0x303030);
        mPaint.setColorFilter(filter);
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() + 50, 0, mPaint);

        float[] src = {
                -1, 0, 0, 0, 255,
                0, -1, 0, 0, 255,
                0, 0, -1, 0, 255,
                0, 0, 0, 1, 0
        };
        ColorMatrix cm = new ColorMatrix(src);
        filter = new ColorMatrixColorFilter(cm);
        mPaint.setColorFilter(filter);
        canvas.drawBitmap(mBitmap, 0, mBitmap.getHeight() + 50, mPaint);

        cm.setSaturation(0);
        filter = new ColorMatrixColorFilter(cm);
        mPaint.setColorFilter(filter);
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() + 50, mBitmap.getHeight() + 50, mPaint);
    }

    private void drawShader(Canvas canvas) {
        int[] colors = { Color.RED, Color.GREEN, Color.BLUE };
        float[] positions = { 0, 0.3f, 1 };

        Shader shader = new LinearGradient(200, 200, 400, 400, colors, positions, Shader.TileMode.MIRROR);
        mPaint.setShader(shader);
        canvas.drawRect(100, 100, 400, 400, mPaint);

        shader = new RadialGradient(200, 600, 100, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawCircle(200, 600, 100, mPaint);

        colors = new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.RED };
        shader = new SweepGradient(500, 600, colors, null);
        mPaint.setShader(shader);
        canvas.drawCircle(500, 600, 100, mPaint);
    }

    private void drawColor(Canvas canvas) {
        int color = Color.argb(0x80, 0xff, 0xea, 0x1b);
        mPaint.setColor(color);
        canvas.drawRect(100, 100, 300, 300, mPaint);

        mPaint.setAlpha(0x80);
        canvas.drawBitmap(mBitmap, 200, 200, mPaint);
    }

    private void drawPathDashPathEffect(Canvas canvas) {
        PathDashPathEffect effect = new PathDashPathEffect(arrowPath, 10, 0, PathDashPathEffect.Style.ROTATE);
        mPaint.setPathEffect(effect);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLUE);

        canvas.drawCircle(250, 250, 150, mPaint);
    }

    private void drawPathEffect(Canvas canvas) {
        float[] intervals = { 20, 10, 15, 5 };
        DashPathEffect effect = new DashPathEffect(intervals, 20);
        mPaint.setPathEffect(effect);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        canvas.drawCircle(250, 250, 150, mPaint);
    }

    private void drawBitmapMesh(Canvas canvas) {
        canvas.drawBitmapMesh(mBitmap, 4, 1, meshVertices, 0, null, 0, mPaint);
    }

    private void drawBitmap(Canvas canvas) {
        matrix.setTranslate(100, 100);
        matrix.postRotate(45, 100, 100);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        matrix.setScale(1, -1, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        matrix.postSkew(1, 0);
        matrix.postTranslate(0, mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, matrix, mPaint);
    }

    String text = "Hello Android!";
    float hOffset = 0;

    private void drawText(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
        //canvas.drawText(text, 0, 30, mPaint);
        canvas.drawTextOnPath(text, textPath, hOffset, 0, mPaint);
        hOffset += 5;
        invalidate();
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingLeft() + getPaddingRight() + mBitmap.getWidth();
        int height = getPaddingTop() + getPaddingBottom() + mBitmap.getHeight();

        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int x = 0, y = 0;

        x = getPaddingLeft() + ((right - left) - (getPaddingLeft() + getPaddingRight()) - mBitmap.getWidth()) / 2;
        y = getPaddingTop() + ((bottom - top) - (getPaddingTop() + getPaddingBottom()) - mBitmap.getHeight()) / 2;
        matrix.setTranslate(x, y);
    }

    float x = 0, y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN :
//                return true;
//            case MotionEvent.ACTION_UP :
//                x = event.getX();
//                y = event.getY();
//                invalidate();
//                return true;
//        }
//        return super.onTouchEvent(event);

        boolean consumed = mDetector.onTouchEvent(event);
        return consumed || super.onTouchEvent(event);
    }
}
