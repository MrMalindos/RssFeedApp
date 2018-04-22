package com.gleb.rssnewstest.data.providers.database;

import com.gleb.rssnewstest.data.dao.db.NewsDbModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IDataBaseProvider {
    Single<List<NewsDbModel>> loadNewsList();

    Completable saveNewsItem(NewsDbModel model);

    Completable removeNewsItem(Long tableId);
}
