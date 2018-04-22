package com.gleb.rssnewstest.presentation.news.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.gleb.rssnewstest.R;
import com.gleb.rssnewstest.presentation.news.presenter.NewsPresenter;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFragment extends MvpAppCompatFragment implements INewsView, View.OnClickListener {
    private static final String ITEM_ID = "ITEM_ID";
    private Unbinder mUnbinder;
    private NewsViewModel mModel;

    @InjectPresenter
    NewsPresenter mNewsPresenter;

    @BindView(R.id.title_txt)
    TextView mTitleTxt;
    @BindView(R.id.image_view)
    CircleImageView mImage;
    @BindView(R.id.link_btn)
    Button mMoreBtn;
    @BindView(R.id.favourite_btn)
    Button mFavouriteBtn;
    @BindView(R.id.news_toolbar)
    Toolbar mToolbar;

    public NewsFragment() {
    }

    public static NewsFragment newInstance(NewsViewModel model) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ITEM_ID, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mModel = getArguments().getParcelable(ITEM_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

        //Toolbar + menu settings
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        mMoreBtn.setOnClickListener(this);
        mFavouriteBtn.setOnClickListener(this);

        setData();
    }

    private void setData() {
        mTitleTxt.setText(mModel.getTitle());
        if (mModel.getMode().isEmpty()) {
            mFavouriteBtn.setText(R.string.add_favourite_btn);
        } else {
            mFavouriteBtn.setText(R.string.remove_favourite_btn);
        }
        if (mModel.getImage().equals("def")) {
            Picasso.get()
                    .load(R.drawable.ic_default_icon)
                    .into(mImage);
        } else {
            Picasso.get()
                    .load(mModel.getImage())
                    .into(mImage);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.link_btn:
                mNewsPresenter.onLinkButtonClicked(mModel.getLink());
                break;
            case R.id.favourite_btn:
                mNewsPresenter.onFavouriteBtnClicked(mModel);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNewsPresenter.onBackItemSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void changeFavouriteBtn(int textId) {
        mFavouriteBtn.setText(textId);
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
