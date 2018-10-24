package bgroseclose.videogamesearch.Modules;

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import bgroseclose.videogamesearch.Services.IGameListService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {NetworkModule.class, ContextModule.class })
public class GameListServiceModule {

    @Provides
    public IGameListService gameListService(Retrofit retrofit) {
        return retrofit.create(IGameListService.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://www.giantbomb.com/api/")
                .build();
    }

    @Provides
    public Picasso picasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
}
