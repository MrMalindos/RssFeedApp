package com.gleb.rssnewstest.business;

import com.gleb.rssnewstest.data.dao.mappers.NewsDbToViewModelMapper;
import com.gleb.rssnewstest.data.dao.mappers.NewsNetworkToViewModelMapper;
import com.gleb.rssnewstest.data.dao.mappers.NewsViewToDbModelMapper;
import com.gleb.rssnewstest.data.repositories.INewsListRepository;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class NewsListInteractor implements INewsListInteractor {
    private INewsListRepository mNewsListRepository;
    private PublishSubject<NewsViewModel> modelState = PublishSubject.create();
    private Set<Long> keySet = new HashSet<>();

    public NewsListInteractor(INewsListRepository mNewsListRepository) {
        this.mNewsListRepository = mNewsListRepository;
        mNewsListRepository.loadNewsListDb()
                .subscribeOn(Schedulers.io())
                .filter(n -> keySet.add(n.getId()))
                .subscribe();
    }

    @Override
    public Observable<NewsViewModel> loadNewsListWeb() {
        return mNewsListRepository.loadNewsListWeb()
                .map(new NewsNetworkToViewModelMapper(keySet))
                .toSortedList((o1, o2) -> (int) (o2.getId() - o1.getId()))
                .flatMapObservable(Observable::fromIterable);
    }

    @Override
    public Observable<NewsViewModel> loadFavouritesNews() {
        return mNewsListRepository.loadNewsListDb()
                .map(new NewsDbToViewModelMapper())
                .toSortedList((o1, o2) -> (int) (o2.getId() - o1.getId()))
                .flatMapObservable(Observable::fromIterable);
    }

    @Override
    public void subscribeModelChanges(Observer<NewsViewModel> observer) {
        modelState.subscribe(observer);
    }

    @Override
    public Completable changeNewsMode(NewsViewModel model) {
        modelState.onNext(model);
        if (model.getMode().isEmpty()) {
            keySet.remove(model.getId());
            return mNewsListRepository.removeNewsItem(model.getId());
        } else {
            keySet.add(model.getId());
            return mNewsListRepository.saveNewsItem(NewsViewToDbModelMapper.map(model));
        }
    }
}
