package com.simplegallary.gallary;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import models.Downloadimage;
import models.Recyclerviewadaptor;
import models.Recyclerviewgettersetter;
import util.Errorhelper;
import util.Jsonparser;
import util.Mysingleton;
import util.Shareimage;
import util.Utils;

public class MainActivity extends AppCompatActivity {

    public Recyclerviewadaptor adapter;
    public ArrayList<Recyclerviewgettersetter> arrayList, sharelist;
    public Boolean is_in_action = false;
    public ProgressDialog progressDialog;
    private RecyclerView imagerecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private TextView toolbartext;
    private int counter = 0;
    public FloatingActionButton refresh;
    public ConstraintLayout progressbar, errorlayout;

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Downloading Image..");
        checkpermission();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();
        setrecyclerview();
        retrievedata();

    }

    private void findviews() {
        imagerecyclerView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbartext = findViewById(R.id.itemselected);
        refresh = findViewById(R.id.refresh);
        progressbar = findViewById(R.id.progressBar);
        errorlayout = findViewById(R.id.errorlayout);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrievedata();
            }
        });
    }

    private void setrecyclerview() {
        arrayList = new ArrayList<>();
        sharelist = new ArrayList<>();
        imagerecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        imagerecyclerView.setItemAnimator(new DefaultItemAnimator());
        imagerecyclerView.setLayoutManager(layoutManager);

    }

    private void retrievedata() {
        Errorhelper.controlmainactivity(imagerecyclerView,progressbar,errorlayout,2);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utils.apiurl, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {


                        new Jsonparser(MainActivity.this, response, arrayList) {
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                adapter = new Recyclerviewadaptor(arrayList, MainActivity.this);
                                imagerecyclerView.setAdapter(adapter);
                                startviewpager();
                                Errorhelper.controlmainactivity(imagerecyclerView,progressbar,errorlayout,1);
                                adapter.runLayoutAnimation(imagerecyclerView);
                            }
                        }.execute();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Errorhelper.controlmainactivity(imagerecyclerView,progressbar,errorlayout,3);
            }
        });

        Mysingleton.getInstance(MainActivity.this.getApplicationContext()).addtorequestque(jsonObjectRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void startviewpager() {
        if (adapter != null) {
            adapter.setOnitemclicklistener(new Recyclerviewadaptor.onItemclicklistener() {
                @Override
                public void onItemclick(int position) {
                    if (is_in_action) {
                        handlearraylist(position);
                        handletoolbartext();
                        adapter.notifyDataSetChanged();
                    } else {

                        Intent intent = new Intent(MainActivity.this, Viewpager.class);
                        intent.putExtra("arraylist", arrayList);
                        intent.putExtra("position", position);
                        startActivity(intent);


                    }

                }

                @Override
                public void onItemlongclick(int position) {
                    onLongclick(position);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normalmode, menu);
        return true;
    }

    private void onLongclick(int position) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.actionmode);
        toolbartext.setText("1 Item Selected");
        is_in_action = true;
        handlearraylist(position);
        handletoolbartext();
        adapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handletoolbartext() {

        if (counter == 0) {
            toolbar.getMenu().clear();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbar.inflateMenu(R.menu.normalmode);
            toolbartext.setText("Image Gallary");
            is_in_action = false;
            adapter.clearanimestatus();
            sharelist.clear();
        } else {
            toolbartext.setText("" + counter + " Item Selected");
        }
    }

    private void handlearraylist(int position) {
        if (arrayList.get(position).getanimestatus()) {
            sharelist.remove(arrayList.get(position));
            arrayList.get(position).setanimestatus(false);
            counter--;
        } else {
            arrayList.get(position).setanimestatus(true);
            sharelist.add(arrayList.get(position));
            counter++;
        }
    }

    @Override
    public void onBackPressed() {
        if (is_in_action) {
            counter = 0;
            handletoolbartext();
            adapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
        progressDialog.cancel();
        progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.share) {

            if (sharelist.size() != 0) {
                progressDialog.show();
                new Downloadimage(sharelist, this).downloadimage();

            } else {
                Toast.makeText(this, "No Item Selected", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == android.R.id.home) {
            counter = 0;
            handletoolbartext();
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    public void share(ArrayList<String> imageuri) {
        if (imageuri.size() == 0) {
            Toast.makeText(this, "Downloading Failed", Toast.LENGTH_SHORT).show();
        } else {
            new Shareimage(this).sharemultipleimages(imageuri);
        }
    }

    private void checkpermission() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 001);
        }
    }

}
