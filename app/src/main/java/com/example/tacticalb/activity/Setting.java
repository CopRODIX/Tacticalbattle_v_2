package com.example.tacticalb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Setting extends AppCompatActivity implements View.OnClickListener {
    /*
    # Активность настроек пользователя
    ##Изменение данных пользователя
    ###Фотография аватарки(Selectfoto)
    ###Пароль(ChangePASSWORD)
    ###Имя пользователя или позывной(login)(ChangeLOGIN)
    ##Выход из учетной записи
     */
    Button login_btn,password;
    ImageButton exit;
    DatabaseReference databaseReference;
    Save_inf save_inf;
    TextView login;
    ImageView user_view;


    String update_foto;
    static  final int UPDATE_FOTO=1;
    static final String backTypeselection="back_type_picture";


    FirebaseUser firebaseUser;
    StorageReference mStorageRef;
    FirebaseUser user;
    Hero hero;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.settings));
        setContentView(R.layout.setting_activity);
        save_inf = new Save_inf(this);

        user_view=findViewById(R.id.image);

        login=findViewById(R.id.name);
        login_btn=findViewById(R.id.change_login);
        password=findViewById(R.id.change_password);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
        user_view.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        password.setOnClickListener(this);

        update_foto=save_inf.getString(Save_inf.REMEMBER_FOR_URL_AVATAR);
        hero=save_inf.getHero( );

        //Подключение к информации пользователя на Firebase Database
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


            DatabaseReference databaseRef= FirebaseDatabase.getInstance().getReference("USER").child(firebaseUser.getUid()).child("login");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                login.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("USER").child(firebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Glide.with(this).load(update_foto).into(user_view);

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(login, 12, 112, 5,
                TypedValue.COMPLEX_UNIT_SP);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){

            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);

        }
        else if(metricsB.widthPixels/metricsB.density>500){
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{
            login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image:{
                //Запуск активности для обновления аватарки
                Intent intent=new Intent(this,Selectfoto.class);
                startActivityForResult(intent,UPDATE_FOTO);
            }
            break;
            case R.id.change_login:{
                //Запуск активности для замены логина или имени пользователя
                startActivity(new Intent(this, ChangeLOGIN.class));
            }break;
            case R.id.change_password:{
                //Запуск активности для замены пароля  пользователя
                startActivity(new Intent(this,ChangePASSWORD.class));
            }break;
            case R.id.exit:{
                //Выход из учетной записи пользователя
                FirebaseAuth.getInstance().signOut();
                save_inf.clear();
                Intent intent=new Intent(this,Login.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Загрузка обновленных картинки
        if (resultCode==RESULT_OK){
            if (requestCode==UPDATE_FOTO) {


                Glide.with(this).load(save_inf.getString(Save_inf.REMEMBER_FOR_URL_AVATAR)).into(user_view);

            }
        }
    }

}
