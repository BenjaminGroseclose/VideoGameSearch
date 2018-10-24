package bgroseclose.videogamesearch.Services;

import bgroseclose.videogamesearch.Models.SearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGameListService {
//api_key={key}&format=json&query=\"{search_term}\"&resources=game"

    @GET("search/")
    Call<SearchResult> getSearchResult(@Query("api_key") String key,
                @Query("format") String format,
                @Query("query") String searchTerm,
                @Query("resources") String resource);

}
