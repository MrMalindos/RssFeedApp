package com.gleb.rssnewstest.di.newslist;

import com.gleb.rssnewstest.business.INewsListInteractor;
import com.gleb.rssnewstest.business.NewsListInteractor;
import com.gleb.rssnewstest.data.providers.database.IDataBaseProvider;
import com.gleb.rssnewstest.data.providers.network.Api;
import com.gleb.rssnewstest.data.repositories.INewsListRepository;
import com.gleb.rssnewstest.data.repositories.NewsListRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsListModule {
    @Provides
    @NewsListScope
    INewsListRepository provideNewsListRepository(Api api, IDataBaseProvider mDataBaseProvider) {
        return new NewsListRepository(api, mDataBaseProvider);
    }

    @Provides
    @NewsListScope
    INewsListInteractor provideNewsListInteractor(INewsListRepository mNewsListRepository) {
        return new NewsListInteractor(mNewsListRepository);
    }
}
