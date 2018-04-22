package com.gleb.rssnewstest.presentation.newslist.presenter;

import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

public interface INewsListPresenter {
    void init();

    void onNewsItemClicked(NewsViewModel model, int adapterPosition);

    void updateList();

    void initFavourite();
}
