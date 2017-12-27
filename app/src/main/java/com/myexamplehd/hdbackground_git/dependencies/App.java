package com.myexamplehd.hdbackground_git.dependencies;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .wallpapersServiceModule(new WallpapersServiceModule(this))
                .glideModule(new GlideModule(this))
                .parserModule(new ParserModule(this))
                .presenterModule(new PresenterModule())
                .fileModule(new FileModule(this))
                .build();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
