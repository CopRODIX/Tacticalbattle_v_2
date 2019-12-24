package com.example.tacticalb.Adapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tacticalb.R;
import com.example.tacticalb.class_unit.Skill;

import java.util.List;

public class SkillAdapter extends ArrayAdapter<Skill> {
    private LayoutInflater inflater;
    private int layout;
    private List<Skill> skills;
private int type;
/*
#Адаптер для показа списков навыков (Skill)
 */
    public SkillAdapter(Context context, int resource, List<Skill> skills, int type) {
        super(context, resource, skills);
        this.skills = skills;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.type=type;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);


        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView levelView = (TextView) view.findViewById(R.id.text_level);
        TextView XpView = (TextView) view.findViewById(R.id.text_xp);
        switch (type){
            case 0:   view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.armor_3));break;
            case 1:   view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.damage_3));break;
            case 2:   view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.health_3));break;

        }

        Skill skill = skills.get(position);


        nameView.setText(skill.getType()+" +"+skill.getIzm());
        levelView.setText(Integer.toString(skill.getLevel_skill()));
        XpView.setText(Integer.toString(skill.getXp_skill()));


        return view;
    }
}
