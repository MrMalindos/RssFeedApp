package com.gleb.rssnewstest.presentation.news.presenter;

import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

public interface INewsPresenter {
    void onLinkButtonClicked(String link);

    void onBackItemSelected();

    void onFavouriteBtnClicked(NewsViewModel mModel);
}
