package com.gleb.rssnewstest.data.dao.mappers;

import com.gleb.rssnewstest.data.dao.db.NewsDbModel;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

public class NewsViewToDbModelMapper {
    public static NewsDbModel map(NewsViewModel model) {
        return new NewsDbModel(
                model.getId(),
                model.getTitle(),
                model.getLink(),
                model.getImage(),
                model.getMode()
        );
    }
}
