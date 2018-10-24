package bgroseclose.videogamesearch.Models;

import com.google.gson.annotations.SerializedName;

public class GameModel {

    @SerializedName("name")
    private String gameName;
    @SerializedName("image")
    private ImagesModel images;
    @SerializedName("deck")
    private String gameDesc;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ImagesModel getImages() {
        return images;
    }

    public void setImages(ImagesModel images) {
        this.images = images;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }
}
