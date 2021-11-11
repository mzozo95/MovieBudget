package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.databinding.ItemMovieDetailBinding;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.tools.MovieTools;
import com.majlathtech.moviebudget.ui.UiTools;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieSearchItemAdapter extends RecyclerView.Adapter<MovieSearchItemAdapter.MovieViewHolder> {
    private List<MovieDetail> items;
    private Set<MovieDetail> favorites = new HashSet<>();
    private OnItemChangedListener listener;

    public void setListItems(List<MovieDetail> list) {
        items = list;
        notifyDataSetChanged();
    }

    public void setFavorites(Set<MovieDetail> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void setListener(OnItemChangedListener listener) {
        this.listener = listener;
    }

    public void clearItems() {
        notifyItemRangeRemoved(0, getItemCount());
        items = null;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
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
        holder.binding.cbFavorite.setOnCheckedChangeListener(null);
        holder.binding.cbFavorite.setChecked(favorites.contains(movie));
        holder.binding.cbFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (isChecked) {
                        favorites.add(movie);
                        if (listener != null) {
                            listener.onFavoriteAdded(movie);
                        }
                    } else {
                        favorites.remove(movie);
                        if (listener != null) {
                            listener.onFavoriteDeleted(movie);
                        }
                    }
                }
        );
        UiTools.loadImage(holder.binding.ivPoster, MovieTools.generatePosterImageUrl(movie));
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieDetailBinding binding;

        public MovieViewHolder(ItemMovieDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemChangedListener {

        void onFavoriteAdded(MovieDetail favorite);

        void onFavoriteDeleted(MovieDetail favorites);
    }
}

