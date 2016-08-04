package com.tacademy.samplegraphics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScrollActivity extends AppCompatActivity {

    CustomScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        scrollView = (CustomScrollView)findViewById(R.id.view_scroll);
        Button btn = (Button)findViewById(R.id.btn_scroll_image_activity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.moveLeft();
            }
        });
    }
}
