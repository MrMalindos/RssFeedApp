package com.gleb.rssnewstest.data.repositories;

import com.gleb.rssnewstest.data.dao.db.NewsDbModel;
import com.gleb.rssnewstest.data.dao.network.NewsNetworkModel;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface INewsListRepository {
    Observable<NewsNetworkModel> loadNewsListWeb();

    Observable<NewsDbModel> loadNewsListDb();

    Completable saveNewsItem(NewsDbModel model);

    Completable removeNewsItem(Long tableId);
}
