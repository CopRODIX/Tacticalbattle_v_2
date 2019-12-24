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
import android.widget.Toast;

import com.example.tacticalb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends Activity {
    /*
    #Активность ,которая позволяет востановить забытый пароль через почтовный ящик
    Приложение присылает запрос на сервис Firebase о смене пароля и сервис отправляет ссылку на изменение пароля
     */
    EditText editText;
    Button send;
    TextView rest;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.restoration));
        setContentView(R.layout.forgot_activity);
        editText=findViewById(R.id.email_send);
        send = findViewById(R.id.send_btn);
        rest = findViewById(R.id.re);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            rest.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            send.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            rest.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
            send.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
        }
        else if(metricsB.widthPixels/metricsB.density>500){
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            rest.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
            send.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            rest.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
            send.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            rest.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            send.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }
        editText.setHintTextColor(getResources().getColor(R.color.white));
    }
//Метод который с помощью sendPasswordResetEmail отправляет на Firebase уведомление о смене пароляв свою очередь Firebase высылает на почту пользователя письмо  с сылкой на изщменение пароля
   public void SendEmail(View view) {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.sendletter),Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.error),Toast.LENGTH_LONG).show();
            }
        });
    }
}
