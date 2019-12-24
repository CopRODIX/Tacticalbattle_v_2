package com.example.tacticalb.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacticalb.Adapter.ScenarioAdapte;
import com.example.tacticalb.ClassforServer.ApiClient;
import com.example.tacticalb.ClassforServer.ApiInterface;
import com.example.tacticalb.ClassforServer.MainPreseter;
import com.example.tacticalb.ClassforServer.Main_Base;
import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Map_S extends Fragment implements Main_Base {
    /*
    #Фрагмент,дающий возможность покупки(преобретения) сценариев(карт) для этого нужно кликнуть два раза на item
    #Программа посылает SQL запрос на php докумет ,который получает все сценарии из бд на сервере ,формирует json Файл и отправляет его в приложение ,который c помощью Retrofit формирует List из сценариев(Scenario)
    #Затем списки сортируются с помощью Comparator<Scenario> и передаются ScenarioAdapte
    #При покупке карты:
    ##Название карты записывается к праметру mymap пользователя Firebase Database
    ##Отправляет update запрос в updatescenario.php для увелечение кол-ва скачиваний карты в БД
     */
public interface Changeparam{
    public void param();
    }
    ScenarioAdapte scenarioAdapte;
    ScenarioAdapte.OnItemList onItemList;
    RecyclerView recyclerView;
    List<Scenario> kol;
    Hero hero;
    Save_inf save_inf;
    Save_inf save_inf_local;
    MainPreseter mainPreseter;
    int pos=-1;
    Changeparam changeparam;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            changeparam = (Changeparam) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.map_s,container,false);


        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        save_inf=new Save_inf(getContext());
        hero=save_inf.getHero( );
        save_inf_local=new Save_inf(getContext(),Save_inf.CHILD_FOR_USER_LOCAL);
        if (save_inf.getTypeGame()){

        mainPreseter = new MainPreseter(this,"SELECT * FROM `SCENARIO2` WHERE `USER`='admin@admin.ru' and `LEVEL`<='"+hero.getLevel()+"' and not("+hero.my_scenario()+")");
        mainPreseter.getData();
         }
         else
             getAdap(save_inf.getListScenario(Save_inf.SCENARIO_LIST_SHOP));

        return view;
    }

    private void getAdap(List<Scenario> scenarioList) {
        onItemList=((view1, position) ->{
            Scenario scenario=kol.get(position);
            if (position==pos){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),R.style.AlertDialogCustom);
                dialog.setTitle(getResources().getString(R.string.buymap)+" "+scenario.getNAME()+" ?").setMessage(getResources().getString(R.string.formap)+" "+scenario.getLEVEL()+" "+getResources().getString(R.string.levelspend)+" "+scenario.getXP_SOLD()+"Xp"+"!\n "+
                        getResources().getString(R.string.mapcontains)+" "+scenario.getSTRAGE()+" "+getResources().getString(R.string.ofstage)).setPositiveButton(getResources().getString(R.string.buy),new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buy(scenario);
                    }
                }).setNegativeButton(getResources().getString(R.string.backcatalog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();



                pos=-1;
            }
            else
                pos=position;

        }
        );
        Collections.sort(scenarioList, new Comparator<Scenario>() {
            @Override
            public int compare(Scenario o1, Scenario o2) {
                int comp=0;
                if (o1.getLEVEL()<o2.getLEVEL())
                    comp=1;
                else if (o1.getLEVEL()>o2.getLEVEL()) comp=-1;
                if (comp==0){
                    if (o1.getDOWNLOADS()<o2.getDOWNLOADS())
                        comp=1;
                    else comp=-1;
                }
                return comp;
            }
        });
        scenarioAdapte=new ScenarioAdapte(scenarioList ,getContext(), onItemList);
        scenarioAdapte.notifyDataSetChanged();
        recyclerView.setAdapter(scenarioAdapte);


        kol=scenarioList;
    }

    private void buy(Scenario scenario) {
        hero=save_inf.getHero();
        if (hero.getXp()-scenario.getXP_SOLD()>=0){

            if (save_inf.getTypeGame()){
            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("USER").child(firebaseUser.getUid()).child("mymap");
            databaseReference.setValue(hero.getMy_scenario()+scenario.getNAME()+"~").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toasty.success(getContext(),getResources().getString(R.string.congratulatemap), Toasty.LENGTH_LONG,true).show();
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("USER").child(firebaseUser.getUid()).child("xp");
                    databaseReference.setValue(String.valueOf(save_inf.getHero( ).getXp()-scenario.getXP_SOLD()));

                    }
                    else
                        Toasty.error(getContext(),getResources().getString(R.string.erroebuymap), Toasty.LENGTH_LONG,true).show();
                }
            });
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(ApiClient.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            ApiInterface apiInterface=retrofit.create(ApiInterface.class);
            //apiInterface.update("UPDATE `SCENARIO` SET `DOWNLOADS`=`"+(scenario.getDOWNLOADS()+1)+"` WHERE `_ID`=`"+scenario.get_ID()+"`");
            Call<String> call=apiInterface.update("UPDATE `SCENARIO2` SET `DOWNLOADS`= ' "+String.valueOf(scenario.getDOWNLOADS()+1).toString()+" ' WHERE `_ID`=' "+scenario.get_ID()+" '");
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            })




            ;} else{
                save_inf.addScenariotoList(scenario);
                save_inf.updateParHero(Hero.Type_mymap,  hero.getMy_scenario()+scenario.getNAME()+"~");
                save_inf.updateParHero(Hero.Type_xp,save_inf.getHero( ).getXp()-scenario.getXP_SOLD());
                Toasty.success(getContext(),getResources().getString(R.string.congratulatemap), Toasty.LENGTH_LONG,true).show();
            changeparam.param();
            }
            kol.remove(scenario);
save_inf.putListScenario(Save_inf.SCENARIO_LIST_SHOP,kol);



            onGetResult(kol);
        } else
            Toasty.error(getContext(),getResources().getString(R.string.notenoughmoney), Toasty.LENGTH_LONG,true).show();
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
}
