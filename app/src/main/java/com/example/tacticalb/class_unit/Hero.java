package com.example.tacticalb.class_unit;


import com.example.tacticalb.Hex;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;

public class Hero extends Unit implements Serializable {
public static final String Type_armor="armor";
public static final String Type_damage="damage";
public static final String Type_health="health";
public static final String Type_level="level";
public static final String Type_login="login";
public static final String Type_mymap="mymap";
public static final String Type_xp="xp";
public static final String Type_uri_piu="uri_pic";



    private String _ID;
    private String my_scenario;
    private String login;
    private String language;


    private int health;
    private int xp;
    private  int damage;
    private int armor;
    private  int level;
    private String email;
    private int initiative;
    private Unit unit;
    final private int max_health=500;
    final private int max_damage=40;
    final private int max_armor=10;



    private int warheal;
    final private int max_damver =100;
    final private int max_armver=100;
    private int warxp;
    private int armver;
    private int damver;
public void  updateXP(int sum){
    xp-=sum;
}
    public int getWarheal() {
        return warheal;
    }

    public void setWarheal(int warheal) {
        this.warheal = warheal;
    }

    public int getWarxp() {
        return warxp;
    }

    public void setWarxp(int warxp) {
        this.warxp = warxp;
    }

    public int getArmver() {
        return armver;
    }

    public void setArmver(int armver) {
        this.armver = armver;
    }
    public int getDamver() {
        return damver;
    }

    public void setDamver(int damver) {
        this.damver = damver;
    }

    public int getMax_health() {
        return max_health;
    }

    public int getMax_damage() {
        return max_damage;
    }

    public int getMax_armor() {
        return max_armor;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getMax_damver() {
        return max_damver;
    }

    public int getMax_armver() {
        return max_armver;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getArmor() {
        return armor;
    }
    public Hero(Unit unit, int initiative) {
        super(unit.getCoord(),unit.getHex());
        this.unit=unit;
        this.initiative = initiative;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
public int getNextXp(){
        int pow =(int) Math.pow(level+1,5)    ;
        return pow;
    }

    public Hero(int[] coord, Hex hex, String _ID, String my_scenario, int health, int xp, int damage, int armor, int level, int initiative, Unit unit) {
        super(coord, hex);
        this._ID = _ID;
        this.my_scenario = my_scenario;
        this.health = health;
        this.xp = xp;
        this.damage = damage;
        this.armor = armor;
        this.level = level;
        this.initiative = initiative;

        this.unit = unit;
    }




    public Hero(Unit unit, String _ID, String login, int xp, int level, int armor, int damage, int health, int initiative) {
        super(unit.getCoord(),unit.getHex());
        this.health = health;
        this.xp = xp;
        this._ID=_ID;
        this.my_scenario=my_scenario;
        this.login=login;
        this.unit=unit;
        this.damage = damage;
        this.level = level;
        this.armor = armor;

        this.initiative = initiative;

    }



    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
    public Hero( String _ID,  int health, int damage, int armor, int level, int armver,int damver,int warxp, int warheal,int initiative) {

        this._ID = _ID;
        this.health = health;
        this.damage = damage;
        this.armor = armor;
        this.level = level;
        this.initiative = initiative;
        this.armver =armver;
        this.damver =damver;
        this.warxp = warxp;
        this.warheal = warheal;




    }
    public String getType(){
        return "Hero";
    }
    @Override
    public String toString() {
        HashMap<String,Object> pop = new HashMap<>();
        pop.put("initiative",initiative);
      // pop.put("unit",new Gson().toJson(unit));

        return new Gson().toJson(pop)
                .toString();

    }
    public void Hero_param_update(Hero hero){
        warxp=hero.getWarxp();
        warheal=hero.getWarheal();
        damver=hero.getDamver();
        armver=hero.getArmver();
    }
    public Hero(Unit unit, String  _ID, String login, String email, String language, String my_scenario, int xp, int level, int armor, int damage, int health, int initiative) {
        super(unit.getCoord(),unit.getHex());
        this.health = health;
        this.xp = xp;
        this._ID=_ID;
        this.email=email;
        this.my_scenario=my_scenario;
        this.login=login;
        this.unit=unit;
        this.damage = damage;
        this.language=language;
        this.level = level;
        this.armor = armor;

        this.initiative = initiative;

    }

    public int getLevel() {
        return level;
    }
    public String my_scenario(){
        String [] split=my_scenario.split("~");
        String otvet="`NAME`= '"+split[0]+"'";
        for (int i = 1; i <split.length ; i++) {
            otvet=otvet+"or `NAME`= '"+split[i]+"'";
        }
        return otvet;
           }
    public String toString_Hero() {
        return
                _ID+
                "#"+login+
                        "#"+email+
                        "#"+language+
                "#"+my_scenario+
                "#"+ xp +
                "#" + level +
                "#" + armor +
                "#" + damage +
                "#" + health +
                "#" + initiative+"#" ;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMy_scenario(String my_scenario) {
        this.my_scenario = my_scenario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getMy_scenario() {
        return my_scenario;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
