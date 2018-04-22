package com.gleb.rssnewstest.data.repositories;

import com.gleb.rssnewstest.data.dao.db.NewsDbModel;
import com.gleb.rssnewstest.data.dao.network.NewsListNeyworkModel;
import com.gleb.rssnewstest.data.dao.network.NewsNetworkModel;
import com.gleb.rssnewstest.data.providers.database.IDataBaseProvider;
import com.gleb.rssnewstest.data.providers.network.Api;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class NewsListRepository implements INewsListRepository {
    public static final String TAG = "MY_TAG";

    private Api mApi;
    private IDataBaseProvider mDataBaseProvider;

    public NewsListRepository(Api mApi, IDataBaseProvider mDataBaseProvider) {
        this.mApi = mApi;
        this.mDataBaseProvider = mDataBaseProvider;
    }

    @Override
    public Observable<NewsNetworkModel> loadNewsListWeb() {
        return Observable.merge(loadLifehackerNewsList(), loadTechCrunchNewsList());
    }

    private Observable<NewsNetworkModel> loadTechCrunchNewsList() {
        return mApi.loadTechCrunchNewsList("https://feeds.feedburner.com/TechCrunch/")
                .map(NewsListNeyworkModel::getNewsNetworkModelList)
                .flatMapObservable(Observable::fromIterable);
    }

    private Observable<NewsNetworkModel> loadLifehackerNewsList() {
        return mApi.loadLifehackerNewsList("https://lifehacker.com/rss")
                .map(NewsListNeyworkModel::getNewsNetworkModelList)
                .flatMapObservable(Observable::fromIterable);
    }

    @Override
    public Observable<NewsDbModel> loadNewsListDb() {
        return mDataBaseProvider.loadNewsList()
                .flatMapObservable(Observable::fromIterable);
    }

    @Override
    public Completable saveNewsItem(NewsDbModel model) {
        return mDataBaseProvider.saveNewsItem(model);
    }

    @Override
    public Completable removeNewsItem(Long tableId) {
        return mDataBaseProvider.removeNewsItem(tableId);
    }
}
