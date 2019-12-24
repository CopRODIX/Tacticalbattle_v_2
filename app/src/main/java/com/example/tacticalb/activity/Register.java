package com.example.tacticalb.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tacticalb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;


public class Register extends AppCompatActivity {
    /*
    #Активность для создания новой учетной записи пользователя
    ##При корректном наборе всех полей приложение создает на Firebase Database новую деректорию с названием уникального id пользователя ,которой создает  Firebase Audification
    #Для активации учетной записи Firebase Audification посылает на почтовый ящик ссылку ,по которой можно будет полностью активировать почту
     */
    public EditText login,email,password,c_password;
    ImageView user_view;
    Spinner spinner;
    Button btn_regist;
    FirebaseAuth auth;
    Uri uri;
    DatabaseReference reference;
    DatabaseReference databaseReference_FOTO;
    StorageReference storageReference;
    int pos_lang;//язык
    TextView reg,country;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //
        user_view=findViewById(R.id.user_vieww);

        spinner=findViewById(R.id.lang);
        login=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        c_password=findViewById(R.id.password_confirm);
        btn_regist=findViewById(R.id.regidt);
        country = findViewById(R.id.country);
        reg = findViewById(R.id.reg);
        //
    //Закачивание начальной аватарки пользователя
        Glide.with(this).load("http://cj76241.tmweb.ru/pic/2.jpg").into(user_view);


        //Принадлежность к языку
        String [] lang_view=getResources().getStringArray(R.array.language_origenal);
        String [] lang=getResources().getStringArray(R.array.language_eng);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,lang_view);
       // arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setPrompt(getResources().getString(R.string.yourlanguage));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos_lang=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //




        auth= FirebaseAuth.getInstance();
        databaseReference_FOTO= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login_text= login.getText().toString().trim();
                final String email_text=  email.getText().toString().trim();
                final String password_text= password.getText().toString().trim();
                final String password_c= c_password.getText().toString().trim();
                if (login_text.isEmpty() || email_text.isEmpty() || password_text.isEmpty() || password_c.isEmpty())
                 //   Toast.makeText(getApplicationContext(),"Не все поля заполнены!!!",Toast.LENGTH_LONG).show();
                    Toasty.warning(getApplicationContext(),getResources().getString(R.string.notcomplete), Toasty.LENGTH_LONG,true).show();
                else  if (password_text.length()<8)
                  //  Toast.makeText(getApplicationContext(),"Пароль содержит меньше 8 символов",Toast.LENGTH_LONG).show();
                    Toasty.warning(getApplicationContext(),getResources().getString(R.string.less8), Toasty.LENGTH_LONG,true).show();
                else if (!password_c.equals(password_text))
                   // Toast.makeText(getApplicationContext(),"Пароли не одинаковы", LENGTH_SHORT).show();
                    Toasty.warning(getApplicationContext(),getResources().getString(R.string.notthesame), Toasty.LENGTH_LONG,true).show();
                    else
                regist(login_text,email_text,password_text,lang[pos_lang]);

            }
        });
        user_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.changesettings), Toast.LENGTH_SHORT).show();
            }
        });
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            c_password.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            btn_regist.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
            reg.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            country.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,38);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,38);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,38);
            c_password.setTextSize(TypedValue.COMPLEX_UNIT_SP,38);
            btn_regist.setTextSize(TypedValue.COMPLEX_UNIT_SP,33);
            reg.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
            country.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
        }
        else if(metricsB.widthPixels/metricsB.density>500){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            c_password.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            btn_regist.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            reg.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
            country.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            c_password.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            btn_regist.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
            reg.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
            country.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            email.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            c_password.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            btn_regist.setTextSize(TypedValue.COMPLEX_UNIT_SP,19);
            reg.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            country.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }
    }

    private void regist(String login, String email,String password,String language) {
//Создание нового пользователя в Firebase Authentication
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {

                FirebaseUser firebaseUser = auth.getCurrentUser();
                String _ID = firebaseUser.getUid();
                reference = FirebaseDatabase.getInstance().getReference("USER").child(_ID);
                HashMap<String, String> user = new HashMap<>();
                user.put("myid", _ID);
                user.put("login", login);
                user.put("email", email);
                user.put("language", language);
                user.put("level", "1");
                user.put("xp", "10");
                user.put("health", "250");
                user.put("armor", "5");
                user.put("damage", "20");
                user.put("mymap", "Hello~");
                user.put("online", "1");
                user.put("uri_pic", "http://cj76241.tmweb.ru/pic/2.jpg");

//Создание новой диретории Firebase Database c начальными значениями user{}
                reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Отправка на Firebase уведомления для потвержденеия почты  пользователя
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toasty.success(getApplicationContext(), getResources().getString(R.string.mail), Toasty.LENGTH_LONG, true).show();
                                        //Toast.makeText(getApplicationContext(),"Письмо для подтвердитя вашего EMAIL отправлено!!!",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Register.this, Login.class);

                                        startActivity(intent);
                                        finish();
                                    } else
                                        Toasty.error(getApplicationContext(), getResources().getString(R.string.errorregistration), Toasty.LENGTH_LONG, true).show();
                                    //Toast.makeText(getApplicationContext(),"Ошибка",Toast.LENGTH_LONG).show();
                                }

                            });


                        }
                    }
                });
            }
            else Toasty.error(getApplicationContext(),getResources().getString(R.string.noaccess), Toasty.LENGTH_SHORT,true).show();

        }

    });



        }








}





