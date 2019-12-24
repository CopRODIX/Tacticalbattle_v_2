package com.example.tacticalb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacticalb.Adapter.ScenarioAdapte;
import com.example.tacticalb.ClassforServer.MainPreseter;
import com.example.tacticalb.ClassforServer.Main_Base;
import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.dialog.Constructor_Fragment;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class Home_map extends Fragment implements Main_Base {
    /*
    #Фрагмент,в котором открываюся купленные пользователем карты
    #Открывает окна-конструкторы для настройки параметром сценариев игры
     */
    Save_inf save_inf;
    Save_inf save_inf_local;
    Hero hero;
    MainPreseter mainPreseter ;
    RecyclerView recyclerView;
    ScenarioAdapte scenarioAdapte;
    List<Scenario> kol;
    ScenarioAdapte.OnItemList onItemList;
    int pos=-1;
    private static final String TAG = "Home_map";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_map,container,false);
        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        save_inf=new Save_inf(getContext());
        save_inf_local=new Save_inf(getContext(),Save_inf.CHILD_FOR_USER_LOCAL);
        hero=save_inf.getHero( );
        /*Закачивание карт
        #Если версия онлайн то закачивание карт происходит с сервера
        #Если игра локальная,то закачивание карт идет с SharedPreference
                */
if (save_inf.getTypeGame()){
        mainPreseter = new MainPreseter(this,"SELECT * FROM `SCENARIO2` WHERE "+hero.my_scenario()+" or `USER`= '" +hero.getEmail()+"'");
        mainPreseter.getData();
}else {
    getAdap(save_inf.getListScenario(Save_inf.SCENARIO_LIST_HOME));
}


        return view;
    }

    @Override
    public void showLonging() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void ResultHome(List<Scenario> scenario) {

    }

    @Override
    public void ResultShop(List<Scenario> scenario) {

    }

    @Override
    public void onGetResult(List<Scenario> scenario) {
        getAdap(scenario);
    }



    @Override
    public void onError(String localizedMessage) {

    }
    public void getAdap(List<Scenario> scenarios){
        //Лямбда для вызова кода в случае нажатия (onItemList())
        onItemList=((view1, position) ->{
            Scenario scenario=kol.get(position);

            if (position==pos){
                if (save_inf.getHero( ).getXp()>scenario.getXP_SOLD()) {

                    if (scenario.getTYPE() != 2) {
                        //Для типа карты 2,то вызывается диалог для настройки праметров карт
                        Constructor_Fragment constructor_fragment = new Constructor_Fragment();
                        Bundle bundle = new Bundle();

                        bundle.putSerializable(Scenario.class.getSimpleName(), scenario);
                        constructor_fragment.setArguments(bundle);
                        constructor_fragment.show(getFragmentManager(), TAG);

                    } else {
                        //Перход в игру MAinActivity
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra(Save_inf.REMEMBER_BOOLEAN_FOR_ARGUMENTS, false);
                        intent.putExtra(Scenario.class.getSimpleName(), scenario);
                        startActivity(intent);
                    }

                            /*.setNegativeButton(getResources().getString(R.string.openonlineroom), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }*/
                }else
                    Toasty.error(getContext(),getResources().getString(R.string.notenoughxp), Toasty.LENGTH_LONG,true).show();

                pos=-1;
            }
            else
                pos=position;

        }
        );
        //Отдача adapterScenario списка карт
        scenarioAdapte=new ScenarioAdapte(scenarios ,getContext(), onItemList);
        scenarioAdapte.notifyDataSetChanged();
        recyclerView.setAdapter(scenarioAdapte);
        kol=scenarios;
    }
}
