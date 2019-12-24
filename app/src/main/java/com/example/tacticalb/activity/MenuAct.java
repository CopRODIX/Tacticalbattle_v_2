package com.example.tacticalb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.tacticalb.ClassforServer.Otvet;
import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.Service.VibrateService;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.class_unit.Unit;
import com.example.tacticalb.dialog.Answer;
import com.example.tacticalb.dialog.Loading;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class MenuAct extends AppCompatActivity implements View.OnClickListener, Answer.Dialog_Fr {
    /*
    #Активность главного меню приложения
    ##Настройки пользователя(Setting)
    ##Рейтинг(RatingU)
    ##О разработчиках(About)
    ##
    #Также в классе есть главный слушатель на изменения данных пользователя на Firebase Database и синхронизирует с героем приложения save_inf.getHero()
    #В случае конфликтно закрытой игры (выход из приложения во время игры или без интеренета,что не позволило изменить данные пользователя) предоставляет возможность пользователю ее запустить
     */
    Save_inf save_inf;
    ImageButton points;
    Button play,reting,setting;
    LinearLayout linmenu;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    //ProgressDialog progressDialog;
    Loading loading;
    int t=0;
    private static final int ABOUT=030;
    public static final String TAG="menu";
    ValueEventListener valueEventListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        linmenu = findViewById(R.id.linearmenu);
        play=findViewById(R.id.play);
        reting=findViewById(R.id.web_reyt);
        setting=findViewById(R.id.setting);
        points=findViewById(R.id.points);
        points.setOnClickListener(this);
        play.setOnClickListener(this);
        reting.setOnClickListener(this);
        setting.setOnClickListener(this);
        save_inf= new Save_inf(this);
        if (!save_inf.getTypeGame())
        {
            reting.setVisibility(View.GONE);
            setting.setVisibility(View.GONE);
        }





        if (save_inf.getTypeGame()) {
            loading=new Loading(this);
            loading.show();


//

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("USER").child(firebaseUser.getUid());
            valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    Otvet otvet = dataSnapshot.getValue(Otvet.class);

                    loading.dismiss();
//Toast.makeText(getApplicationContext(),otvet.getLANGUAGE(),Toast.LENGTH_SHORT).show();
                    save_inf.putString(Save_inf.REMEMBER_FOR_URL_AVATAR, otvet.getURI_pic());
                    Hero hero = new Hero(new Unit(), otvet.getMyid(), otvet.getLOGIN(), otvet.getEMAIL(), otvet.getLANGUAGE(), otvet.getMymap(), Integer.parseInt(otvet.getXP()), Integer.parseInt(otvet.getLEVEL()), Integer.parseInt(otvet.getARMOR()), Integer.parseInt(otvet.getDAMAGE()), Integer.parseInt(otvet.getHEALTH()), 5
                    );
            //  if (save_inf.getHero("hero")!=null)
                    if (hero.getXp() > hero.getNextXp()) {
                        databaseReference.child("level").setValue(String.valueOf(hero.getLevel() + 1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                       save_inf.ToastnewLevel();
                    }
                        });
                    }
                    save_inf.putUser(Save_inf.REMEMBER_HERO, hero);
//progress bar
            t = 1;
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
//
}

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){

            play.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            reting.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            setting.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            play.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            reting.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            setting.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
        }
        else if(metricsB.widthPixels/metricsB.density>500){
            play.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            reting.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            setting.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            play.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            reting.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            setting.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            play.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            reting.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            setting.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            Intent intent= new Intent(this,ChoseTypeGame.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (save_inf.getTypeGame())
            databaseReference.removeEventListener(valueEventListener);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.points:{
                Intent intent = new Intent(this,About.class);
                startActivityForResult(intent,ABOUT);}
            break;
            case R.id.play:{
                if (save_inf.getBoolean("resume")){

                    //       Toast.makeText(this,"продолжить",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(this, VibrateService.class);
                    startService(intent);
                    Answer dialogFragment= new Answer();
                    dialogFragment.show(getSupportFragmentManager(),"menu");
                }


                else {
                    Intent intent = new Intent(this, Difficulty.class);
                    startActivity(intent);
                }

            }break;
            case R.id.web_reyt:{
                Intent intent=new Intent(this,RatingU.class);
                startActivity(intent);
            }break;
            case R.id.setting:{
                Intent intent=new Intent(this,Setting.class);
                startActivity(intent);
            }break;
        }
    }



    @Override
    public void canal(boolean otvet) {
        Intent intent = new Intent(this,Difficulty.class);
        Scenario scenario = save_inf.getscenario(Save_inf.RESUME_SCENARIO_LOST_GAME);
        Hero hero =save_inf.getHero();
        switch (scenario.getWin()) {
            case 1:
                int sum=hero.getXp()+(1 + scenario.getXP_SOLD()) * scenario.getXP_SOLD() + scenario.sumUnit();
                if (save_inf.getTypeGame())
                databaseReference.child("xp").setValue(String.valueOf(sum)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                        save_inf.putBoolean(Save_inf.RESUME_GAME, false);
                            Toasty.custom(getApplicationContext(),getResources().getString(R.string.win),R.drawable.win, R.color.appRegist, Toasty.LENGTH_LONG,true,true).show();

                        startActivity(intent);}
                        else
                            Toasty.error(getApplicationContext(),getResources().getString(R.string.nointernet), Toasty.LENGTH_SHORT,true).show();
                    }
                });
else {save_inf.updateParHero(Hero.Type_xp,sum);

                }
                break;
            case -1:
                int s=hero.getXp()-scenario.getXP_SOLD();
               if (save_inf.getTypeGame())
                databaseReference.child("xp").setValue(String.valueOf(s)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                        save_inf.putBoolean(Save_inf.RESUME_GAME, false);
                            Toasty.custom(getApplicationContext(),getResources().getString(R.string.lose),R.drawable.lose,R.color.colorAccent, Toasty.LENGTH_LONG,true,true).show();

                        startActivity(intent);}   else
                            Toasty.error(getApplicationContext(),getResources().getString(R.string.nointernet), Toasty.LENGTH_SHORT,true).show();
                    }
                });
else{
    save_inf.updateParHero(Hero.Type_xp,s);

               }
                break;
            case 0:

                startActivity(intent);

                if (otvet) {

                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtra(Save_inf.REMEMBER_BOOLEAN_FOR_ARGUMENTS, true);
                    startActivity(intent1);
                } else {
                    save_inf.putBoolean(Save_inf.RESUME_GAME, false);


                }
                break;
        }


    }
}
