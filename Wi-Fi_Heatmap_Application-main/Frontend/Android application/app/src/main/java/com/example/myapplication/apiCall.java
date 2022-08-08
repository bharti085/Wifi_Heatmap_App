package com.example.myapplication;



import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface apiCall {
    static String BASE_URL="https://wifiserver01.herokuapp.com/";
//    Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        return retrofit;
    }

    @Multipart
    @POST("generate-heatmap")

//    Call<ResponseBody> addRecord(@Part MultipartBody.Part avatar, @Part MultipartBody.Part parts);
    Call<ResponseBody> addRecord(@Part MultipartBody.Part avatar, @Part MultipartBody.Part parts,@Part("Height") RequestBody Height,@Part("Width") RequestBody Width);
//    @Part("base64image") RequestBody base64image
}
