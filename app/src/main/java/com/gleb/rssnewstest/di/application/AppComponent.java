package com.gleb.rssnewstest.di.application;


import com.gleb.rssnewstest.di.newslist.NewsListComponent;
import com.gleb.rssnewstest.di.newslist.NewsListModule;
import com.gleb.rssnewstest.presentation.activities.view.DescriptionActivity;
import com.gleb.rssnewstest.presentation.activities.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(DescriptionActivity descriptionActivity);

    NewsListComponent plus(NewsListModule mNewsListModule);
}
