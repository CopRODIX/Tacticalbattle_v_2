package com.example.tacticalb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class ChangePASSWORD extends Activity {
    /*
#Смена пароля пользователя на сервере FireBase Audification

 */
    Button change;
    TextView changepass;
    EditText password;
    private static final String TAG="Change_PASSWORD";
    FirebaseUser user;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.changepassword));
        setContentView(R.layout.change_password);
        password=findViewById(R.id.password);
        change = findViewById(R.id.changpass);
        changepass = findViewById(R.id.passchange);
        //Подключение к Firebase Authentication и получение ID пользователя
        user = FirebaseAuth.getInstance().getCurrentUser();
        Toasty.info(getApplicationContext(),getResources().getString(R.string.more8)+"!", Toasty.LENGTH_LONG).show();
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,48);
            changepass.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,42);
            changepass.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);

        }
        else if(metricsB.widthPixels/metricsB.density>500){
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            changepass.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,33);
            changepass.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            changepass.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            change.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }
    }
    public void updateUserPassword() {
        if (!password.getText().toString().isEmpty()) {
            if (password.getText().toString().length() < 8)
                Toasty.warning(getApplicationContext(),getResources().getString(R.string.less8)+"!", Toasty.LENGTH_LONG,true).show();
               // Toast.makeText(getApplicationContext(), "Пароль меньше 8 символов!!!", Toast.LENGTH_LONG).show();
            else {
                String newPassword = password.getText().toString();

                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toasty.success(getApplicationContext(),getResources().getString(R.string.passwordwarning), Toasty.LENGTH_LONG,true).show();
                                    //Toast.makeText(getApplicationContext(), "Пароль изменен", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "User password updated.");
                                    finish();


                                } else
                                    Toasty.error(getApplicationContext(),getResources().getString(R.string.passwordwarning), Toasty.LENGTH_LONG,true).show();
                                  //  Toast.makeText(getApplicationContext(), "Пароль не изменен", Toast.LENGTH_LONG).show();

                            }
                        });

            }
        }
    }
    //Метод смены пароля
    // Приложение с помощью метода updatePassword обновляет пароль пользователя в хешированном аиде
public void nkn(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = password.getText().toString();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.nochange),Toast.LENGTH_LONG).show();
                    }
                });}
    public void Changepassword(View view) {
        //updateUserPassword();
        nkn();
    }
}
