package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Recyclerviewgettersetter implements Parcelable{

    private String imageurl;
    private String imagename;
    private Boolean anime;

    public Recyclerviewgettersetter(String imageurl, String imagename,Boolean anime) {
        this.imageurl = imageurl;
        this.imagename = imagename;
        this.anime = anime;
    }


    protected Recyclerviewgettersetter(Parcel in) {
        imageurl = in.readString();
        imagename = in.readString();
        byte tmpAnime = in.readByte();
        anime = tmpAnime == 0 ? null : tmpAnime == 1;
    }

    public static final Creator<Recyclerviewgettersetter> CREATOR = new Creator<Recyclerviewgettersetter>() {
        @Override
        public Recyclerviewgettersetter createFromParcel(Parcel in) {
            return new Recyclerviewgettersetter(in);
        }

        @Override
        public Recyclerviewgettersetter[] newArray(int size) {
            return new Recyclerviewgettersetter[size];
        }
    };

    public String getImageurl() {
        return imageurl;
    }

    public String getImagename() {
        return imagename;
    }

    public Boolean getanimestatus(){ return anime;}

    public void setanimestatus(Boolean status){anime = status;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageurl);
        parcel.writeString(imagename);
        parcel.writeByte((byte) (anime == null ? 0 : anime ? 1 : 2));
    }
}
