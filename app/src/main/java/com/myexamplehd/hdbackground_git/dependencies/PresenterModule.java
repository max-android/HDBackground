package com.myexamplehd.hdbackground_git.dependencies;


import com.myexamplehd.hdbackground_git.model.service.AssetsParser;
import com.myexamplehd.hdbackground_git.model.service.WallpapersService;
import com.myexamplehd.hdbackground_git.presenter.WallpapersPresenter;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Максим on 03.11.2017.
 */

@Module
@Singleton
public class PresenterModule {




    @Provides
    @Singleton
    public WallpapersPresenter provideWallpapersPresenter(AssetsParser parser, WallpapersService service){

        return  new WallpapersPresenter(parser,service);
    }

}
