package com.example.tacticalb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class ChangeLOGIN extends Activity {
    /*
#Смена имя пользователя на сервере FireBase Database RealTime

     */
    Button change;
    TextView changelog;
    EditText login;
    Hero hero;
    private static final String TAG="Change_EMAIL";
    FirebaseUser user;

    @Override
    protected void onCreate(  Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.changename));
        setContentView(R.layout.change_email);
        //Полученние данных о пользователе из  SharedPreference
        Save_inf save_inf=new Save_inf(this);
        hero=save_inf.getHero( );


        change=findViewById(R.id.chang);
        changelog=findViewById(R.id.loginchange);
        login=findViewById(R.id.login);
        login.setText(hero.getLogin());
        user = FirebaseAuth.getInstance().getCurrentUser();
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,48);
            changelog.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,42);
            changelog.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);

        }
        else if(metricsB.widthPixels/metricsB.density>500){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            changelog.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,33);
            changelog.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            changelog.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }
    }

//Метод смены логина
    public void Changelogin(View view) {
        if (!login.getText().toString().isEmpty()){
            //Подключение к Firebase Database и изменение директории "login"
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef=database.getReference("USER").child(user.getUid()).child("login");
            myRef.setValue(login.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()){
                   Toasty.success(getApplicationContext(),getResources().getString(R.string.changelogin), Toasty.LENGTH_LONG,true).show();
                  // Toast.makeText(getApplicationContext(),"Логин был изменен",Toast.LENGTH_LONG).show();
                   finish();
               }
               else
                  // Toast.makeText(getApplicationContext(),"Ошибка в измнении login",Toast.LENGTH_LONG).show();
                   Toasty.error(getApplicationContext(),getResources().getString(R.string.loginerror), Toasty.LENGTH_LONG,true).show();
                }
            });

    }
    }
}
