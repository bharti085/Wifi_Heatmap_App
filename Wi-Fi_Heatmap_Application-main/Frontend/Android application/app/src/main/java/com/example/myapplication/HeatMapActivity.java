package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import java.io.File;


public class HeatMapActivity extends AppCompatActivity {
    ImageView imageView3;
    Uri HeatmapUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
        Zoom zoom = new Zoom();
        imageView3 = (ImageView) findViewById(R.id.HeatmapImage);
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        HeatmapUri = Uri.parse(extras.getString("HEATMAP_IMAGEVIEW_BITMAP"));
        imageView3.setImageURI(HeatmapUri);
        imageView3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                zoom.onTouch(v, event);    // allows th euser to zoom the heatmap generated
                return true;
            }

        });

    }
}
