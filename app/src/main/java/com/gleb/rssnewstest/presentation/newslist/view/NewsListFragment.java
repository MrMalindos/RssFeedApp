package com.gleb.rssnewstest.presentation.newslist.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.R;
import com.gleb.rssnewstest.presentation.newslist.adapters.NewsListRecyclerViewAdapter;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;
import com.gleb.rssnewstest.presentation.newslist.presenter.NewsListPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsListFragment extends MvpAppCompatFragment implements INewsListView, NewsListRecyclerViewAdapter.OnItemClickListener {
    public static final String TAG = "MY_TAG";
    private static final String MODE = "MODE";
    @InjectPresenter
    NewsListPresenter mNewsListPresenter;

    @BindView(R.id.news_list_recycler_view)
    RecyclerView mNewsListRecyclerView;
    @BindView(R.id.empty_states_layout)
    LinearLayout mEmptyListTxt;
    @BindView(R.id.news_list_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Unbinder mUnbinder;
    private String mMode;
    private NewsListRecyclerViewAdapter mNewsListRecyclerViewAdapter;

    public static NewsListFragment newInstance(String mode) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMode = getArguments().getString(MODE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return Objects.requireNonNull(inflater).inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

        //RecyclerView settings
        mNewsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsListRecyclerViewAdapter = new NewsListRecyclerViewAdapter(this);
        mNewsListRecyclerView.setAdapter(mNewsListRecyclerViewAdapter);

        mEmptyListTxt.setVisibility(View.GONE);

        if (mMode.equals(Constants.ALL)) {
            //SwipeRefreshLayout settings
            mSwipeRefreshLayout.setEnabled(true);
            mSwipeRefreshLayout.setOnRefreshListener(mNewsListPresenter::updateList);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            mNewsListPresenter.init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMode.equals(Constants.FAVOURITE)) {
            mNewsListPresenter.initFavourite();
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void showEmptyList() {
        mNewsListRecyclerView.setVisibility(View.GONE);
        mEmptyListTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNews(NewsViewModel model) {
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyListTxt.setVisibility(View.GONE);
        mNewsListRecyclerView.setVisibility(View.VISIBLE);
        mNewsListRecyclerViewAdapter.updateData(model);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showLoadError() {
        final AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.error_dialog_title)
                .setMessage(R.string.error_dialog_message)
                .setPositiveButton(R.string.error_dialog_button, (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false)
                .create();
        dialog.show();
    }

    @Override
    public void changeItem(NewsViewModel model, int adapterPosition) {
        mNewsListRecyclerViewAdapter.updateItem(model, adapterPosition);
    }

    @Override
    public void clearAdapter() {
        mNewsListRecyclerViewAdapter.clearList();
    }

    @Override
    public void onItemClick(NewsViewModel model, int adapterPosition) {
        mNewsListPresenter.onNewsItemClicked(model, adapterPosition);
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
