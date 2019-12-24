package com.example.tacticalb.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.R;

import java.util.List;

public class ScenarioAdapte extends RecyclerView.Adapter<ScenarioAdapte.RecyclerViewAdapte> {
    /*
    #Адаптер для показа Cardview карт(Scenario)
     */
    List<Scenario> scenarioList;
    Context context;
    OnItemList onItemList;

    public ScenarioAdapte(List<Scenario> scenarioList, Context context, OnItemList onItemList) {
        this.scenarioList = scenarioList;
        this.context = context;
        this.onItemList = onItemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapte onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.scenario_item,viewGroup,false);

        return new  RecyclerViewAdapte(view,onItemList);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerViewAdapte holder, int i) {
        Scenario scenario=scenarioList.get(i);
        holder.name.setText(scenario.getNAME()+"");
        holder.level.setText(scenario.getLEVEL()+"");
        holder.xp_sold.setText(scenario.getXP_SOLD()+"");
        holder.strange.setText(scenario.getSTRAGE()+"");
        holder.unit.setText((scenario.getDEVIL()+scenario.getGHOST()+scenario.getROBBER()+scenario.getWARRIOR()+scenario.getWIZARD()+scenario.getWOLF())+"");

        holder.about.setText(scenario.getDOWNLOADS()+" скачиваний\n"+scenario.getABOUT());
        //Назначения цвета CardView карты по различным уровням
switch (scenario.getLEVEL()){
    case 1:  holder.relativeLayout.setBackgroundColor(Color.argb(150,0, 108, 108));               break;
    case 2: holder.relativeLayout.setBackgroundColor(Color.argb(150,0, 172, 44));                   break;
    case 3:  holder.relativeLayout.setBackgroundColor(Color.argb(150,0, 215, 0));                   break;
    case 4:  holder.relativeLayout.setBackgroundColor(Color.argb(150,44, 172, 0));                   break;
    case 5:  holder.relativeLayout.setBackgroundColor(Color.argb(150,108, 108, 0));                   break;
    case 6:  holder.relativeLayout.setBackgroundColor(Color.argb(150,172, 44, 0));                   break;
    case 10:  holder.relativeLayout.setBackgroundColor(Color.argb(150,215, 0, 0));                   break;
    case 9:  holder.relativeLayout.setBackgroundColor(Color.argb(150,172,0,44));                   break;
    case 8:  holder.relativeLayout.setBackgroundColor(Color.argb(150,108,0,108));                   break;
    case 7:  holder.relativeLayout.setBackgroundColor(Color.argb(150,44,0,172));                   break;
}

    }

    @Override
    public int getItemCount() {
        return scenarioList.size();
    }

    class RecyclerViewAdapte extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,level,xp_sold,strange,about,unit;
        CardView cardView;
        OnItemList onItemList;
        RelativeLayout relativeLayout;
        RecyclerViewAdapte(@NonNull View itemView,OnItemList onItemList) {

            super(itemView);
            relativeLayout=itemView.findViewById(R.id.view);
            name= itemView.findViewById(R.id.name_sc);
            level=itemView.findViewById(R.id.text_level);
            xp_sold=itemView.findViewById(R.id.text_xp);
            strange=itemView.findViewById(R.id.strange);
            about=itemView.findViewById(R.id.about);
            unit=itemView.findViewById(R.id.unit);
            cardView=itemView.findViewById(R.id.card_scenario);
            this.onItemList=onItemList;
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemList.onItemClick(v,getAdapterPosition());
        }
    }
    public  interface OnItemList{
        void onItemClick(View view, int position);
    }
}
