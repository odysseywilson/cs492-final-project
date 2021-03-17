package com.example.spillthetea;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {

    private ApiService service;

    private MutableLiveData<ArrayList<TeaItem>> mTea;

    public ApiRepository()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(TeaItem.class, new TeaItem.JsonDeserializer())
                .create();

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
                Log.e("STACK TRACE", t.getMessage());
                for(StackTraceElement s : t.getStackTrace())
                {
                    Log.e("", "\tat " + s.getClassName() + "." + s.getMethodName() + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
                }
            }
        });
    }

    public void getTea()
    {
        Call<ArrayList<TeaItem>> results = this.service.getAllTea();
        results.enqueue(new Callback<ArrayList<TeaItem>>() {

            @Override
            public void onResponse(Call<ArrayList<TeaItem>> call, Response<ArrayList<TeaItem>> response) {
                if(response.code() == 200)
                {
                    Log.d("GET REQUEST", "call success");
                    mTea.setValue(response.body());
                }
                else
                {
                    Log.d("GET REQUEST", "call failure");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("GET REQUEST", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
