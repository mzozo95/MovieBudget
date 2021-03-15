package com.majlathtech.moviebudget.ui.moviefavorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieFavoritesFragment extends Fragment implements MovieFavoritesScreen {
    @Inject
    MovieFavoritesPresenter presenter;

    Unbinder unbinder;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    MovieFavoritesItemAdapter movieAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        injector.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_favorites, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        movieAdapter = new MovieFavoritesItemAdapter();
        recyclerView.setAdapter(movieAdapter);

        presenter.attachScreen(this);
        presenter.getFavorites();

        return view;
    }

    @Override
    public void showFavorites(List<MovieDetail> favoriteMovieElements) {
        if (favoriteMovieElements.size() > 0) {
            tvEmpty.setVisibility(View.GONE);
            movieAdapter.setListItems(favoriteMovieElements);
        } else {
            tvEmpty.setText(R.string.no_favorites_to_show);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachScreen();
        unbinder.unbind();
    }
}
