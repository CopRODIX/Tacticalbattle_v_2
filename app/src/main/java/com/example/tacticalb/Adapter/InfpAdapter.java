package com.example.tacticalb.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacticalb.DirectoryItem;
import com.example.tacticalb.R;

import java.util.List;

public class InfpAdapter extends RecyclerView.Adapter<InfpAdapter.RecyclerViewAdapter> {
    /*
      #Адаптер для показа Cardview карт(Scenario)
       */
    List<DirectoryItem> directoryItems;
    Context context;


    public InfpAdapter(List<DirectoryItem> directoryItems, Context context) {
        this.directoryItems = directoryItems;
        this.context = context;

    }

    @NonNull
    @Override
    public InfpAdapter.RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.directory_item,viewGroup,false);

        return new InfpAdapter.RecyclerViewAdapter(view );
    }

    @Override
    public void onBindViewHolder(@NonNull InfpAdapter.RecyclerViewAdapter holder, int i) {
        DirectoryItem directory=directoryItems.get(i);
        holder.termin.setText( directory.getTitle());
        holder. poredel.setText(directory.getMassege() );
        holder. imageView.setImageResource(directory.getId()    );
    }




    @Override
    public int getItemCount() {
        return directoryItems.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder   {
        TextView termin,poredel;
        ImageView imageView;


        RecyclerViewAdapter(@NonNull View itemView ) {

            super(itemView);
           termin=itemView.findViewById(R.id.termin);
            poredel= itemView.findViewById(R.id.opr);
           imageView=itemView.findViewById(R.id.opr_picture);




        }


    }
    public  interface OnItemList{
        void onItemClick(View view, int position);
    }
}
