package com.example.tacticalb.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tacticalb.ClassforServer.Otvet;
import com.example.tacticalb.R;
import com.example.tacticalb.Service.Save_inf;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder        > {
    /*
    # Адаптер для показа списков user игры
     */
    private Context context;
    private List<Otvet> mUser;
    Save_inf save_inf;
    public UserAdapter(Context context,List<Otvet> mUser){
        this.context=context;
        this.mUser=mUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(context).inflate(R.layout.useradapter,viewGroup,false);
       return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
Otvet otvet=mUser.get(i);
viewHolder.number.setText("#"+(i+1));
switch (i){
    case 0:viewHolder.nagrada.setImageResource(R.drawable.first);break;
    case 1:viewHolder.nagrada.setImageResource(R.drawable.second);break;
    case 2:viewHolder.nagrada.setImageResource(R.drawable.tr);break;
}
save_inf=new Save_inf(context);
if (otvet.getMyid().equals(save_inf.getHero().get_ID()))
viewHolder.linearLayout.setBackgroundColor(Color.argb(99,00,78,156));
viewHolder.username.setText(otvet.getLOGIN());
viewHolder.level.setText(otvet.getLEVEL());
viewHolder.xp.setText(otvet.getXP());
Glide.with(context).load("http://cj76241.tmweb.ru/picture/flag/"+otvet.getLANGUAGE()+".png").into(viewHolder.st);
if (otvet.getURI_pic().equals("NO")){
    viewHolder.user_pic.setImageResource(R.drawable.wolf1);

}else
    {
       Glide.with(context).load(otvet.getURI_pic()).into(viewHolder.user_pic);
}
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username,level,xp,number;
        public ImageView user_pic,nagrada,st;
        LinearLayout linearLayout;

        public ViewHolder( View itemView) {
            super(itemView);
            number=itemView.findViewById(R.id.number);
            linearLayout=itemView.findViewById(R.id.linear);
            username=itemView.findViewById(R.id.userName);
            level=itemView.findViewById(R.id.z_level);
            xp=itemView.findViewById(R.id.z_xp);
            user_pic=itemView.findViewById(R.id.user_picture);
            st=itemView.findViewById(R.id.st);
            nagrada=itemView.findViewById(R.id.nagrada);
            TextView l=itemView.findViewById(R.id.lev);
            l.setText("Level");
            ImageView image_xp=itemView.findViewById(R.id.xpd_im);
            image_xp.setImageResource(R.drawable.xpfoe);

        }
    }
}
