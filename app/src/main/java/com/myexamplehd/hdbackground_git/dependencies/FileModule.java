package com.myexamplehd.hdbackground_git.dependencies;

import android.content.Context;
import com.myexamplehd.hdbackground_git.model.service.FileManager;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Максим on 23.12.2017.
 */
@Module
@Singleton
public class FileModule {

    private Context context;

    public FileModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public FileManager provideFileManager(){

        return new FileManager(context);

    }
}
