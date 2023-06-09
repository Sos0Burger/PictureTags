package com.sosoburger.back.imagga;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImaggaApi {
    @Multipart
    @POST("tags?language=ru")
    Call<UploadResponse> upload(@Part MultipartBody.Part image);
}
