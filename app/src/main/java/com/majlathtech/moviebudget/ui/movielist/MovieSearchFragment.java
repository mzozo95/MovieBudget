package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.main.MainActivity;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieSearchFragment extends Fragment implements MovieSearchScreen, MovieSearchItemAdapter.OnItemChangedListener {
    @Inject
    MovieSearchPresenter presenter;

    Unbinder unbinder;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.btnFavorites)
    Button btnFavorites;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.pbDownload)
    ProgressBar pbDownload;

    MovieSearchItemAdapter movieAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        injector.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        movieAdapter = new MovieSearchItemAdapter();
        movieAdapter.setListener(this);
        recyclerView.setAdapter(movieAdapter);

        presenter.attachScreen(this);
        presenter.getFavorites();

        return view;
    }

    @Override
    public void onFavoriteAdded(MovieDetail favorite) {
        presenter.addToFavorites(favorite);
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
            tvEmpty.setText(R.string.no_items_to_show);
            tvEmpty.setTextColor(getResources().getColor(R.color.defaultTextColor, null));
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(String errorMsg) {
        hideLoading();
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {
        hideLoading();
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(R.string.network_error);
        tvEmpty.setTextColor(getResources().getColor(R.color.errorTextColor, null));
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

    private void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void searchMovies(String title) {
        showLoading();
        presenter.searchMovie(title);
        movieAdapter.clearItems();
    }

    @OnClick({R.id.btnSearch, R.id.btnFavorites})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                hideKeyboard();
                searchMovies(etSearch.getText().toString());
                break;
            case R.id.btnFavorites:
                if (getActivity() instanceof MainActivity) {
                    hideKeyboard();
                    ((MainActivity) getActivity()).replaceFragment(MainActivity.FAVORITE_SCREEN);
                }
                break;
        }
    }
}
