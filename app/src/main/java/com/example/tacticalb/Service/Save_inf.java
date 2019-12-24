package com.example.tacticalb.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tacticalb.ClassforServer.Scenario;
import com.example.tacticalb.Hex;
import com.example.tacticalb.R;
import com.example.tacticalb.class_unit.Devil;
import com.example.tacticalb.class_unit.Hero;
import com.example.tacticalb.class_unit.Robber;
import com.example.tacticalb.class_unit.Unit;
import com.example.tacticalb.class_unit.Warrior;
import com.example.tacticalb.class_unit.Wizard;
import com.example.tacticalb.class_unit.Wolf;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Save_inf {
    //Всопогательный класс для сохранения информации в  SharedPreferences
    private SharedPreferences preferences;
    private SharedPreferences preferencesser;
    public static String CONNECT_DIALOG_FOR_PARAM_TYPE="paramtype";
    public static String RESUME_HERO_LOST_GAME="herotop";
    public static String RESUME_GAME="resume";
    public static String RESUME_SCENARIO_LOST_GAME="scenario";
    public static String REMEMBER_FOR_LOGIN="remember";
    public static String REMEMBER_FOR_URL_AVATAR="Url_foto";
    public static String REMEMBER_HERO="hero";
    public static String REMEMBER_BOOLEAN_FOR_ARGUMENTS="rememb";
    public static String CHILD_FOR_USER_LOCAL="local";
    public static String SER_LOC="SER_LOC";
    public static String CHILD_PREFERENCES="child";
    public static String CHILD_PREFERENCES_USER="user";
    public static String SCENARIO_LIST_HOME="listscenariohome";
    public static String SCENARIO_LIST_SHOP="listscenarioshop";
Context context;



    public Save_inf(Context appContext ) {
        this.context=appContext;
        //Общее хранилище
        preferencesser=PreferenceManager.getDefaultSharedPreferences(appContext);
        //Открытие директорий
        preferences = appContext.getSharedPreferences(getTypeGameChild(),Context.MODE_PRIVATE);

    }


    public Save_inf(Context appContext ,String child) {
        preferences = appContext.getSharedPreferences(child,Context.MODE_PRIVATE);

    }


    public String getTypeGameChild(){
        return preferencesser.getString(CHILD_PREFERENCES,null);
    }
//Toast для показа обнрвления уровня
    public void ToastnewLevel() {
        Toast toast = Toast.makeText(context, context.getResources().getString(R.string.congratulate) + " !\n" + context.getResources().getString(R.string.nextlevel) + (getHero().getLevel()) + " !\n" + context.getResources().getString(R.string.newmaps), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.WHITE);
        v.setGravity(Gravity.CENTER);
        toastContainer.setBackgroundColor(Color.BLUE);
        toast.show();
    }
    //True-онлайн игра,false -локальная версия
public boolean getTypeGame(){
        return preferencesser.getBoolean(SER_LOC,true);
}
//Закачивание в виде json в SharedPreference Списка карт/сценариев
public void putListScenario(String type,List<Scenario> list){
        Gson gson= new Gson();
      preferences.edit().putString( type, gson.toJson(list)).apply();
}
//Получение из SharedPreference списка сценариев
public List<Scenario> getListScenario(String type){
        Type listType= new TypeToken<List<Scenario>>(){}.getType();
        return new Gson().fromJson(getString(type),listType);
}
//НАзначение типа игры
public void putTypeGame(Boolean server){
        preferencesser.edit().putBoolean(SER_LOC,server).apply();
}
//Определенние директории SharedPreference
public void putTypeGame(String child){
        preferencesser.edit().putString(CHILD_PREFERENCES,child).apply();
    }
//Получение значение int по ключу key
    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }


    //Получение значение float по ключу key
    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }
    //Получение значение double по ключу key
    public double getDouble(String key, double defaultValue) {
        String number = getString(key);

        try {
            return Double.parseDouble(number);

        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
//Обновление параметров героя в Sharedpreference
public void updateParHero(String type,Object value){
    String s="";
    int vl = 0;
    Hero hero=getHero();
if (type.equals(Hero.Type_login)||type.equals(Hero.Type_mymap)||type.equals(Hero.Type_uri_piu)){
    s=value.toString();
}
else vl=Integer.parseInt(value.toString());

switch (type){
    case Hero.Type_armor:hero.setArmor(vl);break;
    case Hero.Type_damage:hero.setDamage(vl);break;
    case Hero.Type_health:hero.setHealth(vl);break;
    case Hero.Type_level:hero.setLevel(vl);break;
    case Hero.Type_login:hero.setLogin(s);break;
    case Hero.Type_mymap:hero.setMy_scenario(s);break;
    case Hero.Type_uri_piu:break;
    case Hero.Type_xp:hero.setXp(vl);break;
}
    if (hero.getXp() > hero.getNextXp())
    {
        ToastnewLevel();
        hero.setLevel(hero.getLevel()+1);
    }
putUser(REMEMBER_HERO,hero);
}
//Добавление новых карт в список карт Sharedprefence
public void addScenariotoList(Scenario scenario){
        String json=getString(Save_inf.SCENARIO_LIST_HOME);
        String l=new Gson().toJson(scenario);

        preferences.edit().putString(SCENARIO_LIST_HOME, json.substring(0,json.length()-1)+","+l+"]").apply();
}
    public String getString(String key) {         return preferences.getString(key, "");
    }

//Получение из SharedPreference списка из строк
    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }


    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }
    //Получение из SharedPreference списка из int
    public ArrayList<Integer> getListInt(String key) {
        String[] split = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> splitList = new ArrayList<String>(Arrays.asList(split));
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (String item : splitList)
            newList.add(Integer.parseInt(item));

        return newList;
    }

    public List<ArrayList<Unit>> getListUn(String value){

        List<ArrayList<Unit>> pop= new ArrayList<>();
        String [] pp=TextUtils.split(value,"~");
        for (String p:pp) {
            ArrayList<Unit> newList = new ArrayList<>();

            String[] split = p.split("@");
            for (int i = 1; i <split.length ; i++) {
                String [] split1= split[i].split("-");String [] ko=split1[1].split(" ");
                int x=Integer.parseInt(ko[ko.length-2]);
                int y=Integer.parseInt(ko[ko.length-1]);
                Unit unit= new Unit(new int[]{x,y},new Hex(new int[]{x,y},1));
                switch (split1[0]) {
                    case "Hero":
                        newList.add(new Hero(unit, ko[0],ko[1],Integer.parseInt(ko[2]),Integer.parseInt(ko[3]),Integer.parseInt(ko[4]),Integer.parseInt(ko[5]),Integer.parseInt(ko[6]),Integer.parseInt(ko[7])));        break;
                    case "Warrior":
                        newList.add(new Warrior(unit,Integer.parseInt(ko[0]),Integer.parseInt(ko[1]),Integer.parseInt(ko[2]),Integer.parseInt(ko[3]),Integer.parseInt(ko[4]),Integer.parseInt(ko[5]),Integer.parseInt(ko[6])));
                        break;
                    case "Wolf":
                        newList.add(new Wolf(unit,Integer.parseInt(ko[0]),Integer.parseInt(ko[1]),Integer.parseInt(ko[2]),Integer.parseInt(ko[3]),Integer.parseInt(ko[4]),Integer.parseInt(ko[5]),Integer.parseInt(ko[6])));
                        break;
                    case "Devil":
                        newList.add(new Devil(unit,Integer.parseInt(ko[0]),Integer.parseInt(ko[1]),Integer.parseInt(ko[2]),Integer.parseInt(ko[3]),Integer.parseInt(ko[4]),Integer.parseInt(ko[5]),Integer.parseInt(ko[6]),Integer.parseInt(ko[7])));

                        break;
                    case "Robber":
                        newList.add(new Robber(unit,Integer.parseInt(ko[0]),Integer.parseInt(ko[1]),Integer.parseInt(ko[2]),Integer.parseInt(ko[3]),Integer.parseInt(ko[4]),Integer.parseInt(ko[5]),Integer.parseInt(ko[6])));
                        break;
                    case "Wizard":
                        newList.add(new Wizard(unit,Integer.parseInt(ko[0]),Integer.parseInt(ko[1]),Integer.parseInt(ko[2]),Integer.parseInt(ko[3]),Integer.parseInt(ko[4]),Integer.parseInt(ko[5]),Integer.parseInt(ko[6])));
                        break; }

            }
            pop.add(newList);
        }
        return pop;
    }
    public List<ArrayList<Unit>> getListUnit(String key){
        return getListUn(preferences.getString(key,""));}
    public Hex[][] getfield(String key){
        Hex[][] field= new Hex[20][20];
        String [] split_I =TextUtils.split(preferences.getString(key,""),"@");
        for (int i = 0; i <split_I.length ; i++) {
            String [] split_j=split_I[i].split("-");
            for (int j = 1; j < split_j.length; j++) {
                String[]split=split_j[j].split(" ");

                field[i][j-1]=new Hex(new  int []{Integer.parseInt(split[1]),Integer.parseInt(split[2])},Integer.parseInt(split[3]));
            }
        }


        return field;
    }
    public int [][] getArInt(String value){
        String [] strings= TextUtils.split(value,"-");
        int [][] avtiv = new int[20][20];

        for (int i = 0; i < strings.length-1; i++) {
            String [] split_J= strings[i].split(" ");
            for (int j = 0; j < split_J.length; j++) {
                avtiv[i][j]=Integer.parseInt(split_J[j]);
            }
        }
        return avtiv;
    }
    public int [][] getIIn(String key){
        return getArInt(preferences.getString(key,""));
    }
    public void putInt(String key, int value) {
        key_null(key);
        preferences.edit().putInt(key, value).apply();
    }

    public void key_null(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }
    public void putListInt(String key, ArrayList<Integer> intList) {
        key_null(key);
        Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myIntList)).apply();
    }

    public void  putUser(String key,Hero hero){
        SharedPreferences.Editor editor=preferences.edit(); Gson gson=new Gson();
        String json=gson.toJson(hero);
        editor.putString(key,json);
        editor.apply();
    }
    public String putTypelist_item(ArrayList<Unit> listunit){
        LinkedHashMap<String,String> hap = new LinkedHashMap<>();
        int i=0;
        for (Unit unit:listunit) {
            String name="";
            i+=1;
            if (unit instanceof Devil)
                name="De"+String.valueOf(i);
            else
            if (unit instanceof Hero)
                name="Hr"+String.valueOf(i);
            else
            if (unit instanceof Robber)
                name="Rb"+String.valueOf(i);
            else
            if (unit instanceof Warrior)
                name="Wa"+String.valueOf(i);
            else
            if (unit instanceof Wizard)
                name="Wi"+String.valueOf(i);
            else
            if (unit instanceof Wolf)
                name="Wo"+String.valueOf(i);
           String s=unit.toString();
           int [] jk=unit.getHex().getCoord();
s=s.substring(0,s.length()-1)+",\"x\":"+jk[0]+",\"y\":"+jk[1]+"}";
                   hap.put(name,s);
        }

        return new Gson().toJson(hap).toString();
    }
    public void putListScenario(List<ArrayList<Unit>> typelist, String key){

        List<String> data = new ArrayList<>(); GsonBuilder gsonBuilder= new GsonBuilder();
        Gson gson=gsonBuilder.create();
        for (ArrayList<Unit> listunit:typelist) {

            data.add(putTypelist_item(listunit));
        }
        //HashMap<String,Unit> data= new HashMap<>();



        preferences.edit().putString(key,putListString(data)).apply();
    }
    public String putListString(List<String> data){
        Gson gson = new Gson();
        return  gson.toJson(data);
    }
    public void putScenario(Scenario scenario,String key){
        Gson gson=new Gson();
        String json=gson.toJson(scenario);
        preferences.edit().putString(key,json).apply();
    }
    public List<String> getListStrang(String json){

        Type type=new TypeToken<List<String>>(){}.getType();
        List<String> op = new Gson().fromJson(json,type);
        return op;
    }

    public List<ArrayList<Unit>> getTypeList(String json){
        List<ArrayList<Unit>> lists= new ArrayList<>();
        List<String> op =  getListStrang(json);
        for (String json_hash:op
        ) {

            lists.add(getUnit(json_hash));
        }


        return lists;
    }
    public List<ArrayList<Unit>> getTypeList_key(String key){


        return getTypeList(getString(key));
    }
    public ArrayList<Unit>          getUnit(String json_hash){
        ArrayList<Unit> units= new ArrayList<>();
        Gson gson= new Gson();
        LinkedHashMap<String,Object> pp=new Gson().fromJson(json_hash,new TypeToken<LinkedHashMap<String,Object>>(){}.getType());
        for(Map.Entry<String, Object> entry : pp.entrySet()) {
            String value = entry.getValue().toString();
            try {
                JSONObject jsonObject= new JSONObject(value);
                int x=jsonObject.getInt("x");
                int y=jsonObject.getInt("y");
                Unit unit= new Unit(new int[]{x,y},new Hex(new int[]{x,y},1));
            switch (entry.getKey().substring(0,2)){
                case "De":
                    units.add(new Devil(unit,jsonObject.getInt("health"),jsonObject.getInt("armor"),jsonObject.getInt("damage"),jsonObject.getInt("xp_sold"),jsonObject.getInt("heal"),jsonObject.getInt("armer"),jsonObject.getInt("damver"),jsonObject.getInt("initiative")));
                    //units.add(gson.fromJson(value,Devil.class));
                    break;
                case "Hr":
                    units.add(new Hero(unit,jsonObject.getInt("initiative")));
                    //units.add(gson.fromJson(value,Hero.class));break;
                    break;
                case "Rb":
                    units.add(new Robber(unit,jsonObject.getInt("health"),jsonObject.getInt("armor"),jsonObject.getInt("damage"),jsonObject.getInt("xp_sold"),jsonObject.getInt("armver"),jsonObject.getInt("damver"),jsonObject.getInt("initiative")));
                    break;
                    //units.add(gson.fromJson(value,Robber.class));break;
                case "Wa":
                    units.add(new Warrior(unit,jsonObject.getInt("health"),jsonObject.getInt("armor"),jsonObject.getInt("damage"),jsonObject.getInt("xp_sold"),jsonObject.getInt("armver"),jsonObject.getInt("damver"),jsonObject.getInt("initiative")));
                    //units.add(gson.fromJson(value,Warrior.class));break;
                    break;
                case "Wo":
                    units.add(new Wolf(unit,jsonObject.getInt("health"),jsonObject.getInt("armor"),jsonObject.getInt("damage"),jsonObject.getInt("xp_sold"),jsonObject.getInt("armver"),jsonObject.getInt("damver"),jsonObject.getInt("initiative")));
                    //units.add(gson.fromJson(value,Wolf.class))
                    break;
                case "Wi":
                    units.add(new Wizard(unit,jsonObject.getInt("health"),jsonObject.getInt("armor"),jsonObject.getInt("damage"),jsonObject.getInt("xp_sold"),jsonObject.getInt("armver"),jsonObject.getInt("damver"),jsonObject.getInt("initiative")));
                    //units.add(gson.fromJson(value,Wizard.class));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
        return units;
    }
    public Scenario getscenario(String key){
        Gson gson=new Gson();
        String json=preferences.getString(key,null);
        Type type=new TypeToken<Scenario>(){}.getType();
        Scenario scenario=gson.fromJson(json,type);
        return scenario;

    }
    public Hero getHero(){
        return getHero("hero");
    }
    public Hero getHero(String key){
        Gson gson=new Gson();
        String json=preferences.getString(key,null);
        Type type=new TypeToken<Hero>(){}.getType();
        Hero  hero=gson.fromJson(json,type);
        return hero;
    }
    /* public void putUser(String key, Hero hero){
         key_null(key);
         preferences.edit().putString(key,hero.toString_Hero()).apply();

     }/*
    /* public Hero getHero(String key){
         String [] split =preferences.getString(key,"").split("#");
         Hero hero= new Hero(new Unit(),split[0],split[1],split[2],split[3],split[4],Integer.parseInt(split[5]),Integer.parseInt(split[6]),Integer.parseInt(split[7]),Integer.parseInt(split[8]),Integer.parseInt(split[9]),Integer.parseInt(split[10]));
     return hero;
     }*/
    public void putListUnit(String key,ArrayList<Unit> unitList){
        key_null(key);
        String  strings="";
        for (Unit o:unitList) {
            strings+=o.toString()+o.getHex().toString();

        }
        strings+="~";
        preferences.edit().putString(key,strings).apply();
    }
    public String toStrTypelist(ArrayList<Unit> unitList){
        String  strings="";
        for (Unit o:unitList) {
            strings+=o.toString()+o.getHex().toString();

        }
        strings+="~";
        return strings;

    }
    public String connectTypelist(String[] ko){
        String s="";
        for (String i:ko
        ) {
            s+=i;
        }
        return s;
    }
    public  String getStringActivity(int [][] ints){
        String string="";
        for (int i = 0; i <ints.length ; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                string=string+ints[i][j]+" ";
            }
            string+="-";
        }
        return string;
    }
    public void putIIn (String key,int [][] ints){
        key_null(key);
        String string="";
        for (int i = 0; i <ints.length ; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                string=string+ints[i][j]+" ";
            }
            string+="-";
        }
        preferences.edit().putString(key,string).apply();
    }
    public  void putListHex(String key , Hex[][] hexes){
        key_null(key);
        String string="-";
        for (int i = 0; i <hexes.length ; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                string+=hexes[i][j].toSrt();
            }
            string+="@-";
        }
        preferences.edit().putString(key,string).apply();
    }

    public void putFloat(String key, float value) {
        key_null(key);
        preferences.edit().putFloat(key, value).apply();
    }
    public void putString(String key, String value) {
        key_null(key);
        preferences.edit().putString(key, value).apply();
    }


    public void putBoolean(String key, boolean value) {
        key_null(key);
        preferences.edit().putBoolean(key, value).apply();
    }


    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }


    public void clear() {
        preferences.edit().clear().apply();
    }


}
