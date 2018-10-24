package bgroseclose.videogamesearch.Components;

import com.squareup.picasso.Picasso;

import bgroseclose.videogamesearch.Modules.GameListServiceModule;
import bgroseclose.videogamesearch.Services.IGameListService;
import dagger.Component;

@Component(modules = GameListServiceModule.class)
public interface IGameListComponent {

    Picasso getPicasso();

    IGameListService getGameListService();

}
