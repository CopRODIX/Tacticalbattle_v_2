package com.example.tacticalb.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity implements View.OnClickListener {
    /*
    # Активность для авторизации пользователя с помощью email и пароля
    # Также дает возможность пользователю создать учетную запись
    # Уведомляет зарегистрированного пользователя что нужно потвердить свой почтовый ящик,в случае если это не сделано ранее
    #Дает возможность восстановить пароль , в случае его утраты
     */

    EditText email,password;
    Button login_btn,regist_btn,forgot;
    FirebaseAuth auth;
    CheckBox known;
    String email_text,password_text;
    boolean remember=false;
    boolean l=false;
    Save_inf save_inf;


    //
    MediaPlayer mediaPlayer;
    FirebaseUser firebaseUser;



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mediaPlayer!=null)
            mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer!=null)
            mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int k=0;
    }



    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
    //    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeGame);
        setContentView(R.layout.login_activity);

        save_inf= new Save_inf(this);



        Typeface face = Typeface.createFromAsset(getAssets(), "typt.ttf");
        Typeface face1 = Typeface.createFromAsset(getAssets(), "suranna.ttf");
        known=findViewById(R.id.known);
        known.setTypeface(face1);

        email=findViewById(R.id.email);
        email.setTypeface(face1);

        password=findViewById(R.id.password);
        password.setTypeface(face1);

        login_btn=findViewById(R.id.login);
        login_btn.setTypeface(face1);

        regist_btn=findViewById(R.id.rigistrator);
        regist_btn.setTypeface(face1);

        forgot=findViewById(R.id.resetpassword);
        forgot.setTypeface(face1);
        forgot.setOnClickListener(this);

        regist_btn.setOnClickListener(this);

        login_btn.setOnClickListener(this);

        known.setOnClickListener(this);



 mediaPlayer=MediaPlayer.create(this,R.raw.top_music_foractivity);
mediaPlayer.setLooping(true);
        mediaPlayer.start();

        auth= FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        //
        if (firebaseUser!=null & save_inf.getBoolean(Save_inf.REMEMBER_FOR_LOGIN)) {
            if (firebaseUser.isEmailVerified()){
                Intent intent=new Intent(Login.this, MenuAct.class);
                startActivity(intent);
                finish();}}




        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){
            known.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            regist_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            forgot.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            known.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            regist_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            forgot.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
        }
        else if(metricsB.widthPixels/metricsB.density>500){
            known.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            regist_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            forgot.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            known.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            regist_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            forgot.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            known.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            regist_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            forgot.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login:

                this.password_text=password.getText().toString();
                this.email_text=email.getText().toString();
                if (this.password_text.isEmpty() || this.email_text.isEmpty())
                    Toasty.warning(getApplicationContext(),getResources().getString(R.string.notfull)+ "!", Toasty.LENGTH_LONG,true).show();
                   // Toast.makeText(getApplicationContext(),"Не все заполнено !!!",Toast.LENGTH_LONG).show();
                else {
                    log_in(this.email_text,this.password_text);
                }
                break;
            case R.id.rigistrator:
                Intent intent=new Intent(Login.this, Register.class);
                startActivity(intent);break;
            case R.id.known:
                if (known.isChecked()){
                    remember=true;
                }
                else
                    remember=false;
                break;
            case R.id.resetpassword:
startActivity(new Intent(this,ForgotPassword.class));
                break;
        }
    }
void log_in(String email_,String password_){

    auth.signInWithEmailAndPassword(email_,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                if (auth.getCurrentUser().isEmailVerified()){
                Intent intent=new Intent(Login.this, MenuAct.class);
                startActivity(intent);

                save_inf.putBoolean(Save_inf.REMEMBER_FOR_LOGIN,remember);
                finish();}
                else
                    Toasty.info(getApplicationContext(),getResources().getString(R.string.confirmemail), Toasty.LENGTH_LONG,true).show();
            }
            else{
                Toasty.error(getApplicationContext(),getResources().getString(R.string.authentication), Toasty.LENGTH_LONG,true).show();
               // Toast.makeText(getApplicationContext(),"Аутентификация не состоялась",Toast.LENGTH_LONG).show();}
        }
    }});
}

    }

