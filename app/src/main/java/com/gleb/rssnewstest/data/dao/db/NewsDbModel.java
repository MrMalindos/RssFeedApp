package com.gleb.rssnewstest.data.dao.db;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio3.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = "news")
public class NewsDbModel {
    @NonNull
    @StorIOSQLiteColumn(name = "_id", key = true)
    Long tableId;
    @NonNull
    @StorIOSQLiteColumn(name = "news_id")
    Long id;
    @NonNull
    @StorIOSQLiteColumn(name = "title")
    String title;
    @NonNull
    @StorIOSQLiteColumn(name = "link")
    String link;
    @NonNull
    @StorIOSQLiteColumn(name = "image")
    String image;
    @NonNull
    @StorIOSQLiteColumn(name = "mode")
    String mode;

    NewsDbModel() {
    }

    public NewsDbModel(@NonNull Long id, @NonNull String title,
                       @NonNull String link, @NonNull String image,
                       @NonNull String mode) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.image = image;
        this.mode = mode;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getLink() {
        return link;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    @NonNull
    public String getMode() {
        return mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsDbModel that = (NewsDbModel) o;

        if (!tableId.equals(that.tableId)) return false;
        if (!id.equals(that.id)) return false;
        if (!title.equals(that.title)) return false;
        if (!link.equals(that.link)) return false;
        if (!image.equals(that.image)) return false;
        return mode.equals(that.mode);
    }

    @Override
    public int hashCode() {
        int result = tableId.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + image.hashCode();
        result = 31 * result + mode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NewsDbModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
