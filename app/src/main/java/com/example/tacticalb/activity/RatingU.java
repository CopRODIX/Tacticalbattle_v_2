package com.example.tacticalb.activity;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tacticalb.Adapter.UserAdapter;
import com.example.tacticalb.ClassforServer.Otvet;
import com.example.tacticalb.R;
import com.example.tacticalb.dialog.Loading;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RatingU extends AppCompatActivity {
    /*
    #Активность для рейтинга пользователей
    ##Comparator<Otvet> сортирует и выстраивает рейтинг игроков
     */
    MediaPlayer mediaPlayer;

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    ValueEventListener eventListener;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    private List<Otvet> mUsers;  DatabaseReference reference;
 Loading loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer=MediaPlayer.create(this, R.raw.gameofthrones);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.6f,0.6f);
        setTheme(R.style.AppThemeGame);
        setContentView(R.layout.rating_activity);
        recyclerView=findViewById(R.id.user_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mUsers=new ArrayList<>();
        read();




    }
//Метод для закачивания информации пользователей из Firebase Database
    private void read() {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("USER");
       loading= new Loading(this);
        loading.show();
        eventListener= reference.addValueEventListener(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {mUsers.clear();
                                                               for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                                                   Otvet otvet=snapshot.getValue(Otvet.class) ;
                                                                   mUsers.add(otvet);

                                                               }
                                                               reference.removeEventListener(eventListener);
                                                               //Сортирование списка пользователей
                                                               Collections.sort(mUsers, new Comparator<Otvet>() {
                                                                           @Override
                                                                           public int compare(Otvet o1, Otvet o2) {
                                                                               int comp=0;
                                                                               if (Integer.parseInt(o1.getLEVEL())<Integer.parseInt(o2.getLEVEL()))
                                                                                   comp=1;
                                                                               else{ if (Integer.parseInt(o1.getLEVEL())>Integer.parseInt(o2.getLEVEL()))
                                                                                   comp=-1;
                                                                               else {


                                                                                   if (Integer.parseInt(o1.getXP())<Integer.parseInt(o2.getXP()))
                                                                                       comp=1;
                                                                                   else if (Integer.parseInt(o1.getXP())>Integer.parseInt(o2.getXP()))
                                                                                       comp=-1;
                                                                                   else
                                                                                   if (Integer.parseInt(o1.getHEALTH())<Integer.parseInt(o2.getHEALTH()))
                                                                                       comp=1;
                                                                                   else if (Integer.parseInt(o1.getHEALTH())>Integer.parseInt(o2.getHEALTH()))
                                                                                       comp=-1;
                                                                                   else  comp=o2.getLOGIN().compareTo(o1.getLOGIN());
                                                                               }

                                                                               }
                                                                               return comp;
                                                                           }

                                                                       }
                                                               );
                                                               userAdapter=new UserAdapter(getApplicationContext(),mUsers);
                                                               recyclerView.setAdapter(userAdapter);
                                                               loading.dismiss();

                                                           }

                                                           @Override
                                                           public void onCancelled(@NonNull DatabaseError databaseError) {
                                                           }
                                                       }
        );


    }  @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }
}
