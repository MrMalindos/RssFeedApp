package com.gleb.rssnewstest.di.newslist;

import com.gleb.rssnewstest.presentation.news.presenter.NewsPresenter;
import com.gleb.rssnewstest.presentation.newslist.presenter.NewsListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {NewsListModule.class})
@NewsListScope
public interface NewsListComponent {
    void inject(NewsListPresenter mNewsListPresenter);

    void inject(NewsPresenter newsPresenter);
}
