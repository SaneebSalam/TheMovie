package com.saneebsalam.www.themovie.dagger2.component;

import android.app.Application;
import android.content.Context;

import com.saneebsalam.www.themovie.MyApplication;
import com.saneebsalam.www.themovie.dagger2.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Saneeb Salam
 * on 2/18/2018.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApplication demoApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();


}
