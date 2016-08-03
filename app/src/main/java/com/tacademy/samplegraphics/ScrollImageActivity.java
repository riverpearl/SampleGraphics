package com.tacademy.samplegraphics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScrollImageActivity extends AppCompatActivity {

    ImageView iconView;
    TextView messageView;
    GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_image);

        iconView = (ImageView)findViewById(R.id.image_icon);
        messageView = (TextView)findViewById(R.id.text_message);

        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)messageView.getLayoutParams();
                lp.bottomMargin += distanceY;

                if (lp.bottomMargin > 0)
                    lp.bottomMargin = 0;

                if (lp.bottomMargin < -lp.height)
                    lp.bottomMargin = -lp.height;

                messageView.setLayoutParams(lp);
                Log.i("Scroll", "y : " + distanceY);
                return true;
            }
        });

        iconView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mDetector.onTouchEvent(motionEvent);
            }
        });
    }
}
