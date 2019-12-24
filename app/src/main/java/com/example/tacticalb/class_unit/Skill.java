package com.example.tacticalb.class_unit;
//Класс навыков игрока в игре
public class Skill  {
    private String type;
    private int xp_skill;
    private int level_skill;
    private int izm;


    public Skill(String type, int xp_skill, int level_skill,int izm) {
        this.type = type;
        this.xp_skill = xp_skill;
        this.level_skill = level_skill;
        this.izm=izm;

    }

    public int getIzm() {
        return izm;
    }

    public void setIzm(int izm) {
        this.izm = izm;
    }

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    public int getXp_skill() {
        return xp_skill;
    }

    public void setXp_skill(int xp_skill) {
        this.xp_skill = xp_skill;
    }

    public int getLevel_skill() {
        return level_skill;
    }

    public void setLevel_skill(int level_skill) {
        this.level_skill = level_skill;
    }



    @Override
    public String toString() {
        return type +
                " " + xp_skill +
                " " + level_skill +" "+izm;
    }
}
