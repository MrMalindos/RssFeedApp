package com.gleb.rssnewstest.presentation.news.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gleb.rssnewstest.App;
import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.R;
import com.gleb.rssnewstest.business.INewsListInteractor;
import com.gleb.rssnewstest.di.newslist.NewsListModule;
import com.gleb.rssnewstest.presentation.news.view.INewsView;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class NewsPresenter extends MvpPresenter<INewsView> implements INewsPresenter {
    private static final String TAG = "MY_TAG";
    @Inject
    Router mRouter;
    @Inject
    INewsListInteractor mInteractor;

    public NewsPresenter() {
        App.get().plusNewsListModule(new NewsListModule()).inject(this);
    }

    @Override
    public void onFavouriteBtnClicked(NewsViewModel model) {
        if (model.getMode().isEmpty()) {
            model.setMode(Constants.FAVOURITE);
            getViewState().changeFavouriteBtn(R.string.remove_favourite_btn);
        } else {
            model.setMode("");
            getViewState().changeFavouriteBtn(R.string.add_favourite_btn);
        }
        mInteractor.changeNewsMode(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(this::onError)
                .subscribe();
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, "onError: ", throwable);
    }

    @Override
    public void onLinkButtonClicked(String link) {
        mRouter.navigateTo(Constants.LINK_SCREEN, link);
    }

    @Override
    public void onBackItemSelected() {
        mRouter.exit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
