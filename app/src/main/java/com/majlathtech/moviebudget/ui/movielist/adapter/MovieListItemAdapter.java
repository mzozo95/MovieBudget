package com.majlathtech.moviebudget.ui.movielist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.util.MovieUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListItemAdapter extends RecyclerView.Adapter<MovieListItemAdapter.MovieViewHolder> {
    private List<MovieDetail> items;


    public void setListItems(List<MovieDetail> list) {
        items = list;
        notifyDataSetChanged();
    }

    public void clearItems() {
        notifyItemRangeRemoved(0, getItemCount());
        items = null;
    }

    public MovieListItemAdapter() {
    }

    public MovieListItemAdapter(List<MovieDetail> r) {
        this.items = r;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_detail, viewGroup, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieDetail result = items.get(position);
        Context context = holder.tvTitle.getContext();
        holder.tvTitle.setText(result.getTitle());
        holder.tvBudget.setText(String.format(context.getString(R.string.money_format), result.getBudget()));
        Glide.with(context)
                .load(MovieUtil.generatePosterImageUrl(result))
                .into(holder.ivPoster);//Todo set up placeholder and error img
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvBudget)
        TextView tvBudget;
        @BindView(R.id.ivPoster)
        ImageView ivPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

