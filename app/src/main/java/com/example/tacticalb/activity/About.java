package com.example.tacticalb.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.widget.TextView;


import com.example.tacticalb.R;

public class About extends AppCompatActivity {
MediaPlayer mediaPlayer;

    TextView textView1,textView2,textView3;
/*
#Основная информация о проекте и разработчиках






 */
    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }
//Ставрт медиаплеера
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGame);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        mediaPlayer=MediaPlayer.create(this, R.raw.gameofthrones);
        mediaPlayer.setLooping(true);
        textView1= findViewById(R.id.textView3);
        textView2= findViewById(R.id.textView2);
        textView3= findViewById(R.id.textView5);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        if(metricsB.widthPixels/metricsB.density>700){
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
            textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        }
        else if(metricsB.widthPixels/metricsB.density>600){
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
            textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP,44);
        }
        else if(metricsB.widthPixels/metricsB.density>500){
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
            textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,39);
        }
        else if(metricsB.widthPixels/metricsB.density>400){
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
            textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
        }
        else{
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);

        }

    }
//отключение проигрывателя
    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }
}
