package com.example.tacticalb;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Hex {
    //Класс клеток игрового поля

    private int[] center = new int[2];
    private int [] coord = new int [2];
    private int active;

    public Hex() {
        active=0;
        center=new int[]{0,0};
        center=new int[]{0,0};
    }

    public Hex(int[] coord, int active) {
        this.coord = coord;
        this.active = active;
    }
public String toSrt(){
        return  " "+String.valueOf(coord[0]) +" "+
                String.valueOf(coord[1])+" "+String.valueOf(active)+
                " -";
}
    @Override
    public String toString() {
        return
                " "+String.valueOf(coord[0]) +" "+
                        String.valueOf(coord[1])+
                        " -";
    }


    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }



    public int[] getCenter() {
        return center;
    }

    public void setCenter(int[] center) {
        this.center = center;
    }

    public int [][] Findpeaks(int r, int [] center){
        int [][] peaks = new int [6][2];
  for (int i = 0; i < 6; i++) {
            peaks[i][0] = (int) (center[0]+cos(PI*i/3)*r);
            peaks[i][1] = (int) (center[1]+sin(PI*i/3)*r);
        }
        return  peaks;
    }

    public void DrawHex(Canvas canvas, Paint paint, int[][] peaks){
        for (int i = 0; i < 6; i++) {
            if (i!=5)
                canvas.drawLine(peaks[i][0],peaks[i][1],peaks[i+1][0],peaks[i+1][1],paint);
            else
                canvas.drawLine(peaks[5][0],peaks[5][1],peaks[0][0],peaks[0][1],paint);

        }
    }

    public int Checkhex(int x,int y,int[][]peaks,int[]k,int[]l){
        int m = 1;
        for (int i = 0; i < 6; i++) {
            if (i !=5){
                k[i] = (peaks[i+1][1]-peaks[i][1])/(peaks[i+1][0]-peaks[i][0]);
                l[i]=-(k[i]*peaks[i][0])+peaks[i][1];
            }
            else {
                k[i] = (peaks[0][1]-peaks[5][1])/(peaks[0][0]-peaks[5][0]);
                l[i]=-(k[i]*peaks[5][0])+peaks[5][1];
            }
        }
        for (int i = 0; i < 6; i++) {
            if(i<3){
                if (y > k[i]*x+l[i]){
                    m=0;
                }
            }
            else{
                if (y < k[i]*x+l[i]){
                    m=0;
                }
            }
        }
        return m;
    }
    public void drawHexPath(Canvas canvas, Paint paint,int[][]peaks) {
        Path hexPath = new Path();
        hexPath.reset();
        hexPath.moveTo(peaks[0][0], peaks[0][1]);
        for (int i=1; i<6; i++)
            hexPath.lineTo(peaks[i][0], peaks[i][1]);
        hexPath.close();
        canvas.drawPath(hexPath, paint);
    }

}
