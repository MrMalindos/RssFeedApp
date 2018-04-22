package com.gleb.rssnewstest.data.dao.mappers;

import android.util.Log;

import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.data.dao.network.NewsNetworkModel;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

import io.reactivex.functions.Function;

public class NewsNetworkToViewModelMapper implements Function<NewsNetworkModel, NewsViewModel> {
    private static final String TAG = "MY_TAG";
    private Set<Long> keySet;

    public NewsNetworkToViewModelMapper(Set<Long> keySet) {
        this.keySet = keySet;
    }

    @Override
    public NewsViewModel apply(NewsNetworkModel n) {
        Long id = parseDate(n.getPubDate());
        return new NewsViewModel(
                id,
                n.getTitle(),
                n.getLink(),
                parseImage(n.getImage()),
                parseMode(id)
        );
    }

    private String parseMode(Long id) {
        return keySet.contains(id) ? Constants.FAVOURITE : "";
    }

    private String parseImage(String image) {
        String[] split = image.split("<img src=" + "\"");
        if (split.length == 1) {
            return "def";
        } else {
            int i = split[1].indexOf("\"");
            return split[1].substring(0, i);
        }
    }

    private Long parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            Log.e(TAG, getClass().getSimpleName() + " error: ", e);
            return System.currentTimeMillis();
        }
    }
}

