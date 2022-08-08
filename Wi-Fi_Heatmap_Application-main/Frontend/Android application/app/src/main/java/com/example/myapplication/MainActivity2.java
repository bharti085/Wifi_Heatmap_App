package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
//import static com.github.karthyks.runtimepermissions.PermissionActivity.REQUEST_PERMISSION_CODE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.util.Base64;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;


//import com.github.karthyks.runtimepermissions.Permission;
//import com.github.karthyks.runtimepermissions.googleapi.LocationSettingsHelper;
//import com.google.android.gms.location.LocationRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity2 extends AppCompatActivity {
    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private Button permissions;
    MyGestureListener ob = new MyGestureListener();
    ImageView imageView2;
    Uri myUri;
    Uri uri2;
    Button buttonM;
    Button bt;
    Button buttonCal;
    List<ScanResult> results;
    int i = 1;
    //    Button bt;
    Handler handler;
    double operating_band;
    int rssi;
    String ssid = new String();
    String bssid = new String();
    int frequency;
    int Linkspeed;
    int ChannelNumber;
    int RxLinkSpeed;
    int TxLinkSpeed;
    int count;
    int Bandwidth;
    int SLNO = 0;
    int mynum;
    // Context context = this;
    String currentSSID = new String();
    String previousSSID = new String();
    File file;
    float x;
    float y;
    int x1;
    int y1;
    ContentResolver resolver;
    Context incontext;
    String base64;
    private ProgressDialog progress;
    Uri HeatmapURI;
    boolean flag=true;
    private long then;
    private int longClickDuration = 5000;
    int height,width;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("EXTRA_IMAGEVIEW_URL"));
        System.out.println("1");
        System.out.println("1");
        imageView2.setImageURI(myUri);
        ViewTreeObserver vto = imageView2.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView2.getViewTreeObserver().removeOnPreDrawListener(this);
                height = imageView2.getMeasuredHeight();
                width = imageView2.getMeasuredWidth();
                Log.d("Width: ", String.valueOf(imageView2.getMeasuredWidth()));
                Log.d(" Height: ", String.valueOf(imageView2.getMeasuredHeight()));
                return true;
            }
        });

        String filePath = getPath(myUri);
        Log.i("url path", String.valueOf(filePath));

        System.out.println("1");
        System.out.println("1");
        Log.i("url", String.valueOf(myUri));
        resolver = getContentResolver();
        incontext = getApplicationContext();

        buttonCal = (Button) findViewById(R.id.buttonheatmap);
        progress = new ProgressDialog(MainActivity2.this);
        buttonCal.setEnabled(false);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());


        imageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//
//            then = (long) System.currentTimeMillis();
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//        if ((System.currentTimeMillis() - then) > longClickDuration) {
//            /* Implement long click behavior here */
//            x = event.getX();
//            y = event.getY();
//            x1=(int)x;
//            y1=(int)y;
//            disp();
//
//            savetofile();
//            checkEnabled();
//            i++;
//            SLNO++;
//            Toast.makeText(MainActivity2.this, "X: " + x1 + " Y: " + y1 + "  RSSI:" + rssi, Toast.LENGTH_SHORT).show();
//            System.out.println("1");
//
//            System.out.println("Long Click has happened!");
//            return false;
//        }
//    }
//    return true;
//}
        });
//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        buttonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addRecord(filePath);

            }
        });
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
//        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        scaleGestureDetector.onTouchEvent(motionEvent);
//        return true;
//    }
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
//            imageView2.setScaleX(mScaleFactor);
//            imageView2.setScaleY(mScaleFactor);
//            return true;
//        }
//    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onLongPress(MotionEvent event) {

            x = event.getX();
            y = event.getY();
            x1=(int)x;
            y1= height - (int)y;
            disp();

            savetofile();
            checkEnabled();
            i++;
            SLNO++;
            Toast.makeText(MainActivity2.this, "X: " + x1 + " Y: " + y1 + "  RSSI:" + rssi, Toast.LENGTH_SHORT).show();
            System.out.println("1");

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    public void disp() {
        //  try {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        rssi = wifiManager.getConnectionInfo().getRssi();
        bssid = wifiManager.getConnectionInfo().getBSSID();
        frequency = wifiManager.getConnectionInfo().getFrequency();

        Linkspeed = wifiManager.getConnectionInfo().getLinkSpeed();
//        RxLinkSpeed = wifiManager.getConnectionInfo().getRxLinkSpeedMbps();
//        TxLinkSpeed = wifiManager.getConnectionInfo().getTxLinkSpeedMbps();

        try {


            results = wifiManager.getScanResults();
            List<ScanResult> results = wifiManager.getScanResults();

            for (ScanResult result : results) {
                if (bssid.equals(result.BSSID)) {
                    if (result.channelWidth == 0) {
                        Bandwidth = 20;
                    } else if (result.channelWidth == 1) {
                        Bandwidth = 40;
                    } else if (result.channelWidth == 2) {
                        Bandwidth = 80;
                    } else if (result.channelWidth == 3) {
                        Bandwidth = 160;
                    } else if (result.channelWidth == 4) {
                        Bandwidth = 160;
                    } else {
                        Bandwidth = 320;
                    }

                    if (frequency >= 2412 && frequency < 2484) {
                        ChannelNumber = ((frequency - 2412) / 5 + 1);
                    } else if (frequency >= 5170 && frequency <= 5825) {
                        ChannelNumber = ((frequency - 5170) / 5 + 34);
                    } else if (frequency == 2484) {
                        ChannelNumber = 14;
                    }
                }
            }
            ssid = wifiInfo.getSSID();


            Log.d("Bharti", "currentSSID" + ssid);
            currentSSID = ssid;
            Log.d("Bharti", "prevSSID" + currentSSID);
            // to obtain the previous AP information
            if (previousSSID.equals(currentSSID)) {
//                st10.setText("\n\t\t" + currentSSID + "=" + SLNO);
                count = SLNO;
            } else if (currentSSID.equals("<unknown ssid>")) {
//                st10.setText("\n\t\t" + previousSSID + "=" + (SLNO));

            } else {
                if (count != 0) {
//                    st11.append("\t" + previousSSID + "=" + count);
                    SLNO = 1;
                }
            }
            if (!currentSSID.equals("<unknown ssid>")) {
                previousSSID = currentSSID;
            }


            if (frequency > 2400 && frequency < 3000)
                operating_band = 2.4;
            else
                operating_band = 5.0;

            // Display on the screen
            Log.d("Number of times=", String.valueOf(SLNO));
            Log.d("\t\tSignal Strength of ", ssid);
            Log.d("\t\tRSSI (dbm) = ", String.valueOf(rssi));
            Log.d("\t\tFrequency=", String.valueOf(frequency));
            Log.d("\t\tLinkspeed=", String.valueOf(Linkspeed));
            Log.d("\t\tRxLinkSpeed=", String.valueOf(RxLinkSpeed));
            Log.d("\t\tTxLinkSpeed=", String.valueOf(TxLinkSpeed));
            Log.d("\t\tOperating band=", String.valueOf(operating_band));
            Log.d("\t\tX=", String.valueOf(x1));
            Log.d("\t\tY=", String.valueOf(y1));

//            Toast.makeText(MainActivity2.this,  "RSSI:"+ rssi + "  SSID:" + ssid, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            Toast.makeText(MainActivity2.this, "Device is not connected to any network", Toast.LENGTH_LONG).show();

        }
    }



    public void savetofile() {
        Log.v("Bharti", "entering save file");
        File directory = null;

        directory = new File(Environment.getExternalStorageDirectory() + java.io.File.separator + "WSS");
        directory.mkdirs();
        Log.v("Bharti", "make directory file");

        file = new File(Environment.getExternalStorageDirectory() + java.io.File.separator + "WSS" + java.io.File.separator + "WSS.txt");
        System.out.println(file);


//        Date currentTime = Calendar.getInstance().getTime();
        Log.v("Bharti", "flag");

        if(flag==true)
        {   Log.v("Bharti", String.valueOf(file));
            file.delete();
            flag=false;
        }
        if (!file.exists()) {
            try {   Log.v("Bharti", String.valueOf(file));
                file.createNewFile();
            } catch (Exception e) {
                Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }




        System.out.println("1");
        uri2 = Uri.parse(String.valueOf(file));
        System.out.println(uri2);

        try {
            System.out.println("2");
            OutputStreamWriter file_writer = new OutputStreamWriter(new FileOutputStream(file, true));
            System.out.println("3");
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            System.out.println("4");
            if (SLNO == 0) {
                buffered_writer.write("\nNumber of times" + "\tSSID" + "\tRSSI" + "\tX" + "\tY" + "\tFrequency" + "\tLinkSpeed" + "\tRxLinkSpeed" + "\tTxLinkSpeed" + "\toperating_band");
                System.out.println("5");
                buffered_writer.write("\n" + SLNO + "\t" + ssid + "\t" + rssi + "\t" + x1 + "\t" + y1 + "\t" + frequency + "\t" + Linkspeed + "\t" + RxLinkSpeed + "\t" + TxLinkSpeed + "\t" + operating_band);
                System.out.println("6");
            } else {
                buffered_writer.write("\n" + SLNO + "\t" + ssid + "\t" + rssi + "\t" + x1 + "\t" + y1 + "\t" + frequency + "\t" + Linkspeed + "\t" + RxLinkSpeed + "\t" + TxLinkSpeed + "\t" + operating_band);
                System.out.println("6");
            }
            System.out.println("7");
            buffered_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkEnabled() {
        if (i > 3) {
            buttonCal.setEnabled(true);
        }
    }

    //    private void addTouchListener() {
    private void addRecord(String filePath) {
        Log.d("bharti","8");
//            Log.d("bharti", String.valueOf(file));
        File txtFile = new File(String.valueOf(file));
        File fileImage = new File(filePath);Log.d("image path",filePath);
      Log.d("image path", String.valueOf(fileImage));

//            Log.d("bharti", String.valueOf(files));
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), txtFile);
//            Log.d("bharti", String.valueOf(requestBody));
//            Log.d("bharti","9");
        MultipartBody.Part avatar = MultipartBody.Part.createFormData("txtFile", txtFile.getName(), requestBody);
//            Log.d("bharti", String.valueOf(avatar));
//            Log.d("bharti","10");

        RequestBody requestbody = RequestBody.create(MediaType.parse("image/*"), fileImage);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("base64image", fileImage.getName(), requestbody);
//            base64.replaceAll("[\\n\t ]","");
//            Log.d("bharti", base64);
        RequestBody Height = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(height));
        RequestBody Width = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(width));
//                RequestBody base64image = RequestBody.create(MediaType.parse("text/plain"), base64);
//            Log.d("bharti", String.valueOf(base64image));
//            Log.d("bharti","11");
        apiCall getResponse = apiCall.getRetrofit().create(apiCall.class);
//        Call<ResponseBody> call = getResponse.addRecord(avatar,parts);
        Call<ResponseBody> call = getResponse.addRecord(avatar,parts,Height,Width);
//            Log.d("bharti","13");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Log.d("Response", "=" + response.code());
//                        Log.d("Response", "= " + response.message());
                Log.d("Response", "= " + response);
                Log.d("Response", "= " + response.body());
                Log.d("Response", "= " + response.getClass());
//                        Log.d("Response", "= " + response.raw());
                if(response.code()==200)
                {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        HeatmapURI=getImageUri(incontext, bmp);
                        Intent intent = new Intent(MainActivity2.this,HeatMapActivity.class);
                        intent.putExtra("HEATMAP_IMAGEVIEW_BITMAP", HeatmapURI.toString());
                        startActivity(intent);
////                                imageView2.setImageBitmap(bmp);
//                                imageView2.setImageURI(HeatmapURI);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("failure", "message = " + t.getMessage());
                Log.d("failure", "cause = " + t.getCause());
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bmp, "Title", null);
        return Uri.parse(path);
    }

//    public static class ImageResizer {
//
//        /**
//         * Resizes an image to a absolute width and height (the image may not be
//         * proportional)
//         * @param inputImagePath Path of the original image
//         * @param outputImagePath Path to save the resized image
//         * @param scaledWidth absolute width in pixels
//         * @param scaledHeight absolute height in pixels
//         * @throws IOException
//         */
//        public static void resize(String inputImagePath,
//                                  String outputImagePath, int scaledWidth, int scaledHeight)
//                throws IOException {
//            // reads input image
//            File inputFile = new File(inputImagePath);
//
//            BufferedImage inputImage = ImageIO.read(inputFile);
//
//            // creates output image
//            BufferedImage outputImage = new BufferedImage(scaledWidth,
//                    scaledHeight, inputImage.getType());
//
//            // scales the input image to the output image
//            Graphics2D g2d = outputImage.createGraphics();
//            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
//            g2d.dispose();
//
//            // extracts extension of output file
//            String formatName = outputImagePath.substring(outputImagePath
//                    .lastIndexOf(".") + 1);
//
//            // writes to output file
//            ImageIO.write(outputImage, formatName, new File(outputImagePath));
//        }
//    }
//        });
//    }
//}


//
//    ;

//    @SuppressLint("ClickableViewAccessibility")
//    private void addTouchListener() {
//
//        imageView2.setOnTouchListener(new View.OnTouchListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//
//                ob.onLongPress(event);
//                return false;
//            }
//        });
//    }
//}


//        imageView2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
////                final int waitTime = 5000;
//                x = event.getX();
//                y = event.getY();
////                drawCross(x,y);
////                m_canvas.drawCircle(x,y,10,paint);
////                imageView2.setBitmap(bmp);
//                String msg = String.format("Coordinates : (%.2f,%.2f)", x, y);
//                Log.d("coordinates", msg);
//                Toast.makeText(MainActivity2.this, "X: " + x + " Y: " + y, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            });
       }




