package util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

public class Shareimage {

    private Context context;

    public Shareimage(Context context) {
        this.context = context;
    }

    public void sharemultipleimages(ArrayList<String> imagepath){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        ArrayList<Uri> imageuri = new ArrayList<>();
        for (String path : imagepath){
            imageuri.add(Uri.parse(path));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,imageuri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent,"Share Image via.."));
    }

}
