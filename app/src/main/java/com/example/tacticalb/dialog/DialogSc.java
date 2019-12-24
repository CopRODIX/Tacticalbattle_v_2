package com.example.tacticalb.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.tacticalb.R;
import com.example.tacticalb.activity.Directory;

public class DialogSc extends DialogFragment {
    TabLayout tabLayout;
    FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sc,container,false);
        getDialog().setTitle("Помощь");
        WindowManager.LayoutParams a = getDialog().getWindow().getAttributes();
        a.dimAmount = 0;
       getDialog()      .getWindow().setAttributes(a);
          transaction= getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.FL,new FrCon()).commit();
tabLayout=view.findViewById(R.id.top);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction tran = getChildFragmentManager().beginTransaction();

                 if (tab.getPosition()==0)
                     tran .replace(R.id.FL,new FrCon()) ;
                  else
                     tran .replace(R.id.FL,new Directory()) ;
               tran .addToBackStack(null);
                tran .commit();

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
}
