package com.example.tacticalb.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tacticalb.R;

import java.util.HashMap;

public class Dialog_about extends DialogFragment {
    //Класс-конструктор диолога для показа информации о монстрах(их здоровье и описание и тп)и типах клетках поля
    ProgressBar health_bar,damage_bar;
    Button Baked;
    TextView title,health,damage,about;
    HashMap<String,String> about_map = new HashMap<>();
    LinearLayout q,w,e,r;

void GenerateInfo(){
    about_map.put("Warrior",getResources().getString(R.string.warriorab));
    about_map.put("Wizard",getResources().getString(R.string.wizardab));
    about_map.put("Wolf",getResources().getString(R.string.wolfab));
    about_map.put("Robber",getResources().getString(R.string.robberab));
    about_map.put("Tunnel",getResources().getString(R.string.tonnelab));
    about_map.put("Hut",getResources().getString(R.string.hutab));
    about_map.put("Forge",getResources().getString(R.string.forgeab));
    about_map.put("Castle",getResources().getString(R.string.castleab));
    about_map.put("Plain",getResources().getString(R.string.plainab));
    about_map.put("Hill",getResources().getString(R.string.hillab));
    about_map.put("Sands",getResources().getString(R.string.desertab));
    about_map.put("Tundra",getResources().getString(R.string.tundraab));
    about_map.put("Water",getResources().getString(R.string.waterab));
    about_map.put("Devil",getResources().getString(R.string.devilab));


}
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        GenerateInfo();
        String tir=getArguments().getString("type");
        int [] info=getArguments().getIntArray("info");

        View view = inflater.inflate(R.layout.about_unit,null);
        getDialog().setTitle(tir.toString());
q=view.findViewById(R.id.st);
w=view.findViewById(R.id.dt_k);
title=view.findViewById(R.id.type);
title.setVisibility(View.GONE);

        health_bar=view.findViewById(R.id.progress_health);
        damage_bar=view.findViewById(R.id.progress_damage);
        Baked=view.findViewById(R.id.back);
        health=view.findViewById(R.id.textView);
        damage=view.findViewById(R.id.textView1);
        about=view.findViewById(R.id.text_about);
        about.setText(about_map.get(tir));
       // title.setText(tir.toString());
        if (info[0]!=-1){
        health_bar.setMax(info[2]);
        health_bar.setProgress(info[0]);
        health.setText(""+info[0]);
        damage_bar.setMax(info[3]);
        damage_bar.setProgress(info[1]);

        damage.setText(""+info[1]);}
        else {

            q.setVisibility(View.GONE);
            w.setVisibility(View.GONE);
        }

        Baked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });









        return view;

    }
}

