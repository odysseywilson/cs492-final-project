package com.example.spillthetea;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {

    private ApiService service;

    public ApiRepository()
    {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://192.168.1.100:3000").
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        this.service = retrofit.create(ApiService.class);
    }

    public void postImage(String username, String caption, MultipartBody.Part image)
    {
        Call<ResponseBody> results = this.service.postTea(username, caption, image);
        results.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("POST TEA", "call success");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                for(StackTraceElement s : t.getStackTrace())
                {
                    Log.e("STACK TRACE", s.getLineNumber() + " | " + s.getClassName() + " | " + s.getMethodName());
                }
                Log.e("POST TEA", t.getMessage());
            }
        });
    }

}
