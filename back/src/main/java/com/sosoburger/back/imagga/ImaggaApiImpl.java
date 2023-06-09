package com.sosoburger.back.imagga;

import okhttp3.MultipartBody;
import retrofit2.Call;

public class ImaggaApiImpl implements ImaggaApi{
    static ImaggaApi imaggaApi = RetrofitClient.getInstance().create(ImaggaApi.class);
    @Override
    public Call<UploadResponse> upload(MultipartBody.Part image) {
        return imaggaApi.upload(image);
    }
}
