package util;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Errorhelper {

    public static void controlmainactivity(RecyclerView recyclerView, ConstraintLayout progressbar,
                                           ConstraintLayout errorlayout,int code){

        switch (code){
            case 1:
                recyclerView.setVisibility(View.VISIBLE);
                progressbar.setVisibility(View.GONE);
                errorlayout.setVisibility(View.GONE);
                break;
            case 2:
                progressbar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                errorlayout.setVisibility(View.GONE);
                break;
            case 3:
                errorlayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);
                break;
        }
    }
}
