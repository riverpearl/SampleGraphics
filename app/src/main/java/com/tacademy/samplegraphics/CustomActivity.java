package com.tacademy.samplegraphics;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomActivity extends AppCompatActivity {

    CustomGraphicsView cgv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        cgv = (CustomGraphicsView)findViewById(R.id.custom_view);
        Button btn = (Button)findViewById(R.id.btn_change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bm = ((BitmapDrawable)ContextCompat.getDrawable(CustomActivity.this, R.drawable.sample_1)).getBitmap();
                cgv.changeBitmap(bm);
            }
        });
    }
}
