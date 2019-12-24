package com.example.tacticalb.ClassforServer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //Класс для создания подключения с сервером по Retrofit
    public static  final String BASE_URL="http://cj76241.tmweb.ru/";

    private static Retrofit retrofit;
    public static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
