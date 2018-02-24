package com.majlathtech.moviebudget.ui.movielist.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.util.NetworkUtil;
import com.majlathtech.moviebudget.ui.composite.adapter.AdapterDelegate;
import com.majlathtech.moviebudget.ui.composite.adapter.CompositeAdapter;
import com.majlathtech.moviebudget.ui.composite.model.Types;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailAdapterDelegate  extends AdapterDelegate<MovieDetailAdapterDelegate.ViewHolder> {
    private final CompositeAdapter adapter;
    private Context context;

    public MovieDetailAdapterDelegate(Context context, CompositeAdapter adapter) {
        super(context);
        this.context=context;
        this.adapter = adapter;
    }

    @Override
    public int getViewType() {
        return Types.MOVIE;
    }

    @Override
    public MovieDetailAdapterDelegate.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View contentView = getInflater().inflate(R.layout.li_movie, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieDetail result = (MovieDetail) adapter.getItem(position);
        holder.tvTitle.setText(result.getTitle());
        holder.tvBudget.setText(String.format(context.getString(R.string.money_format), result.getBudget()));
        Glide.with(context)
                .load(NetworkUtil.getPosterImageUrl(result))
                .into(holder.ivPoster);//Todo set up placeholder and error img
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvBudget)
        TextView tvBudget;
        @BindView(R.id.ivPoster)
        ImageView ivPoster;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
