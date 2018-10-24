package bgroseclose.videogamesearch.Models;

import com.google.gson.annotations.SerializedName;

public class ImagesModel {

    @SerializedName("thumb_url")
    private String thumbNail;

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }
}
