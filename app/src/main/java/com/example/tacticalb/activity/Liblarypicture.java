package com.example.tacticalb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tacticalb.Adapter.AdapterPicture;
import com.example.tacticalb.ClassforServer.ApiInterface;
import com.example.tacticalb.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Liblarypicture extends Activity {
    /*
    #Активность,позволяющзая выбрать картинку для аватарки пользователя из загруженных на сервер приложения иконок(серверная библиотека приложения)
    #Приложение сначала с помощью библиотеки Retrofit закачивает json файл где прописан массив имеющихся ссылок(http://cj76241.tmweb.ru/HTTPPICTURE.json),а потом скачивает ,помещая их в RecyclView
     */
      RecyclerView recyclerView;
      GridLayoutManager layoutManager;
      AdapterPicture adapterPicture;
      AdapterPicture.OnItemList onItemList;
    List<String> http;
      int pos=-1;
    final static String url="http://cj76241.tmweb.ru/";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liblary_activity);
        recyclerView=findViewById(R.id.recycle_view);
        layoutManager=new GridLayoutManager(    this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        getHTTPPICTURE();
        onItemList=((view1, position) ->{
            if (position==pos){
                Intent intent=new Intent();
                intent.putExtra(Selectfoto.backfromlibrary,http.get(position));
                setResult(RESULT_OK,    intent);
                finish();
            }else
            {
            pos=position;
                Toasty.warning(getApplicationContext(), getResources().getString(R.string.more1), Toasty.LENGTH_LONG,true).show();

            }
        });

    }
    public void getHTTPPICTURE(){
 http=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<List<String>> call=apiInterface.getHttp();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
          List<String> strings=response.body();
                for (String s:strings
                     ) {
                    http.add(s);
                };
                adapterPicture=new AdapterPicture(http,getApplicationContext(),onItemList);
                recyclerView.setAdapter(adapterPicture);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });



    }

}
