package com.example.tacticalb.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.dialog.Shop_par;


public class Shop_map extends Fragment
{
/*
#Фрагмент активности Difficulty
##Магазин компонентов пользователя
 */
    TabLayout tabLayout;
    TabItem map;



private static String TAG="Shop_map";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.shop_map,container,false);
        tabLayout=view.findViewById(R.id.top);
        map=view.findViewById(R.id.map);
       // parm=view.findViewById(R.id.par);

        FragmentTransaction transaction= getChildFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layou,new Map_S()).commit();





        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //if (tab.getPosition()==0)
                    tot(new Map_S());
              //  else
                    //tot(new Parmа_S());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    void tot(Fragment childFr){
        Shop_par shop_par= new Shop_par();
        Bundle bundle = new Bundle();
Save_inf save_inf=new Save_inf(getContext());
        bundle.putSerializable(Save_inf.CONNECT_DIALOG_FOR_PARAM_TYPE,1);
        bundle.putSerializable(Hero.class.getSimpleName(), save_inf.getHero( ));
        shop_par.setArguments(bundle);
        shop_par.show(getFragmentManager(), TAG);
        FragmentTransaction transaction= getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layou,childFr).commit();
    }


}
