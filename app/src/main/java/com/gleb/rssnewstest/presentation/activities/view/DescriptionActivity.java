package com.gleb.rssnewstest.presentation.activities.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.gleb.rssnewstest.App;
import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.R;
import com.gleb.rssnewstest.presentation.news.view.NewsFragment;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class DescriptionActivity extends MvpAppCompatActivity implements IDescriptionView {
    public static final String EXTRA_ID_KEY = "EXTRA_ID";
    public static final String EXTRA_SCREEN_KEY = "EXTRA_SCREEN_KEY";
    private Navigator mNavigator;

    @Inject
    NavigatorHolder mNavigatorHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.get().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        String mItemScreenKey = getIntent().getExtras().getString(EXTRA_SCREEN_KEY);
        NewsViewModel model = getIntent().getExtras().getParcelable(EXTRA_ID_KEY);
        mNavigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.activity_description) {

            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey) {
                    case Constants.DESCRIPTION_SCREEN:
                        return NewsFragment.newInstance((NewsViewModel) data);
                    default:
                        Log.e("Cicerone", "Unknown screen: " + screenKey);
                        return null;
                }
            }

            @Override
            protected Intent createActivityIntent(Context context, String screenKey, Object data) {
                switch (screenKey) {
                    case Constants.LINK_SCREEN:
                        return new Intent(Intent.ACTION_VIEW, Uri.parse((String) data));
                    default:
                        return null;
                }
            }
        };
        mNavigator.applyCommands(new Command[]{new Replace(mItemScreenKey, model)});
    }

    @Override
    protected void onResume() {
        mNavigatorHolder.setNavigator(mNavigator);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mNavigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        mNavigator.applyCommands(new Command[]{new Back()});
    }
}
