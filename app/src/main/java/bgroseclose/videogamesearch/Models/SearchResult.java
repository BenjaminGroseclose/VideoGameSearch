package bgroseclose.videogamesearch.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {

    @SerializedName("number_of_page_results")
    private int searchCount;
    @SerializedName("results")
    private List<GameModel> games;

    public List<GameModel> getGames() {
        return games;
    }

    public void setGames(List<GameModel> games) {
        this.games = games;
    }

    public int getSearchCount() {

        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }
}
