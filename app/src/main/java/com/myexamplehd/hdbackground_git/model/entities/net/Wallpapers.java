package com.myexamplehd.hdbackground_git.model.entities.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Максим on 17.12.2017.
 */

public class Wallpapers implements Parcelable {

    private String id;
    private String url;

    public Wallpapers(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Wallpapers(Parcel parcel){

        String[] data = new String[2];
         parcel.readStringArray(data);
            id = data[0];
            url = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeStringArray(new String[]{id,url});

    }

    public static final Creator<Wallpapers> CREATOR = new Creator<Wallpapers>() {
        @Override
        public Wallpapers createFromParcel(Parcel source) {
            return new Wallpapers(source);
        }

        @Override
        public Wallpapers[] newArray(int size) {
            return new Wallpapers[size];
        }
    };

}
