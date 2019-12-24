package com.example.tacticalb.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacticalb.Adapter.SkillAdapter;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.class_unit.Skill;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Shop_par  extends DialogFragment implements AdapterView.OnItemClickListener {

    //Класс-конструктор диолога для прокачивание параметров на сервере и на устройстве
   //
    TextView warxp;
    ListView armor_view, damage_view, health_view;
    private List<Skill> armor_list = new ArrayList<Skill>();
    private List<Skill> damage_list = new ArrayList<Skill>();
    private List<Skill> health_list = new ArrayList<Skill>();
    private SkillAdapter adapter_armor, adapter_damage, adapter_health;
    Save_inf save_inf;
    AdapterView<?> parentp;
    int pos;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    boolean post= true;

    private int type;
    Hero hero_param;
    //
    Dialog_Fr dialog_fr;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dialog_fr=( Dialog_Fr) context;

    }
    public interface Dialog_Fr {
        void canal(Hero hero);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.paramtr,null);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

 save_inf = new Save_inf(getContext());

//Объявление progress_bar и их первоначальных значений
        if (save_inf.getTypeGame()){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("USER").child(firebaseUser.getUid());}
//        Hero hero= (Hero) getArguments().getSerializable(Hero.class.getSimpleName());
        type=getArguments().getInt(Save_inf.CONNECT_DIALOG_FOR_PARAM_TYPE);
warxp=view.findViewById(R.id.warxp);
Button j=view.findViewById(R.id.back);
j.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            dialog_fr.canal(hero_param);
        dismiss();
    }
});
        armor_view = (ListView) view.findViewById(R.id.arm);
        damage_view = view.findViewById(R.id.damm);
        health_view = view.findViewById(R.id.heltt); adapter_armor = new SkillAdapter(getContext(), R.layout.item, armor_list, 0);
        adapter_damage = new SkillAdapter(getContext(), R.layout.item, damage_list, 1);
        adapter_health = new SkillAdapter(getContext(), R.layout.item, health_list, 2);
        armor_list.add(null);
        damage_list.add(null);
        health_list.add(null);



        armor_view.setAdapter(adapter_armor);
        damage_view.setAdapter(adapter_damage);
        health_view.setAdapter(adapter_health);

        switch (type){
            case 0:create_list_0();warxp.setVisibility(View.GONE);break;
            case 1:
                armor_list.add(null);
                damage_list.add(null);
                health_list.add(null);
                armor_list.add(null);
                damage_list.add(null);
                health_list.add(null);

                create_list_1();
                hero_param= (Hero) getArguments().getSerializable(Hero.class.getSimpleName());

                warxp.setText(hero_param.getWarxp()+" "+getResources().getString(R.string.warxp));break;
        }
        armor_view.setOnItemClickListener(this);
        damage_view.setOnItemClickListener(this);
        health_view.setOnItemClickListener(this);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dialog_fr.canal(hero_param);
    }


    private void create_list_0() {
        Hero hero=save_inf.getHero();
        if(hero.getArmor()<5+(hero.getLevel()+1)/2){
            armor_list.set(0,new Skill("Броня", (int) ((Math.pow((hero.getArmor()-5)*2+1,4)+1)/2), (hero.getArmor()-5)*2+1, 1));}
        else{
            armor_list.set(0,new Skill("Броня", (int) ((Math.pow((hero.getArmor()-5)*2+1,4)+1)/2), (hero.getArmor()-5)*2+1, 1));
        }

        if(hero.getDamage()<20+hero.getLevel()*2){
            damage_list.set(0,new Skill("Оружие", (int) ((Math.pow((hero.getDamage()-20)/2+1,4)+2)/3), (hero.getDamage()-20)/2+1, 1));}
        else{
            damage_list.set(0,new Skill("Оружие", (int) ((Math.pow((hero.getDamage()-20)/2+1,4)+2)/3), (hero.getDamage()-20)/2+1, 1));
        }

        if(hero.getHealth()<250+hero.getLevel()*5){
            health_list.set(0,new Skill("Жизни", (int) ((Math.pow((hero.getHealth()-250)/25+1,4)+4)/5), (hero.getHealth()-250)/25+1, 5));}
        else{
            health_list.set(0,new Skill("Жизни", (int) ((Math.pow((hero.getHealth()-250)/25+1,4)+4)/5), (hero.getHealth()-250)/25+1, 5));
        }

        adapter_health.notifyDataSetChanged();
        adapter_damage.notifyDataSetChanged();
        adapter_armor.notifyDataSetChanged();
    }
    private void create_list_1() {


        armor_list.set(0,new Skill("Защита", 2, 1, 5));
        armor_list.set(1,new Skill("Защита", 4, 3, 15));
        armor_list.set(2,new Skill("Защита", 6, 5, 25));

        damage_list.set(0,new Skill("Атака", 2, 1, 5));
        damage_list.set(1,new Skill("Атака", 4, 3, 15));
        damage_list.set(2,new Skill("Атака", 6, 5, 25));

        health_list.set(0,new Skill("Жизни", 2, 1, 30));
        health_list.set(1,new Skill("Жизни", 4, 3, 90));
        health_list.set(2,new Skill("Жизни", 6, 5, 150));
        adapter_health.notifyDataSetChanged();
        adapter_damage.notifyDataSetChanged();
        adapter_armor.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Skill selectedSkill = (Skill) parent.getItemAtPosition(position);
        if (parent==parentp) {
            if (post)
            if (position == pos) {
                if (type==0)
                      hero_param =save_inf.getHero( );
                if (selectedSkill.getLevel_skill() <= hero_param.getLevel()) {
                    if ((hero_param.getXp()-selectedSkill.getXp_skill()>0 & type==0)||(hero_param.getWarxp()-selectedSkill.getXp_skill()>=0 & type==1)){
                        Toast toast = Toast.makeText(getContext(),
                                "+ " +
                                        selectedSkill.getIzm() + "  " + selectedSkill.getType(), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);

                        LinearLayout toastContainer = (LinearLayout) toast.getView();
                        DatabaseReference databaseReference_value = null;
                        // Устанавливаем прозрачность у контейнера
                        int dt = 0;int max=0;String typeS = null;


switch (type){
    case 0:{ if (parent == armor_view) {
        if (save_inf.getTypeGame())
        databaseReference_value = databaseReference.child("armor");
        dt =hero_param.getArmor();
        max=hero_param.getMax_armor();
        typeS=Hero.Type_armor;

    }
        if (parent == damage_view) {
            max=hero_param.getMax_damage();
            if (save_inf.getTypeGame())
            databaseReference_value = databaseReference.child("damage");
            dt = hero_param.getDamage();
            typeS=Hero.Type_damage;
        }
        if (parent == health_view) {

            max=hero_param.getMax_health();
            dt = hero_param.getHealth();
            if (save_inf.getTypeGame())
            databaseReference_value = databaseReference.child("health");
      typeS=Hero.Type_health;
        }
        if (dt + selectedSkill.getIzm()<=max){
            if (save_inf.getTypeGame()){
                post=false;
                databaseReference_value.setValue(String.valueOf(dt + selectedSkill.getIzm())).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        post=true;
                        databaseReference.child("xp").setValue(String.valueOf(hero_param.getXp() - selectedSkill.getXp_skill()));
                        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                        v.setTextColor(Color.BLACK);
                        v.setGravity(Gravity.CENTER);
                        toastContainer.setBackgroundColor(Color.TRANSPARENT);
                        toast.show();
                    }
                }
            });}
            else{
                save_inf.updateParHero(typeS.toString(),dt + selectedSkill.getIzm());
                save_inf.updateParHero(Hero.Type_xp,hero_param.getXp() - selectedSkill.getXp_skill());
                dialog_fr.canal(hero_param);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.BLACK);
                v.setGravity(Gravity.CENTER);
                toastContainer.setBackgroundColor(Color.TRANSPARENT);
                toast.show();
                dialog_fr.canal(null
                );
            }
        }

        else
            Toasty.error(getContext(),getResources().getString(R.string.moremax), Toasty.LENGTH_LONG,true).show();
    create_list_0();}break;
    case 1:
    {
        if (parent == armor_view) {
            if (hero_param.getArmver() + selectedSkill.getIzm()<=hero_param.getMax_armver())
            {
                hero_param.setWarxp(hero_param.getWarxp()-selectedSkill.getXp_skill());
                hero_param.setArmver(hero_param.getArmver()+selectedSkill.getIzm());
            } else
                Toasty.error(getContext(),getResources().getString(R.string.moremax), Toasty.LENGTH_LONG,true).show();

        }
        if (parent == damage_view) {
            if (hero_param.getDamver() + selectedSkill.getIzm()<=hero_param.getMax_damver())
            {
                hero_param.setWarxp(hero_param.getWarxp()-selectedSkill.getXp_skill());
                hero_param.setDamver(hero_param.getDamver()+selectedSkill.getIzm());
            } else
                Toasty.error(getContext(),getResources().getString(R.string.moremax), Toasty.LENGTH_LONG,true).show();
        }
        if (parent == health_view) {
            if (hero_param.getWarheal() + selectedSkill.getIzm()<=hero_param.getHealth())
            {
                hero_param.setWarxp(hero_param.getWarxp()-selectedSkill.getXp_skill());
                hero_param.setWarheal(hero_param.getWarheal()+selectedSkill.getIzm());
            } else
                Toasty.error(getContext(),getResources().getString(R.string.moremax), Toasty.LENGTH_LONG,true).show();
        }

        warxp.setText(hero_param.getWarxp()+" warxp");
        dialog_fr.canal(hero_param);
    }break;
}




                    }
                    else
                        Toasty.error(getContext(),getResources().getString(R.string.notenoughxp), Toasty.LENGTH_SHORT,true).show();
                }
                else
                    Toasty.error(getContext(),getResources().getString(R.string.lesslevel),Toast.LENGTH_LONG,true).show();
                pos = -1;
                parentp = null;
            }
        }
        else{

            pos=position;
            parentp = parent;
        }
    }




}
