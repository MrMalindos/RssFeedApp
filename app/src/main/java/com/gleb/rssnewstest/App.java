package com.gleb.rssnewstest;

import android.app.Application;

import com.gleb.rssnewstest.di.application.AppComponent;
import com.gleb.rssnewstest.di.application.AppModule;
import com.gleb.rssnewstest.di.application.DaggerAppComponent;
import com.gleb.rssnewstest.di.newslist.NewsListComponent;
import com.gleb.rssnewstest.di.newslist.NewsListModule;

public class App extends Application {
    private static App mApp;
    private AppComponent mAppComponent;

    private NewsListComponent mNewsListComponent;

    public App() {
        mApp = this;
    }

    public static App get() {
        return mApp;
    }

    @Override
    public void onCreate() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        super.onCreate();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public NewsListComponent plusNewsListModule(NewsListModule mNewsListModule) {
        if (mNewsListComponent == null) {
            mNewsListComponent = getAppComponent().plus(mNewsListModule);
        }
        return mNewsListComponent;
    }

    public void clearNewsListComponent() {
        mNewsListComponent = null;
    }
}
