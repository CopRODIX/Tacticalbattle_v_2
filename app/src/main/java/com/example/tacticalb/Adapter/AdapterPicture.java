package com.example.tacticalb.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tacticalb.R;

import java.util.List;

public class AdapterPicture extends RecyclerView.Adapter<AdapterPicture.MyViewHolder> {
    /*
    #Адаптер для показа и скачивания картинок из библиотеки картинок на сервере
     */
private List<String> http;
private Context context;
 OnItemList onItemList;
public  AdapterPicture(List<String> http,Context context,OnItemList onItemList){
    this.context=context;
    this.http=http;
    this.onItemList=onItemList;

}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_picture,viewGroup,false);

        return new MyViewHolder(view,onItemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
String string=http.get(i).toString();
//Загрузка фотографий по url=string
        Glide.with(context).load(string).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return http.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView;
    OnItemList onItemList;
    CardView cardView;

        public MyViewHolder(@NonNull View itemView,OnItemList onItemList) {
            super(itemView);
            imageView=itemView.findViewById(R.id.card_picture);
            cardView=itemView.findViewById(R.id.card_pic);
            this.onItemList=onItemList;
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

                onItemList.onItemClick(v,getAdapterPosition());

        }
    }

    public  interface OnItemList{
        void onItemClick(View view, int position);
    }
}


