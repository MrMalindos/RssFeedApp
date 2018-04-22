package com.gleb.rssnewstest.presentation.newslist.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.presentation.newslist.view.NewsListFragment;

public class NewsListPagerAdapter extends FragmentPagerAdapter {
    public static final String TAG = "MY_TAG";

    private static final int NUMBERS_OF_TAB = 2;

    public NewsListPagerAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewsListFragment.newInstance(Constants.ALL);
            case 1:
                return NewsListFragment.newInstance(Constants.FAVOURITE);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBERS_OF_TAB;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "News";
            case 1:
                return "Favourite";
            default:
                return null;
        }
    }
}
