package models;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.simplegallary.gallary.MainActivity;
import com.simplegallary.gallary.Viewpager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import util.Utils;

public class Downloadimage {

    private ArrayList<Recyclerviewgettersetter> arrayList;
    private ArrayList<String> uri = new ArrayList<>();
    private Context context;
    private MainActivity mainActivity;
    private Viewpager viewpager;
    private Boolean flag = false;
    private int counter = 1;

    public Downloadimage(ArrayList<Recyclerviewgettersetter> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        mainActivity = (MainActivity)context;
    }

    public Downloadimage(ArrayList<Recyclerviewgettersetter> arrayList,Context context,Boolean flag){
        this.arrayList = arrayList;
        this.context = context;
        this.flag = flag;
        viewpager = (Viewpager)context;
    }

    public ArrayList<String> downloadimage(){

        for (final Recyclerviewgettersetter findurl : arrayList){

            Glide.with(context).load(findurl.getImageurl()).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                     saveimage(resource,findurl.getImagename());

                        }
                    });
        }
        return uri;
    }

    private void saveimage(Bitmap bitmap,String imageuri){

        if (Utils.isExternalStorageAvailable()) {

            File extdir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Downloadimage");

            if (!extdir.exists()) {
                extdir.mkdirs();
            }
            File files = new File(extdir, imageuri);
            OutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(files.getAbsolutePath());
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                outputStream.close();
                uri.add(files.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        if (arrayList.size() > counter){
            counter ++;
        }else {
            if (!flag) {
                mainActivity.progressDialog.cancel();
                mainActivity.progressDialog.dismiss();
                mainActivity.share(uri);
            }else {
                viewpager.progressDialog.cancel();
                viewpager.progressDialog.dismiss();
                viewpager.share(uri);
            }
        }

    }
}
