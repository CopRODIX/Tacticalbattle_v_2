package com.example.tacticalb.ClassforServer;

public class Otvet {
        //Класс для получения данных о пользователях из  Firebase Database Realtime
String myid;
String armor;
String damage;
String email;
String health;
String login;
String mymap;
String online;
String xp;
String level;
String language;
String uri_pic;

        public String getURI_pic() {
                return uri_pic;
        }

        public void setURI_pic(String URI_pic) {
                this.uri_pic = URI_pic;
        }

        public Otvet( String armor, String damage, String email, String health,String language,  String level,String login, String mymap, String online, String uri_pic, String xp,  String myid) {
                this.myid =myid;
                this.armor = armor;
                this.damage = damage;
                this.email = email;
                this.health =health;
                this.login = login;
                this.mymap = mymap;
                this.online = online;
                this.xp = xp;
                this.level = level;
                this.language = language;
                this.uri_pic = uri_pic;
                this.myid = myid;
        }
public Otvet(){}

        public String getLANGUAGE() {
                return language;
        }

        public String getLEVEL() {
                return level;
        }

        public String getMyid() {
                return myid;
        }

        public void setARMOR(String armor) {
                this.armor = armor;
        }

        public void setDAMAGE(String damage) {
                this.damage = damage;
        }

        public void setEMAIL(String email) {
                this.email = email;
        }

        public void setHEALTH(String health) {
                this.health = health;
        }

        public void setLOGIN(String login) {
                this.login = login;
        }

        public void setMymap(String mymap) {
                this.mymap = mymap;
        }

        public void setONLINE(String online) {
                this.online = online;
        }

        public void setXP(String xp) {
                this.xp = xp;
        }

        public void setLEVEL(String level) {
                this.level = level;
        }

        public void setLANGUAGE(String language) {
                this.language = language;
        }



        public void setMyid(String myid) {
                this.myid = myid;
        }



        public String getARMOR() {
                return armor;
        }

        public String getDAMAGE() {
                return damage;
        }

        public String getEMAIL() {
                return email;
        }

        public String getHEALTH() {
                return health;
        }

        public String getLOGIN() {
                return login;
        }

        public String getMymap() {
                return mymap;
        }

        public String getONLINE() {
                return online;
        }

        public String getXP() {
                return xp;
        }

}
