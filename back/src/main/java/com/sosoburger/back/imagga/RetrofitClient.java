package com.sosoburger.back.imagga;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {
    public static Retrofit getInstance() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                .addHeader(
                        "Authorization", Credentials.basic(
                                ImmagaCredentials.API_KEY,
                                ImmagaCredentials.API_SECRET
                        ))
                .build()
        ));
        OkHttpClient client = builder.build();
        return new Retrofit.
                Builder().
                baseUrl("https://api.imagga.com/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build();
    }
}

