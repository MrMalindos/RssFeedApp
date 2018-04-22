package com.gleb.rssnewstest.presentation.newslist.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gleb.rssnewstest.App;
import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.business.INewsListInteractor;
import com.gleb.rssnewstest.di.newslist.NewsListModule;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;
import com.gleb.rssnewstest.presentation.newslist.view.INewsListView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<INewsListView> implements INewsListPresenter {
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private static final String TAG = "MY_TAG";
    private int mAdapterPosition;
    @Inject
    INewsListInteractor mNewsListInteractor;
    @Inject
    Router mRouter;

    public NewsListPresenter() {
        App.get().plusNewsListModule(new NewsListModule()).inject(this);
    }

    @Override
    public void init() {
        getViewState().showLoading();
        loadList(true);
        subscribeModelChanges();
    }

    @Override
    public void initFavourite() {
        loadList(false);
    }

    private void subscribeModelChanges() {
        mNewsListInteractor.subscribeModelChanges(new Observer<NewsViewModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposables.add(d);
            }

            @Override
            public void onNext(NewsViewModel model) {
                getViewState().changeItem(model, mAdapterPosition);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void updateList() {
        loadList(true);
    }

    private void loadList(Boolean mode) {
        Observable<NewsViewModel> request;
        if (mode) {
            request = mNewsListInteractor.loadNewsListWeb();
        } else {
            request = mNewsListInteractor.loadFavouritesNews();
        }
        mDisposables.add(request
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> getViewState().clearAdapter())
                .subscribe(this::onSuccess, this::onError));
    }

    private void onSuccess(NewsViewModel model) {
        Log.d(TAG, "onSuccess: " + model.hashCode());
        if (getViewState() != null) {
            getViewState().showNews(model);
            getViewState().hideLoading();
        }
    }

    private void onError(Throwable t) {
        Log.e(TAG, getClass().getSimpleName() + "onError: ", t);
        if (getViewState() != null) {
            getViewState().hideLoading();
            getViewState().showEmptyList();
            getViewState().showLoadError();
        }
    }

    @Override
    public void onNewsItemClicked(NewsViewModel model, int adapterPosition) {
        mAdapterPosition = adapterPosition;
        mRouter.navigateTo(Constants.DESCRIPTION_SCREEN, model);
    }

    @Override
    public void onDestroy() {
        mDisposables.dispose();
        super.onDestroy();
    }
}
