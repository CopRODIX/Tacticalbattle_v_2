package com.example.tacticalb.class_unit;


import com.example.tacticalb.Hex;

//Суперкласс для монстров игры
public class Unit {
    private int[] coord = new int[2];
    private Hex hex;

    public Unit(int[] coord, Hex hex) {
        this.coord = coord;
        this.hex = hex;
    }

    public Unit() {
    }

    public Hex getHex() {
        return hex;
    }

    public void setHex(Hex hex) {
        this.hex = hex;
    }

    public int[] getCoord() {
         return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }
}
