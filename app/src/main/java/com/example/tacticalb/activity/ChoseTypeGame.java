package com.example.tacticalb.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.tacticalb.ClassforServer.MainPreseter;
import com.example.tacticalb.ClassforServer.Main_Base;
import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.class_unit.Unit;
import com.example.tacticalb.dialog.Loading;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ChoseTypeGame extends AppCompatActivity implements Main_Base {

    Save_inf save_inf;
    Save_inf save_inf_local;
    boolean t=true;
    Button internet,local;
    TextView tactic;

    FirebaseAuth auth;   FirebaseUser firebaseUser;
    public static final Integer apiapp=2;
    // ProgressDialog progressDialog;
    Loading loading;
    ValueEventListener valueEventListener;
    //Локальный пользователь игры
    Hero b;
    String url="http://tacticalbattle.tmweb.ru/download.html";
    final SpannableString webaddress = new SpannableString(
            url);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosetypegame);
        internet=findViewById(R.id.online);
        tactic = findViewById(R.id.tactic);
        local=findViewById(R.id.button);
        //Построенние ссылки
        Linkify.addLinks(webaddress, Linkify.ALL);
        //Подключение к SharedPreference общей директории и локальной сооответственно
       save_inf=new Save_inf(getApplicationContext());
       save_inf_local= new Save_inf(this,Save_inf.CHILD_FOR_USER_LOCAL);
//Проверка наличии пользователя в локальной системе ,и его добавление в в случае отсутствия его создание
        if (save_inf_local.getHero()==null) {
            save_inf_local.putString(Save_inf.SCENARIO_LIST_SHOP, "[{\"ABOUT\":\"If you love the water, then you will be fine here\",\"ACTIVEFIELD\":\"43 4 4 1 1 1 1 3 1 3 10 3 1 3 1 1 1 4 4 46-1 4 4 1 1 1 3 1 3 3 3 1 3 1 1 1 1 4 4 1 -1 4 4 1 1 1 1 1 3 1 14 3 1 1 1 1 1 4 4 1 -1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1-1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 -2 2 2 2 0 0 0 0 0 0 0 0 0 0 0 0 2 2 2 2 -1 1 2 2 0 0 0 0 0 0 0 0 0 0 0 0 0 2 2 1 -2 2 1 0 0 0 0 0 0 35 1 0 0 0 0 0 0 1 2 2 -1 2 2 0 0 0 0 0 0 3 1 3 0 0 0 0 0 0 2 1 -21 2 0 0 0 0 0 0 3 10 32 1 0 0 0 0 0 0 2 21 -1 2 2 0 0 0 0 0 0 3 1 3 0 0 0 0 0 0 2 1 -2 2 1 0 0 0 0 0 0 35 1 0 0 0 0 0 0 1 2 2 -1 1 2 2 0 0 0 0 0 0 0 0 0 0 0 0 0 2 2 1-2 2 2 2 0 0 0 0 0 0 0 0 0 0 0 0 2 2 2 2 -1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 -1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 -1 4 4 1 1 1 1 1 3 1 32 1 3 1 1 1 1 4 4 1 -1 4 4 1 1 1 1 3 3 3 3 3 1 1 1 1 1 4 4 1 -1 4 4 1 1 1 1 3 1 3 3 1 3 1 1 1 1 4 4 1 -46 4 4 1 1 1 1 1 3 14 3 1 1 1 1 1 1 4 4 43 -\",\"NAME\":\"Lake\",\"TYPELIST\":\"NO\",\"USER\":\"admin@admin.ru\",\"DEVIL\":10,\"DOWNLOADS\":13,\"GHOST\":0,\"LEVEL\":1,\"NOWStrange\":0,\"ROBBER\":10,\"STRAGE\":5,\"TYPE\":1,\"WARRIOR\":10,\"WIZARD\":10,\"WOLF\":10,\"XP_SOLD\":7,\"_ID\":7,\"win\":0},{\"ABOUT\":\"Nobody knows what\\u0027s here\",\"ACTIVEFIELD\":\"NO\",\"NAME\":\"Unknown spaces\",\"TYPELIST\":\"No\",\"USER\":\"admin@admin.ru\",\"DEVIL\":10,\"DOWNLOADS\":10,\"GHOST\":0,\"LEVEL\":1,\"NOWStrange\":0,\"ROBBER\":10,\"STRAGE\":5,\"TYPE\":0,\"WARRIOR\":10,\"WIZARD\":10,\"WOLF\":10,\"XP_SOLD\":5,\"_ID\":8,\"win\":0}]");
            save_inf_local.putString(Save_inf.SCENARIO_LIST_HOME, "[{\"ABOUT\":\"Welcome to the training camp where you can learn the basics of combat!\",\"ACTIVEFIELD\":\"4 4 4 1 0 0 1 10 0 0 3 1 1 1 0 0 0 4 4 0 -4 4 4 1 0 1 1 0 2 0 3 1 0 1 1 1 1 0 0 0 -4 43 2 2 2 1 1 2 2 2 32 1 3 0 0 0 0 4 0 0 -24 2 2 2 2 2 1 2 2 3 3 2 2 0 1 0 1 4 1 1 -2 2 2 24 2 2 0 0 3 0 0 0 0 2 2 2 0 43 4 4 -2 21 2 2 2 1 0 1 1 0 1 1 0 2 2 2 1 4 0 0 -4 2 2 2 0 1 0 0 3 1 3 3 0 1 2 2 2 2 4 4 -4 2 2 0 0 1 1 1 1 3 1 1 0 2 2 2 2 2 26 1 -4 4 2 0 1 0 0 1 16 32 3 3 1 0 1 0 1 2 2 1 -4 4 4 1 0 1 0 1 1 3 3 1 0 0 1 0 1 4 1 4 -4 1 1 1 2 2 2 2 1 1 3 3 3 1 1 1 0 1 1 1 -4 4 1 2 25 2 2 2 2 1 3 2 0 0 1 0 1 4 1 4 -4 1 1 1 0 2 2 1 2 2 2 2 2 0 2 0 0 4 1 4 -2 2 4 0 1 1 0 10 2 2 2 2 2 2 1 1 0 4 2 1 -2 2 2 1 1 1 0 0 3 3 2 2 2 2 0 0 0 4 2 2 -1 2 2 1 1 0 0 1 1 3 2 1 0 1 1 0 0 4 4 4 -4 4 21 2 1 0 1 0 3 3 1 1 0 1 1 0 0 0 4 1 -4 0 4 0 0 15 1 1 1 3 3 3 0 0 0 0 0 4 4 4 -1 0 4 0 0 0 1 0 0 1 3 0 2 2 1 2 2 2 4 4 -1 0 4 1 0 1 1 0 0 3 0 3 2 1 2 2 2 2 4 1 -\",\"NAME\":\"Hello\",\"TYPELIST\":\"[\\r\\n  \\\"{\\\\\\\"Rb1\\\\\\\":{\\\\\\\"armor\\\\\\\":5,\\\\\\\"armver\\\\\\\":60,\\\\\\\"damage\\\\\\\":20,\\\\\\\"damver\\\\\\\":40,\\\\\\\"health\\\\\\\":35,\\\\\\\"initiative\\\\\\\":6,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":35,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr2\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Wo2\\\\\\\":{\\\\\\\"armor\\\\\\\":4,\\\\\\\"armver\\\\\\\":50,\\\\\\\"damage\\\\\\\":25,\\\\\\\"damver\\\\\\\":60,\\\\\\\"health\\\\\\\":30,\\\\\\\"initiative\\\\\\\":4,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":30,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Wa2\\\\\\\":{\\\\\\\"armor\\\\\\\":10,\\\\\\\"armver\\\\\\\":70,\\\\\\\"damage\\\\\\\":15,\\\\\\\"damver\\\\\\\":50,\\\\\\\"health\\\\\\\":40,\\\\\\\"initiative\\\\\\\":2,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":40,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Wi2\\\\\\\":{\\\\\\\"armor\\\\\\\":3,\\\\\\\"armver\\\\\\\":30,\\\\\\\"damage\\\\\\\":30,\\\\\\\"damver\\\\\\\":70,\\\\\\\"health\\\\\\\":25,\\\\\\\"initiative\\\\\\\":1,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":25,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Wo3\\\\\\\":{\\\\\\\"armor\\\\\\\":4,\\\\\\\"armver\\\\\\\":50,\\\\\\\"damage\\\\\\\":25,\\\\\\\"damver\\\\\\\":60,\\\\\\\"health\\\\\\\":30,\\\\\\\"initiative\\\\\\\":4,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":30,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[17,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[17,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"De2\\\\\\\":{\\\\\\\"armor\\\\\\\":2,\\\\\\\"armver\\\\\\\":40,\\\\\\\"damage\\\\\\\":12,\\\\\\\"damver\\\\\\\":30,\\\\\\\"heal\\\\\\\":10,\\\\\\\"health\\\\\\\":20,\\\\\\\"initiative\\\\\\\":3,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":20,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}}}\\\"\\r\\n]\",\"USER\":\"admin@admin.ru\",\"DEVIL\":1,\"DOWNLOADS\":6,\"GHOST\":0,\"LEVEL\":1,\"NOWStrange\":0,\"ROBBER\":1,\"STRAGE\":5,\"TYPE\":1,\"WARRIOR\":1,\"WIZARD\":1,\"WOLF\":1,\"XP_SOLD\":0,\"_ID\":16,\"win\":0}]");
            // save_inf_local.putString(Save_inf.SCENARIO_LIST_HOME,"[ 2 1 3 0 0 0 0 4 0 0 -24 2 2 2 2 2 1 2 2 3 3 2 2 0 1 0 1 4 1 1 -2 2 2 24 2 2 0 0 3 0 0 0 0 2 2 2 0 43 4 4 -2 21 2 2 2 1 0 1 1 0 1 1 0 2 2 2 1 4 0 0 -4 2 2 2 0 1 0 0 3 1 3 3 0 1 2 2 2 2 4 4 -4 2 2 0 0 1 1 1 1 3 1 1 0 2 2 2 2 2 26 1 -4 4 2 0 1 0 0 1 16 32 3 3 1 0 1 0 1 2 2 1 -4 4 4 1 0 1 0 1 1 3 3 1 0 0 1 0 1 4 1 4 -4 1 1 1 2 2 2 2 1 1 3 3 3 1 1 1 0 1 1 1 -4 4 1 2 25 2 2 2 2 1 3 2 0 0 1 0 1 4 1 4 -4 1 1 1 0 2 2 1 2 2 2 2 2 0 2 0 0 4 1 4 -2 2 4 0 1 1 0 10 2 2 2 2 2 2 1 1 0 4 2 1 -2 2 2 1 1 1 0 0 3 3 2 2 2 2 0 0 0 4 2 2 -1 2 2 1 1 0 0 1 1 3 2 1 0 1 1 0 0 4 4 4 -4 4 21 2 1 0 1 0 3 3 1 1 0 1 1 0 0 0 4 1 -4 0 4 0 0 15 1 1 1 3 3 3 0 0 0 0 0 4 4 4 -1 0 4 0 0 0 1 0 0 1 3 0 2 2 1 2 2 2 4 4 -1 0 4 1 0 1 1 0 0 3 0 3 2 1 2 2 2 2 4 1 -\",\"NAME\":\"Hello\",\"TYPELIST\":\"[\\r\\n  \\\"{\\\\\\\"Rb1\\\\\\\":{\\\\\\\"armor\\\\\\\":5,\\\\\\\"armver\\\\\\\":60,\\\\\\\"damage\\\\\\\":20,\\\\\\\"damver\\\\\\\":40,\\\\\\\"health\\\\\\\":35,\\\\\\\"initiative\\\\\\\":6,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":35,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr2\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Wo2\\\\\\\":{\\\\\\\"armor\\\\\\\":4,\\\\\\\"armver\\\\\\\":50,\\\\\\\"damage\\\\\\\":25,\\\\\\\"damver\\\\\\\":60,\\\\\\\"health\\\\\\\":30,\\\\\\\"initiative\\\\\\\":4,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":30,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Wa2\\\\\\\":{\\\\\\\"armor\\\\\\\":10,\\\\\\\"armver\\\\\\\":70,\\\\\\\"damage\\\\\\\":15,\\\\\\\"damver\\\\\\\":50,\\\\\\\"health\\\\\\\":40,\\\\\\\"initiative\\\\\\\":2,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":40,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Wi2\\\\\\\":{\\\\\\\"armor\\\\\\\":3,\\\\\\\"armver\\\\\\\":30,\\\\\\\"damage\\\\\\\":30,\\\\\\\"damver\\\\\\\":70,\\\\\\\"health\\\\\\\":25,\\\\\\\"initiative\\\\\\\":1,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":25,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}}}\\\",\\r\\n  \\\"{\\\\\\\"Wo3\\\\\\\":{\\\\\\\"armor\\\\\\\":4,\\\\\\\"armver\\\\\\\":50,\\\\\\\"damage\\\\\\\":25,\\\\\\\"damver\\\\\\\":60,\\\\\\\"health\\\\\\\":30,\\\\\\\"initiative\\\\\\\":4,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":30,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[17,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[17,0],\\\\\\\"active\\\\\\\":1}},\\\\\\\"Hr1\\\\\\\":{\\\\\\\"_ID\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"login\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"unit\\\\\\\":{\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"armor\\\\\\\":0,\\\\\\\"armver\\\\\\\":0,\\\\\\\"damage\\\\\\\":0,\\\\\\\"damver\\\\\\\":0,\\\\\\\"health\\\\\\\":0,\\\\\\\"initiative\\\\\\\":0,\\\\\\\"level\\\\\\\":0,\\\\\\\"max_armor\\\\\\\":10,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damage\\\\\\\":40,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":500,\\\\\\\"warheal\\\\\\\":0,\\\\\\\"warxp\\\\\\\":0,\\\\\\\"xp\\\\\\\":0,\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[1,16],\\\\\\\"active\\\\\\\":1}},\\\\\\\"De2\\\\\\\":{\\\\\\\"armor\\\\\\\":2,\\\\\\\"armver\\\\\\\":40,\\\\\\\"damage\\\\\\\":12,\\\\\\\"damver\\\\\\\":30,\\\\\\\"heal\\\\\\\":10,\\\\\\\"health\\\\\\\":20,\\\\\\\"initiative\\\\\\\":3,\\\\\\\"max_armver\\\\\\\":100,\\\\\\\"max_damver\\\\\\\":100,\\\\\\\"max_health\\\\\\\":20,\\\\\\\"xp_sold\\\\\\\":1,\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"hex\\\\\\\":{\\\\\\\"center\\\\\\\":[0,0],\\\\\\\"coord\\\\\\\":[18,0],\\\\\\\"active\\\\\\\":1}}}\\\"\\r\\n]\",\"USER\":\"admin@admin.ru\",\"DEVIL\":1,\"DOWNLOADS\":6,\"GHOST\":0,\"LEVEL\":1,\"NOWStrange\":0,\"ROBBER\":1,\"STRAGE\":5,\"TYPE\":1,\"WARRIOR\":1,\"WIZARD\":1,\"WOLF\":1,\"XP_SOLD\":0,\"_ID\":16,\"win\":0}]");
            save_inf_local.putUser(Save_inf.REMEMBER_HERO, new Hero(new Unit(), "local", "local", "local", "ru", "Hello~", 20, 1, 5, 20, 250, 5));
        }
        b =save_inf_local.getHero();

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){
            tactic.setTextSize(TypedValue.COMPLEX_UNIT_SP,57);
            internet.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            local.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
        }
        else if(metricsB.widthPixels/metricsB.density>600){

            tactic.setTextSize(TypedValue.COMPLEX_UNIT_SP,52);
            internet.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
            local.setTextSize(TypedValue.COMPLEX_UNIT_SP,37);
        }
        else if(metricsB.widthPixels/metricsB.density>500){

            tactic.setTextSize(TypedValue.COMPLEX_UNIT_SP,47);
            internet.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            local.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
        }
        else if(metricsB.widthPixels/metricsB.density>400){

            tactic.setTextSize(TypedValue.COMPLEX_UNIT_SP,43);
            internet.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
            local.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        else{

           tactic.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
            internet.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            local.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }
}
    //Метод проверки наличии сети интернет
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
        internet.setVisibility(View.GONE);
        return false;
    }

    public void local(View view) {
        //Назначение флагов и имени директории SharedPreference

 save_inf.putTypeGame(false);
 save_inf.putTypeGame(Save_inf.CHILD_FOR_USER_LOCAL);

        startActivity(new Intent(getApplicationContext(),MenuAct.class));
    }

    public void server(View view) {
        //Назначение флагов и имени директории SharedPreference

        save_inf.putTypeGame(true);
        save_inf.putTypeGame(Save_inf.CHILD_PREFERENCES_USER);
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

    @Override
    public void showLonging() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //В случае присутсвия интернет подключения вызывается метод Update_app();
        if (ConnectingToInternet()) {
            loading= new Loading(this);
            loading.show();
            Update_app();

        }

    }
//Метод для проверки обновлений игры
    public void Update_app(){
        //Подключение к Firebase
    auth= FirebaseAuth.getInstance();
    firebaseUser = auth.getCurrentUser();
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    //Прием значения номера последеней версии из директории \"VERSION"
    final DatabaseReference myRef=database.getReference("VERSION");
    valueEventListener= myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            int api_app=Integer.parseInt(dataSnapshot.getValue(String.class));loading.dismiss();
            //Сравнение номеров версий проекта
            if (api_app-apiapp==0){
//Если версия новейшая ,то  идет закачка новых карт с сервера для локального пользователя

                //Закачка карт для пользователя
                MainPreseter mainPreseter1 = new MainPreseter(ChoseTypeGame.this, "SELECT * FROM `SCENARIO2` WHERE " + b.my_scenario());
                mainPreseter1.getDB(Save_inf.SCENARIO_LIST_HOME);

                //Закачка карт для магазина карт
                MainPreseter mainPreseter = new MainPreseter(ChoseTypeGame.this, "SELECT * FROM `SCENARIO` WHERE `USER`='admin@admin.ru' and `LEVEL`<='" + b.getLevel() + "' and not(" + b.my_scenario() + ")");
                mainPreseter.getDB(Save_inf.SCENARIO_LIST_SHOP);





            }
            else {
                //В случае наличия новой версии запускается диалоговое окно с просьбой обновить игру
                //Есть два варианта
                //#Обновить по ссылке url
                //#Продолжить без обновления,при этом не будут закчиваться новые карты и нельзя зайти в сервеную игру
                AlertDialog dialog = new AlertDialog.Builder(ChoseTypeGame.this,R.style.AlertDialogCustom).

                        setTitle(getResources().getString(R.string.update)).setMessage(webaddress).setPositiveButton("Обновить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//Переход пос
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                }).setNegativeButton("Продолжить без обновления", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        internet.setVisibility(View.GONE);
                        Toasty.warning(getApplicationContext(),"Без обновления некоторые функции будут не работать", Toasty.LENGTH_SHORT,true).show();
                        dialog.dismiss();
                        //Убрать кнопку интеренет
                    }
                }).setCancelable(false).show();
                ((TextView) dialog.findViewById(android.R.id.message))
                        .setMovementMethod(LinkMovementMethod.getInstance());

            }
            myRef.removeEventListener(valueEventListener);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}
//Получение мписка сценариев(обновления собственных данных карт с сервера )
    @Override
    public void ResultHome(List<Scenario> scenario) {
        save_inf_local.putListScenario(Save_inf.SCENARIO_LIST_HOME,scenario);

    }
    //Получение мписка сценариев(обновления магазина  данных карт с сервера )
    @Override
    public void ResultShop(List<Scenario> scenario) {
        save_inf_local.putListScenario(Save_inf.SCENARIO_LIST_SHOP,scenario);

    }

    @Override
    public void onGetResult(List<Scenario> scenario) {

    }


    @Override
    public void onError(String localizedMessage) {

    }
}
