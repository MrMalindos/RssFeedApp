package com.gleb.rssnewstest.presentation.newslist.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gleb.rssnewstest.Constants;
import com.gleb.rssnewstest.R;
import com.gleb.rssnewstest.presentation.newslist.model.NewsViewModel;
import com.gleb.rssnewstest.presentation.newslist.view.NewsListFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<NewsListRecyclerViewAdapter.NewsListHolder> {
    private List<NewsViewModel> mNewsList;
    private OnItemClickListener mOnItemClickListener;
    private static int position = 0;

    public NewsListRecyclerViewAdapter(NewsListFragment mOnItemClickListener) {
        this.mNewsList = new ArrayList<>();
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public NewsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_list_row, parent, false);
        return new NewsListHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListHolder holder, int position) {
        NewsViewModel mCurrentNews = mNewsList.get(position);
        holder.bindNews(mCurrentNews);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void updateData(NewsViewModel model) {
        mNewsList.add(model);
        notifyItemChanged(position++);
    }

    public void updateItem(NewsViewModel model, int adapterPosition) {
        mNewsList.remove(adapterPosition);
        mNewsList.add(adapterPosition, model);
        notifyItemChanged(adapterPosition);
    }

    public void clearList() {
        mNewsList.clear();
        position = 0;
    }

    class NewsListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.news_list_row_title)
        TextView mTitleTxt;
        @BindView(R.id.news_list_row_img)
        CircleImageView mImageView;
        @BindView(R.id.favourite_img)
        ImageView mFavouriteImg;
        private NewsViewModel mModel;

        NewsListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(mModel, getAdapterPosition());
        }

        void bindNews(NewsViewModel model) {
            this.mModel = model;
            mTitleTxt.setText(model.getTitle());
            if (mModel.getMode().equals(Constants.FAVOURITE)) {
                mFavouriteImg.setVisibility(View.VISIBLE);
            } else {
                mFavouriteImg.setVisibility(View.GONE);
            }
            if (mModel.getImage().equals("def")) {
                Picasso.get()
                        .load(R.drawable.ic_default_icon)
                        .into(mImageView);
            } else {
                Picasso.get()
                        .load(mModel.getImage())
                        .into(mImageView);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NewsViewModel model, int adapterPosition);
    }
}
