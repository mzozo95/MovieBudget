package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.util.MovieUtil;
import com.majlathtech.moviebudget.ui.UiTools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListItemAdapter extends RecyclerView.Adapter<MovieListItemAdapter.MovieViewHolder> {
    private List<MovieDetail> items;
    private Set<MovieDetail> favorites = new HashSet<>();
    private OnItemChangedListener listener;

    public void setListItems(List<MovieDetail> list) {
        items = list;
        notifyDataSetChanged();
    }

    public void setFavorites(Set<MovieDetail> favorites) {
        this.favorites = favorites;
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
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_detail, viewGroup, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieDetail movie = items.get(position);
        Context context = holder.tvTitle.getContext();
        holder.tvTitle.setText(movie.getTitle());
        holder.tvBudget.setText(String.format(context.getString(R.string.money_format), movie.getBudget()));
        holder.cbFavorite.setOnCheckedChangeListener(null);
        holder.cbFavorite.setChecked(favorites.contains(movie));
        holder.cbFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
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
        UiTools.loadImage(holder.ivPoster, MovieUtil.generatePosterImageUrl(movie));
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvBudget)
        TextView tvBudget;
        @BindView(R.id.ivPoster)
        ImageView ivPoster;
        @BindView(R.id.cbFavorite)
        CheckBox cbFavorite;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemChangedListener {

        void onFavoriteAdded(MovieDetail favorite);

        void onFavoriteDeleted(MovieDetail favorites);
    }
}

