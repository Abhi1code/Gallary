package models;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.simplegallary.gallary.MainActivity;
import com.simplegallary.gallary.R;


import java.util.ArrayList;

import util.Utils;

public class Recyclerviewadaptor extends RecyclerView.Adapter<Recyclerviewadaptor.viewholder>{

    private ArrayList<Recyclerviewgettersetter> arrayList;
    private Context context;
    private onItemclicklistener onitemclicklistener;
    private MainActivity mainActivity;

    public interface onItemclicklistener{
        void onItemclick(int position);
        void onItemlongclick(int position);
    }

    public void setOnitemclicklistener(onItemclicklistener onitemclicklistener){
        this.onitemclicklistener = onitemclicklistener;
    }

    public static class viewholder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public LinearLayout longclickanime;
        public MainActivity mainActivity;

        public viewholder(View itemView,final onItemclicklistener listener,MainActivity mainActivity) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            longclickanime = itemView.findViewById(R.id.longclickanime);
            this.mainActivity = mainActivity;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemclick(position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemlongclick(position);
                        }
                    }
                    return true;
                }
            });
        }
    }

    public Recyclerviewadaptor(ArrayList<Recyclerviewgettersetter> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
        mainActivity = (MainActivity)context;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.childlayout,parent,false);
        viewholder holderclass = new viewholder(v,onitemclicklistener,mainActivity);
        return holderclass;
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        Glide.with(context)
             .load(arrayList.get(position).getImageurl()).placeholder(R.mipmap.load).error(R.mipmap.fail).into(holder.imageView);

        holder.imageView.setContentDescription(arrayList.get(position).getImagename().toString());

        if (mainActivity.is_in_action && arrayList.get(position).getanimestatus()){
            holder.longclickanime.setVisibility(View.VISIBLE);
        }else {
            holder.longclickanime.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void clearanimestatus(){

        for (int i=0;i<arrayList.size();i++){
            arrayList.get(i).setanimestatus(false);
            notifyDataSetChanged();
        }
    }
}
