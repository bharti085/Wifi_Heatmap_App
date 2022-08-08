package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.myapplication.MainActivity2;

import java.io.File;


public class HeatMapActivity extends AppCompatActivity {
    ImageView imageView3;
    Uri HeatmapUri;
//    MainActivity2 ob = new MainActivity2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
        imageView3 = (ImageView) findViewById(R.id.HeatmapImage);
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        HeatmapUri = Uri.parse(extras.getString("HEATMAP_IMAGEVIEW_BITMAP"));
//        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("HEATMAP_IMAGEVIEW_BITMAP");
        imageView3.setImageURI(HeatmapUri);
//        ob.file.delete();

    }
}