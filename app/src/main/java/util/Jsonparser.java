package util;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import models.Recyclerviewadaptor;
import models.Recyclerviewgettersetter;

public class Jsonparser extends AsyncTask<Void,Void,Void>{

    private Context context;
    private JSONObject jsonObject;
    private ArrayList<Recyclerviewgettersetter> arrayList;
    private ArrayList<String> imagename = new ArrayList<>();
    private ArrayList<String> imageurl = new ArrayList<>();

    public Jsonparser(Context context, JSONObject jsonObject, ArrayList<Recyclerviewgettersetter> arrayList) {
        this.context = context;
        this.jsonObject = jsonObject;
        this.arrayList = arrayList;
    }

    public void parsejson(){

        try {
            JSONObject getslider = Utils.getjsonObject("slider",jsonObject);
            String sliderimagemainlink = Utils.getString("imageUrlPrefix",getslider);
            JSONArray getfirstarray = Utils.getjsonArray("posts",getslider);
            int count = 0;
            while (count < getfirstarray.length()){
                JSONObject sliderobject = getfirstarray.getJSONObject(count);
                String slidersuffixlink = Utils.getString("featured_image",sliderobject);
                imagename.add(slidersuffixlink);
                imageurl.add(sliderimagemainlink + slidersuffixlink);
                count ++;
            }

            JSONObject gettop4 = Utils.getjsonObject("top4",jsonObject);
            String top4imagemainlink = Utils.getString("imageUrlPrefix",gettop4);
            JSONArray secondjsonarray = Utils.getjsonArray("posts",gettop4);
            int count2 = 0;
            while (count2 < secondjsonarray.length()){
                JSONObject top4 = secondjsonarray.getJSONObject(count2);
                String top4suffixlink = Utils.getString("featured_image",top4);
                imagename.add(top4suffixlink);
                imageurl.add(top4imagemainlink + top4suffixlink);
                count2 ++;
            }

            if (imageurl.size() != 0 && imagename.size() != 0 && imagename.size() == imageurl.size()){
                for (int i=0;i<imageurl.size();i++){
                    Recyclerviewgettersetter recyclerviewgettersetter = new Recyclerviewgettersetter(imageurl.get(i),imagename.get(i),false);
                    arrayList.add(recyclerviewgettersetter);

                }
            }else {
                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        parsejson();
        return null;
    }
}
