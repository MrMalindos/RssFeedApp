package com.gleb.rssnewstest.di.application;

import android.content.Context;

import com.gleb.rssnewstest.data.dao.db.NewsDbModel;
import com.gleb.rssnewstest.data.dao.db.NewsDbModelSQLiteTypeMapping;
import com.gleb.rssnewstest.data.providers.database.IDataBaseProvider;
import com.gleb.rssnewstest.data.providers.database.StorIODbProvider;
import com.gleb.rssnewstest.data.providers.database.storioutils.StorIODbHelper;
import com.gleb.rssnewstest.data.providers.network.Api;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class AppModule {
    private final Context mContext;
    private final Cicerone<Router> mCicerone;

    public AppModule(Context context) {
        this.mContext = context;
        this.mCicerone = Cicerone.create();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return mCicerone.getRouter();
    }

    @Provides
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return mCicerone.getNavigatorHolder();
    }

    @Provides
    @Singleton
    Api provideApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://feeds.feedburner.com/TechCrunch/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(Api.class);
    }

    @Provides
    @Singleton
    IDataBaseProvider provideDataBaseProvider(StorIOSQLite storIOSQLite) {
        return new StorIODbProvider(storIOSQLite);
    }

    @Provides
    @Singleton
    StorIOSQLite provideStorIOSQLite(Context context) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(new StorIODbHelper(context))
                .addTypeMapping(NewsDbModel.class, new NewsDbModelSQLiteTypeMapping())
                .build();
    }
}
