package models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.simplegallary.gallary.R;

import java.util.ArrayList;

import util.Utils;

public class Viewpageradaptor extends PagerAdapter{

    private Context context;
    public ArrayList<Recyclerviewgettersetter> arrayList;

    public Viewpageradaptor(Context context,ArrayList<Recyclerviewgettersetter> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.viewpagerchild,container,false);
        ImageView pagerimage = v.findViewById(R.id.viewpagerimage);

        Glide.with(context).load(arrayList.get(position).getImageurl()).placeholder(R.mipmap.load).error(R.mipmap.fail).into(pagerimage);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);
    }
}
