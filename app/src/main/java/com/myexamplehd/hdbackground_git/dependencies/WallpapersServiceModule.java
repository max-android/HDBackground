package com.myexamplehd.hdbackground_git.dependencies;

import android.content.Context;
import com.myexamplehd.hdbackground_git.model.service.WallpapersService;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Максим on 23.12.2017.
 */

@Module
@Singleton
public class WallpapersServiceModule {

    private Context context;

    public WallpapersServiceModule(Context context) {
        this.context = context;
    }


    @Provides
    @Singleton
    public WallpapersService provideWallpapersService() {

        return new WallpapersService(context);
    }

}
