package com.example.tacticalb.ClassforServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Интерфейс определеяющий методы для различных файлов и скриптов на сервере
public interface ApiInterface {
    //Скрипт php для получения List<Scenario> по условию "Qwery" из БД по JSON
    @GET("scenario.php")
    Call<List<Scenario>> getScenario(@Query("Qwery") String qwery);
    // Получение List ссылок картинок приложения по json
    @GET("HTTPPICTURE.json")
    Call<List<String>> getHttp();

//Скрипт для обновления информации карт в БД
    @GET("updatescenario.php")
   Call<String>  update(@Query("Qwery") String qwery);




}
