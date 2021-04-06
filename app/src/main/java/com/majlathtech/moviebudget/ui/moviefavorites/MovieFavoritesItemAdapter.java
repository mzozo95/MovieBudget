package com.majlathtech.moviebudget.ui.moviefavorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.databinding.ItemMovieDetailBinding;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.tools.MovieTools;
import com.majlathtech.moviebudget.ui.UiTools;

import java.util.List;

public class MovieFavoritesItemAdapter extends RecyclerView.Adapter<MovieFavoritesItemAdapter.MovieViewHolder> {
    private List<MovieDetail> items;

    public void setListItems(List<MovieDetail> list) {
        items = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemMovieDetailBinding binding =
                ItemMovieDetailBinding.inflate(
                        LayoutInflater.from(viewGroup.getContext()),
                        viewGroup,
                        false);

        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieDetail movie = items.get(position);
        Context context = holder.binding.tvTitle.getContext();
        holder.binding.tvTitle.setText(movie.getTitle());
        holder.binding.tvBudget.setText(String.format(context.getString(R.string.money_format), movie.getBudget()));
        holder.binding.cbFavorite.setVisibility(View.GONE);
        UiTools.loadImage(holder.binding.ivPoster, MovieTools.generatePosterImageUrl(movie));
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieDetailBinding binding;

        public MovieViewHolder(ItemMovieDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

