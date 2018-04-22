package com.gleb.rssnewstest.presentation.newslist.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import java.util.List;

public interface INewsListView extends MvpView {
    void showEmptyList();

    void showNews(NewsViewModel mNewsListModel);

    void hideLoading();

    void showLoading();

    void showLoadError();

    void changeItem(NewsViewModel model, int mAdapterPosition);

    void clearAdapter();
}
