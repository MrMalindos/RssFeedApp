package com.gleb.rssnewstest.data.providers.database;

import com.gleb.rssnewstest.data.dao.db.NewsDbModel;
import com.gleb.rssnewstest.data.dao.db.NewsDbModelTable;
import com.gleb.rssnewstest.data.providers.database.storioutils.Queries;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class StorIODbProvider implements IDataBaseProvider {
    private static final String TAG = "MY_TAG";
    private StorIOSQLite mStorIOSQLite;

    public StorIODbProvider(StorIOSQLite mStorIOSQLite) {
        this.mStorIOSQLite = mStorIOSQLite;
    }

    @Override
    public Single<List<NewsDbModel>> loadNewsList() {
        return mStorIOSQLite.get()
                .listOfObjects(NewsDbModel.class)
                .withQuery(Queries.getQueryList(NewsDbModelTable.NAME))
                .prepare()
                .asRxSingle();
    }

    @Override
    public Completable saveNewsItem(NewsDbModel model) {
        return mStorIOSQLite.put()
                .object(model)
                .prepare()
                .asRxCompletable();
    }

    @Override
    public Completable removeNewsItem(Long tableId) {
        return mStorIOSQLite.delete()
                .byQuery(Queries.deleteQueryItem(NewsDbModelTable.NAME, NewsDbModelTable.ID_COLUMN, tableId))
                .prepare()
                .asRxCompletable();
    }
}
