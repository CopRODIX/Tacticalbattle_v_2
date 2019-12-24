package com.example.tacticalb.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.R;
import com.example.tacticalb.activity.MainActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import es.dmoral.toasty.Toasty;

public class Constructor_Fragment extends DialogFragment implements View.OnClickListener {
    //Класс-конструктор диолога для формирования параметров игры на карте(кол-во этапов,кол-во монстров )
    Scenario scenario;
Button Cancel,Next;
    DiscreteSeekBar Warrior_Bar,Wizard_Bar,Wolf_Bar,Devil_Bar,Robber_Bar,St_Bar;
    ImageView warror,wizard,wolf,devil,robber;
    LinearLayout linear1,linear2,linear3,linear4,linear5,top;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.consructor,null);
         scenario= (Scenario) getArguments().getSerializable(Scenario.class.getSimpleName());
         getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        //
        Robber_Bar=view.findViewById(R.id.Robber_bar);
        Cancel=view.findViewById(R.id.diss);
        Next=view.findViewById(R.id.next);
        Cancel.setOnClickListener(this);
        Next.setOnClickListener(this);
        Wizard_Bar=view.findViewById(R.id.Wizard_bar);
        Warrior_Bar=view.findViewById(R.id.Warrior_bar);
        Wolf_Bar=view.findViewById(R.id.Wolf_bar);
        Devil_Bar=view.findViewById(R.id.Devil_bar);
        St_Bar=view.findViewById(R.id.Sta_bar);
        warror=view.findViewById(R.id.warror);
        wizard=view.findViewById(R.id.wizard);
        wolf  =view.findViewById(R.id.wolf);
        devil =view.findViewById(R.id.demon);
        robber =view.findViewById(R.id.robber);
        linear1=view.findViewById(R.id.t1);
        linear2=view.findViewById(R.id.t2);
        linear3=view.findViewById(R.id.t3);
        linear4=view.findViewById(R.id.t4);
        linear5=view.findViewById(R.id.t5);
        top=view.findViewById(R.id.top);
        top.setBackgroundColor(getResources().getColor(R.color.main));
        if (scenario.getTYPE()==-1 || scenario.getTYPE()==-2)
        {
            Wolf_Bar.setVisibility(view.GONE);
            Warrior_Bar.setVisibility(view.GONE);
            Wizard_Bar.setVisibility(view.GONE);
            Devil_Bar.setVisibility(view.GONE);
           Robber_Bar.setVisibility(view.GONE);
            warror.setVisibility(view.GONE);
                    wizard.setVisibility(view.GONE);
            wolf.setVisibility(view.GONE);
                    devil.setVisibility(view.GONE);
            robber.setVisibility(view.GONE);
            linear1 .setVisibility(view.GONE);
                    linear2.setVisibility(view.GONE);
            linear3.setVisibility(view.GONE);
                    linear4.setVisibility(view.GONE);
            linear5.setVisibility(view.GONE);
        }
        else {
        Toasty.warning(getContext(),getResources().getString(R.string.more3), Toasty.LENGTH_LONG,true).show();
        Toasty.info(getContext(),getResources().getString(R.string.plus), Toasty.LENGTH_LONG,true).show();
        //Присвавивания макснемальных значений
        Robber_Bar.setMin(0);
        Robber_Bar.setMax(scenario.getROBBER());
        Warrior_Bar.setMin(0);
        Warrior_Bar.setMax(scenario.getWARRIOR());
        Wizard_Bar.setMin(0);
        Wizard_Bar.setMax(scenario.getWIZARD());
        Wolf_Bar.setMin(0);
        Wolf_Bar.setMax(scenario.getWOLF());
        Devil_Bar.setMin(0);
        Devil_Bar.setMax(scenario.getDEVIL());
       }
        //
        St_Bar.setMax(scenario.getSTRAGE());
        St_Bar.setMin(1);
return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diss:dismiss();break;
            case R.id.next:
                if (Wolf_Bar.getProgress()+Wizard_Bar.getProgress()+Wolf_Bar.getProgress()+Devil_Bar.getProgress()>2 || scenario.getTYPE()==-2 || scenario.getTYPE()==-1){
                    if (scenario.getTYPE()!=-1 && scenario.getTYPE()!=-2){

                scenario.setWARRIOR(Warrior_Bar.getProgress());
                scenario.setWIZARD(Wizard_Bar.getProgress());
                scenario.setWOLF(Wolf_Bar.getProgress());
                scenario.setDEVIL(Devil_Bar.getProgress());

                scenario.setROBBER(Robber_Bar.getProgress());}
                scenario.setSTRAGE(St_Bar.getProgress());
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("rememb",false);
                    intent.putExtra(Scenario.class.getSimpleName(), scenario);
                    startActivity(intent);
                    dismiss();
                }
                else {
                    Toasty.error(getContext(),getResources().getString(R.string.few), Toasty.LENGTH_SHORT,true).show();
                }







                break;
        }

    }
}
