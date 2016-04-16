package com.printapp.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_URL = "https://api.vk.com/";
    public static String ACCESS_TOKEN = "";
    public static final String API_VERSION = "5.50";
    public static final String USER_FIELDS= "photo_50";

    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
