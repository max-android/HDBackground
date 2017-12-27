package com.myexamplehd.hdbackground_git.dependencies;

import android.content.Context;
import com.myexamplehd.hdbackground_git.model.service.AssetsParser;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Максим on 23.12.2017.
 */


@Module
@Singleton
public class ParserModule {


    private Context context;


    public ParserModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public AssetsParser provideParserModule(){


        return new AssetsParser(context);
    }

}


