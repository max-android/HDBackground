package com.myexamplehd.hdbackground_git.dependencies;

import com.myexamplehd.hdbackground_git.ui.paperhanging.PaperhangingActivity;
import com.myexamplehd.hdbackground_git.ui.wallpapers.WallpapersActivity;
import com.myexamplehd.hdbackground_git.ui.wallpapers.WallpapersFragment;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
                      GlideModule.class,
                   PresenterModule.class,
                   WallpapersServiceModule.class,
                   ParserModule.class,
                   FileModule.class
})

public interface AppComponent {

    void injectWallpapersActivity(WallpapersActivity activity);
    void injectWallpapersFragments(WallpapersFragment fragments);
    void injectPaperhangingActivity(PaperhangingActivity activity);

}



