package org.sosoburger.api;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {
    public static Retrofit getInstance() {
        return new Retrofit.
                Builder().
                baseUrl("http://localhost:8080/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }
}
