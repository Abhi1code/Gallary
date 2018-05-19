package com.simplegallary.gallary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

import models.Downloadimage;
import models.Recyclerviewgettersetter;
import models.Viewpageradaptor;
import util.Shareimage;
import util.Utils;

public class Viewpager extends AppCompatActivity{

    private ViewPager viewPager;
    private Viewpageradaptor viewpageradaptor;
    public ArrayList<Recyclerviewgettersetter> arrayList;
    public int position;
    private Toolbar toolbar;
    public ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Downloading Image..");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        getviews();
        getarguments();
    }

    private void getviews(){
        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getarguments(){
        arrayList = getIntent().getParcelableArrayListExtra("arraylist");
        position = getIntent().getIntExtra("position",0);
        if (arrayList == null){
            arrayList = new ArrayList<>();
        }
        viewpageradaptor = new Viewpageradaptor(this,arrayList);
        viewPager.setAdapter(viewpageradaptor);
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionmode,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.share){
            progressDialog.show();
            ArrayList<Recyclerviewgettersetter> getposition = new ArrayList<>();
            getposition.add(arrayList.get(viewPager.getCurrentItem()));
            new Downloadimage(getposition,this,true).downloadimage();

        }else if (item.getItemId() == android.R.id.home){
                finish();

        }
        return true;
    }

    public void share(ArrayList<String> imageuri){
        if (imageuri.size() == 0){
            Toast.makeText(this, "Downloading Failed", Toast.LENGTH_SHORT).show();
        }else {
            new Shareimage(this).sharemultipleimages(imageuri);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            finish();

    }
}
