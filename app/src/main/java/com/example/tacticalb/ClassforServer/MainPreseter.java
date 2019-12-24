package com.example.tacticalb.ClassforServer;
import com.example.tacticalb.Service.Save_inf;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPreseter {
    //Класс получения ответа от сервера(скрипта БД)получающий в ответ список карт(List<Scenario>)
       private Main_Base view;
private String qwery;

    public MainPreseter(Main_Base view, String qwery) {
        this.view = view;
        this.qwery = qwery;
    }
//Закачивание карт по Retrofit
    public void getData( ){
        view.showLonging();
        ApiInterface apiInteface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Scenario>> call =apiInteface.getScenario(qwery);
        call.enqueue(new Callback<List<Scenario>>() {
            @Override
            public void onResponse(Call<List<Scenario>> call, Response<List<Scenario>> response) {
                view.hideLoading();

                if (response.isSuccessful() && response.body() != null){

                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Scenario>> call, Throwable t) {
                view.hideLoading();
                view.onError(t.getLocalizedMessage());

            }
        });
    }
    //Закачивание карт  Retrofit по ключу type
    public void getDB(String type){

        ApiInterface apiInteface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Scenario>> call =apiInteface.getScenario(qwery);
        call.enqueue(new Callback<List<Scenario>>() {
            @Override
            public void onResponse(Call<List<Scenario>> call, Response<List<Scenario>> response) {

                if (response.isSuccessful() && response.body() != null){
if (type.equals(Save_inf.SCENARIO_LIST_HOME))
                  view.ResultHome(response.body());
else if (type.equals(Save_inf.SCENARIO_LIST_SHOP))
    view.ResultShop(response.body());


                }
            }

            @Override
            public void onFailure(Call<List<Scenario>> call, Throwable t) {


            }
        });

    }
}
