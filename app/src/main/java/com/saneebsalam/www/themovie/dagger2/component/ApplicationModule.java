package com.saneebsalam.www.themovie.dagger2.component;

import android.app.Application;
import android.content.Context;

import com.saneebsalam.www.themovie.dagger2.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Saneeb Salam
 * on 2/18/2018.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


}
