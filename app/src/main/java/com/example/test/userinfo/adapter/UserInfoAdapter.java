package com.example.test.userinfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.userinfo.R;
import com.example.test.userinfo.model.network.UserInfoResponse;
import com.example.test.userinfo.ui.ItemClickListener;
import com.example.test.userinfo.ui.OnLoadMoreListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserInfoResponse.Results> usersList;
    private Context context;
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;
    private boolean loading;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;
    private ItemClickListener itemClickListener;

    public UserInfoAdapter(RecyclerView recyclerView, List<UserInfoResponse.Results> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            if (onLoadMoreListener != null) {
                                loading = true;
                                onLoadMoreListener.onLoadMore();
                            }

                        }
                    }
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_info_row,parent,false);
            return new InfoHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof InfoHolder) {
            InfoHolder infoHolder = (InfoHolder) holder;

            infoHolder.fullNameTextView.setText(usersList.get(position).getFullName());
            Glide.with(context).load(usersList.get(position).getPicture().getLargeImage()).into(infoHolder.profileImageView);

            infoHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(position, infoHolder.profileImageView);
                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

     }

    @Override
    public int getItemViewType(int position) {
        return usersList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    @Override
    public int getItemCount() {
        return usersList == null ? 0 : usersList.size();
    }

    class InfoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_full_name)
        TextView fullNameTextView;

        @BindView(R.id.user_picture)
        ImageView profileImageView;

        @BindView(R.id.card_view)
        CardView cardView;

        InfoHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
