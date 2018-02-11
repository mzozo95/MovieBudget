package com.majlathtech.moviebudget.ui.movielist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class MovieListItemAdapter extends RecyclerView.Adapter<MovieListItemAdapter.MovieViewHolder> {
    private List<MovieDetail> rList = new ArrayList<>();
    private Context context;

    private MovieListItemAdapter() {
    }

    public MovieListItemAdapter(List<MovieDetail> r, Context context) {
        this.rList = r;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return rList.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_detail, viewGroup, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieDetail result = rList.get(position);
        holder.tvTitle.setText("" + result.getTitle());
        holder.tvBudget.setText("" + result.getBudget() + "$");
        Glide.with(context)
                .load(NetworkUtil.getPosterImageUrl(result))
                .into(holder.ivPoster);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvBudget;
        ImageView ivPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }
}

