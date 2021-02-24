package com.majlathtech.moviebudget.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.movielist.MovieListFragment;
import com.majlathtech.moviebudget.ui.movielist.MovieListItemAdapter;
import com.majlathtech.moviebudget.ui.movielist.MovieListPresenter;
import com.majlathtech.moviebudget.ui.movielist.MovieListScreen;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class FavoriteFragment extends Fragment implements MovieListScreen, MovieListItemAdapter.OnItemChangedListener {
    @Inject
    MovieListPresenter presenter;

    MovieListItemAdapter movieAdapter;

    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.pbDownload)
    ProgressBar pbDownload;

    public FavoriteFragment() {
        injector.inject(this);
    }

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        movieAdapter = new MovieListItemAdapter();
        movieAdapter.setListener(this);
        recyclerView.setAdapter(movieAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachScreen(this);
        presenter.getFavorites();
    }

    @Override
    public void onFavoriteAdded(MovieDetail favorite) {
        presenter.addToFavorites(favorite);//safeArgs?https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args
        Navigation.findNavController(btnSearch).navigate(R.id.search);//todo ok its working!
    }

    @Override
    public void onFavoriteDeleted(MovieDetail favorites) {
        presenter.removeFromFavorites(favorites);
    }

    @Override
    public void showFavorites(List<MovieDetail> favoriteMovieElements) {
        movieAdapter.setFavorites(new HashSet<>(favoriteMovieElements));
    }

    @Override
    public void showMovies(List<MovieDetail> movieList) {
        hideLoading();
        if (movieList.size() != 0) {
            tvEmpty.setVisibility(View.GONE);
            movieAdapter.setListItems(movieList);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(String errorMsg) {
        hideLoading();
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    public void hideLoading() {
        pbDownload.setVisibility(View.GONE);
    }

    public void showLoading() {
        pbDownload.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachScreen();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        showLoading();
        presenter.searchMovie(etSearch.getText().toString());
        movieAdapter.clearItems();
    }
}
