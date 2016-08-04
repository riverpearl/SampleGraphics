package com.tacademy.samplegraphics;

import android.content.ClipData;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DragAndDropActivity extends AppCompatActivity implements View.OnLongClickListener {

    ListView listView;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);

        findViewById(R.id.text_item1).setOnLongClickListener(this);
        findViewById(R.id.text_item2).setOnLongClickListener(this);
        findViewById(R.id.text_item3).setOnLongClickListener(this);

        listView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED :
                        View v = (View)dragEvent.getLocalState();
                        v.setBackgroundColor(Color.YELLOW);
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED :
                        listView.setBackgroundColor(Color.GREEN);
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED :
                        listView.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION :
                        break;
                    case DragEvent.ACTION_DROP :
                        float x = dragEvent.getX();
                        float y = dragEvent.getY();
                        ClipData clip = dragEvent.getClipData();
                        String text = clip.getItemAt(0).getText().toString();
                        int position = listView.pointToPosition((int)x, (int)y);

                        if (position != ListView.INVALID_POSITION)
                            mAdapter.insert(text, position);
                        else mAdapter.add(text);

                        return true;
                    case DragEvent.ACTION_DRAG_ENDED :
                        View old = (View)dragEvent.getLocalState();
                        old.setBackgroundColor(Color.TRANSPARENT);
                        listView.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        String text = ((TextView)view).getText().toString();
        ClipData clip = ClipData.newPlainText("label", text);
        view.startDrag(clip, new View.DragShadowBuilder(view), view, 0);
        return true;
    }
}
