        package com.example.tacticalb.class_unit;

import com.google.gson.Gson;

import java.util.HashMap;

        //Класс монстра игры
        public class Devil extends Unit {
            private int initiative;
            private int health;
            private  int damage;
            private int xp_sold;
            private int heal;
            private int armor;
            final private int max_health=20;
            final private int max_damver =100;
            final private int max_armver=100;
            private int armver;
            private int damver;
            Unit unit;

            public Devil(Unit unit, int health, int armor, int damage, int xp_sold,int heal, int armver,int damver, int initiative) {
                super(unit.getCoord(),unit.getHex());
                this.damage=damage;
                this.armor=armor;
                this.health=health;
                this.xp_sold=xp_sold;
                this.initiative = initiative;
                this.heal = heal;
                this.armver =armver;
                this.damver =damver;
                this.unit=unit;
            }

        public String getType(){
                return "Devil";
        }
            public int getArmor() {
                return armor;
            }

            public void setArmor(int armor) {
                this.armor = armor;
            }

            public int getMax_damver() {
                return max_damver;
            }

            public int getMax_armver() {
                return max_armver;
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
            public int getHeal() {
                return heal;
            }

            public void setHeal(int heal) {
                this.heal = heal;
            }

            public int getInitiative() {
                return initiative;
            }

            public void setInitiative(int initiative) {
                this.initiative = initiative;
            }

            public int getHealth() {
                return health;
            }

            public void setHealth(int health) {
                this.health = health;
            }

            public int getDamage() {
                return damage;
            }

            public void setDamage(int damage) {
                this.damage = damage;
            }



            public int getXp_sold() {
                return xp_sold;
            }

            public void setXp_sold(int xp_sold) {
                this.xp_sold = xp_sold;
            }

            @Override
            public String toString() {
                HashMap<String,Object> pop = new HashMap<>();
                pop.put("health",health);
                pop.put("armor",armor);
                pop.put("damage",damage);
                pop.put("xp_sold",xp_sold);
                pop.put("armver",armver);
                pop.put("damver",damver);
                pop.put("initiative",initiative);
                pop.put("heal",heal);
                //pop.put("unit",new Gson().toJson(unit));

                return new Gson().toJson(pop)
                        .toString();

            }
        }

