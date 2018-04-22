package com.gleb.rssnewstest.business;

import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;

public interface INewsListInteractor {
    Completable changeNewsMode(NewsViewModel mId);

    Observable<NewsViewModel> loadNewsListWeb();

    Observable<NewsViewModel> loadFavouritesNews();

    void subscribeModelChanges(Observer<NewsViewModel> observer);
}
