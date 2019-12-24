package com.example.tacticalb.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacticalb.Adapter.InfpAdapter;
import com.example.tacticalb.DirectoryItem;
import com.example.tacticalb.R;

import java.util.ArrayList;
import java.util.List;

public class Directory extends Fragment {
RecyclerView recyclerView;
InfpAdapter infpAdapter;
List<DirectoryItem> directoryItem;
String [] info_title;
String [] info_mas;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.directory,container,false);
        info_title=getResources().getStringArray(R.array.info_title);
        info_mas=getResources().getStringArray(R.array.info_message);
        directoryItem= new ArrayList<>();
getList();
        recyclerView=view.findViewById(R.id.direct);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        infpAdapter=new InfpAdapter(directoryItem ,getContext() );
        infpAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(infpAdapter);



        return view;
    }

 void getList(){
        directoryItem.add(new DirectoryItem(R.drawable.defense,info_mas[0],info_title[0]));
        directoryItem.add(new DirectoryItem(R.drawable.attack,info_mas[1],info_title[1]));
        directoryItem.add(new DirectoryItem(R.drawable.knife,info_mas[2],info_title[2]));
        directoryItem.add(new DirectoryItem(R.drawable.blocking,info_mas[3],info_title[3]));
        directoryItem.add(new DirectoryItem(R.drawable.hero1,info_mas[4],info_title[4]));
        directoryItem.add(new DirectoryItem(R.drawable.warrior1,info_mas[5],info_title[5]));
        directoryItem.add(new DirectoryItem(R.drawable.castle,info_mas[6],info_title[6]));
        directoryItem.add(new DirectoryItem(R.drawable.war,info_mas[7],info_title[7]));
        directoryItem.add(new DirectoryItem(R.drawable.xpfoe,info_mas[8],info_title[8]));
        directoryItem.add(new DirectoryItem(R.drawable.heart,info_mas[9],info_title[9]));

 }
}
