package com.gleb.rssnewstest.presentation.activities.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.gleb.rssnewstest.App;
import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.R;
import com.gleb.rssnewstest.presentation.newslist.adapters.NewsListPagerAdapter;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMainView {
    @BindView(R.id.vpPager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @Inject
    NavigatorHolder mNavigatorHolder;
    private Navigator mNavigator;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get().getAppComponent().inject(this);
        mUnbinder = ButterKnife.bind(this);

        //View pager settings
        mViewPager.setAdapter(new NewsListPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        mNavigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.activity_main) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                return null;
            }

            @Override
            protected Intent createActivityIntent(Context context, String screenKey, Object data) {
                switch (screenKey) {
                    case Constants.DESCRIPTION_SCREEN:
                        Intent mIntent = new Intent(context, DescriptionActivity.class);
                        mIntent.putExtra(DescriptionActivity.EXTRA_SCREEN_KEY, screenKey);
                        mIntent.putExtra(DescriptionActivity.EXTRA_ID_KEY, (NewsViewModel) data);
                        return mIntent;
                    default:
                        Log.e("Cicerone", "Unknown screen: " + screenKey);
                        break;
                }
                return null;
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigatorHolder.setNavigator(mNavigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNavigatorHolder.removeNavigator();
    }

    @Override
    public void onDestroy() {
        App.get().clearNewsListComponent();
        mUnbinder.unbind();
        super.onDestroy();
    }
}
