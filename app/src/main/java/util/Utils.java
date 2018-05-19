package util;

import android.graphics.Color;
import android.os.Build;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;

public class Utils {

    public static final String apiurl = "http://mondaymorning.nitrkl.ac.in/api/post/get/featured";


    public static String getString(String tagname,JSONObject jsonObject) throws JSONException{
        return jsonObject.getString(tagname);
    }

    public static JSONObject getjsonObject(String tagname,JSONObject jsonObject) throws JSONException{
        return jsonObject.getJSONObject(tagname);
    }

    public static JSONArray getjsonArray(String tagname, JSONObject jsonObject) throws JSONException{
        return jsonObject.getJSONArray(tagname);
    }

    public static int getRandomColor() {
        Random rand = new Random();
        String color[] = {"#ff0000","#000080","#191970","#FF4500","#4169E1","#8B4513","#FA8072","#2E8B57","#FFFF00","#0000ff",
                "#006400","#800000","#6B8E23","#800080","#C71585","#B22222","#D2691E","#2F4F4F","#008B8B","#FF1493"};

        return Color.parseColor(color[rand.nextInt(20)]);
    }

    public static boolean isExternalStorageAvailable() {

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean issdkgreaterthan21(){
        if (Build.VERSION.SDK_INT >= 21){
            return true;
        }
        return false;
    }
}
