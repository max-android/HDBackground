package com.myexamplehd.hdbackground_git.dependencies;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Максим on 18.12.2017.
 */


@Module
@Singleton
public class GlideModule {

    private Context context;

    public GlideModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public RequestManager provideGlideRequestManager(){

        RequestManager requestManager = Glide.with(context);

        return requestManager;
    }


}
