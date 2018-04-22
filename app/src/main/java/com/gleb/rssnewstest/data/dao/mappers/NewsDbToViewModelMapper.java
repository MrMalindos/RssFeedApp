package com.gleb.rssnewstest.data.dao.mappers;

import com.gleb.rssnewstest.data.dao.db.NewsDbModel;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import io.reactivex.functions.Function;

public class NewsDbToViewModelMapper implements Function<NewsDbModel, NewsViewModel> {
    @Override
    public NewsViewModel apply(NewsDbModel model) {
        return new NewsViewModel(
                model.getId(),
                model.getTitle(),
                model.getLink(),
                model.getImage(),
                model.getMode()
        );
    }
}
