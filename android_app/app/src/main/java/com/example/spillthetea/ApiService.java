package com.example.spillthetea;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @Multipart
    @POST("api/tea")
    Call<ResponseBody> postTea(
        @Query("username") String username,
        @Query("caption") String caption,
        @Part MultipartBody.Part image
    );
}
