package com.example.tacticalb.dialog;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.tacticalb.R;

public class FrCon extends Fragment {
    VideoView videoPlayer;
    Spinner spinner;
    TextView infor;
    String [] info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.conrol, null);

spinner=view.findViewById(R.id.spinner);
infor=view.findViewById(R.id.info);

getList();
       info=getResources().getStringArray(R.array.info);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            change(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
        videoPlayer = (VideoView) view.findViewById(R.id.videoView);


        return view;
    }

    private void change(int position) {
        String s="android.resource://" + getActivity().getPackageName() + "/";
        switch (position){
            case 0:
                s+=R.raw.move;
                infor.setText(info[0]);
                break;
            case 1:
                s+=R.raw.route;
                infor.setText(info[1]);
                break;
            case 2:
                s+=R.raw.info;
                infor.setText(info[2]);
                break;

            case 3:s+=R.raw.pumping;
                infor.setText(info[3]);
                break;

        }
        Uri myVideoUri = Uri.parse( s);
        videoPlayer.setVideoURI(myVideoUri);


        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoPlayer.start();
    }

    public void getList(){
     String [] list = new  String[]{"Ход","Построение Маршрута","Дополненительная информация","Прокачка боевых праметров"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_item,list);

spinner.setAdapter(arrayAdapter);
        spinner.setPrompt("ndnfn");


    }
}