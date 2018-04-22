package com.gleb.rssnewstest.data.providers.network;

import com.gleb.rssnewstest.data.dao.network.NewsListNeyworkModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET
    Single<NewsListNeyworkModel> loadTechCrunchNewsList(@Url String url);

    @GET
    Single<NewsListNeyworkModel> loadLifehackerNewsList(@Url String url);
}
