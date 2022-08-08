package com.example.myapplication;

import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import com.example.myapplication.MainActivity2;
import androidx.annotation.RequiresApi;
public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String DEBUG_TAG = "Gestures";
    float x;
    float y;
    int x1;
    int x2;
    int i=1;
    int SLNO = 0;
    int rssi;
//    MainActivity2 ob = new MainActivity2();
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onLongPress(MotionEvent event) {
//        Log.d(DEBUG_TAG,"onDown: " + event.toString());
//
//        x = event.getX();
//        y = event.getY();
//        ob.disp();
//        ob.savetofile();
//        ob.checkEnabled();
//        i++;
//        SLNO++;
////        Toast.makeText("X: " + x + " Y: "+ y +"  RSSI:"+ rssi, Toast.LENGTH_SHORT).show();
//
//
//    }

}

