package com.example.tacticalb.activity;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.dialog.Shop_par;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class Difficulty extends AppCompatActivity implements   View.OnClickListener,Map_S.Changeparam,Shop_par.Dialog_Fr {
    /*
    #Класс отвечающий для меню входа в игру и открывает следующие Fragment:
    ##Home_map
    ##Shop_map
    #В разметке класса Difficulty показываются основные параметры пользователя ,закачка которых в строке 136
     */
    FirebaseUser firebaseUser;
    ProgressBar xp,health,armor,damage;
    TextView xp_text,health_text,armor_text,damage_text,level;
    DatabaseReference databaseReference;
    Fragment select;
    private static String TAG="Shop_map";
  boolean pos=true;
    Save_inf save_inf=null;
    //Проверка подключения к интернет сети
    public boolean ConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {

                        return true;
                    }

        }
        Toasty.error(getApplicationContext(),getResources().getString(R.string.nointernet), Toasty.LENGTH_LONG,true).show();
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!ConnectingToInternet()&save_inf.getTypeGame()) {
            //Toast для предупреждения
            Toasty.error(getApplicationContext(), getResources().getString(R.string.includeinterner)+" Xp", Toasty.LENGTH_LONG, true).show();
            Toasty.warning(getApplicationContext(), getResources().getString(R.string.warning)+" Xp", Toasty.LENGTH_LONG, true).show();
        }
    }

    BottomNavigationView navigationView;

    @Override
    protected void onResume() {
        super.onResume();
            updatePar();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setTheme(R.style.AppThemeGame);
        setContentView(R.layout.difficulty_activity);

        save_inf=new Save_inf(this);
        navigationView=findViewById(R.id.botton_menu);
        LinearLayout xpc,damagec;
        xpc=findViewById(R.id.XP);
        damagec=findViewById(R.id.DAMAGE);
        xpc.setOnClickListener(this);
        damagec.setOnClickListener(this);
        navigationView.setOnNavigationItemSelectedListener(navig);
        select=new Home_map();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Home_map()).commit();
        //
        xp=findViewById(R.id.progress_xp);
        xp_text=findViewById(R.id.textView_xp);
        health=findViewById(R.id.progress_health);
        health_text=findViewById(R.id.textView_heralth);
        armor=findViewById(R.id.progress_armor);
        armor_text=findViewById(R.id.textView_arm);
        damage=findViewById(R.id.progress_damage);
        damage_text=findViewById(R.id.textView_dam);
        level=findViewById(R.id.level);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(level, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(xp_text, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(health_text, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(armor_text, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(damage_text, 12, 112, 10,
                TypedValue.COMPLEX_UNIT_SP);










        //
//Назначение Слушателя на итзменения на Firebase Database в директории  \{_ID}
        if (save_inf.getTypeGame()) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("USER").child(firebaseUser.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Обновление информациооной доски пользователя

                    updatePar();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//
        }
        else
            updatePar();




    }
    //Метод для обновления информации для пользователя
    public void updatePar(){
        Hero hero=save_inf.getHero( );
        level.setText(hero.getLevel()+" уровень");
        xp_text.setText(hero.getXp()+"");
        xp.setMax(hero.getNextXp());
        xp.setProgress(hero.getXp());
        health_text.setText(hero.getHealth()+"");
        health.setMax(hero.getMax_health());
        health.setProgress(hero.getHealth());
        damage_text.setText(hero.getDamage()+"");
        damage.setMax(hero.getMax_damage());
        damage.setProgress(hero.getDamage());
        armor_text.setText(hero.getArmor()+"");
        armor.setMax(hero.getMax_armor());
        armor.setProgress(hero.getArmor());
    }
    //Создание и назначения слушателя на нижнее меню пользователя
private BottomNavigationView.OnNavigationItemSelectedListener navig= new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
       select=null;
        switch (menuItem.getItemId()){
            //Открытие фрагментов
            case R.id.shop:
                //Магазин карт
                select=new Shop_map();
                break;
            case R.id.home_:
                //Фрагмент для открытия купленных карт
                select=new Home_map();
                break;
           /* case R.id.play_on:
                select=new Online_ply();
                break;*/

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,select).commit();
        return true;
    }
};


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

;
        getMenuInflater().inflate(R.menu.menu_scenario, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.scenario_update:
                if (select instanceof Home_map)
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Home_map()).commit();
                else if (select instanceof Shop_map)
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Shop_map()).commit();
                /*else if (select instanceof Online_lay)
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new On ine_play()).commit();*/


                return true;
         /*   case R.id.chat:
                Intent intent1 = new Intent(getApplicationContext(),  .class);
                //На время презентациии
              //  startActivity(intent1);*/
        }

        return true;
    }


    @Override
    public void canal(Hero hero) {

            pos=true;
updatePar();
    }

    @Override
    public void onClick(View v) {
        //Открытие диалога Shop_par для прокачки параметров на Firebase Database \{_ID}
        if (v.getId()==R.id.DAMAGE || v.getId()==R.id.XP ){
            if (pos){
                pos=false;
            Shop_par shop_par= new Shop_par();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Save_inf.CONNECT_DIALOG_FOR_PARAM_TYPE,0);
        shop_par.setArguments(bundle);
        shop_par.show(getSupportFragmentManager(), TAG);}}
    }

    @Override
    public void param() {

        updatePar();
    }
}
