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
import com.majlathtech.moviebudget.ui.UiTools;
import com.majlathtech.moviebudget.ui.error.UiError;
import com.majlathtech.moviebudget.ui.main.MainActivity;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieSearchFragment extends Fragment implements MovieSearchItemAdapter.OnItemChangedListener {
    @Inject
    MovieSearchViewModel viewModel;

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

        observableViewModel();
        viewModel.fetchFavorites();

        return view;
    }

    private void observableViewModel() {
        viewModel.getFavorites().observe(getViewLifecycleOwner(), this::showFavorites);
        viewModel.getMovies().observe(getViewLifecycleOwner(), this::showMovies);
        viewModel.getLoading().observe(getViewLifecycleOwner(), this::showLoading);
        viewModel.getError().observe(getViewLifecycleOwner(), this::showError);
    }

    @Override
    public void onFavoriteAdded(MovieDetail favorite) {
        viewModel.addToFavorites(favorite);
    }

    @Override
    public void onFavoriteDeleted(MovieDetail favorites) {
        viewModel.removeFromFavorites(favorites);
    }

    public void showFavorites(List<MovieDetail> favoriteMovieElements) {
        movieAdapter.setFavorites(new HashSet<>(favoriteMovieElements));
    }

    public void showMovies(List<MovieDetail> movieList) {
        if (movieList.size() != 0) {
            tvEmpty.setVisibility(View.GONE);
            movieAdapter.setListItems(movieList);
        } else {
            tvEmpty.setText(R.string.no_items_to_show);
            tvEmpty.setTextColor(getResources().getColor(R.color.defaultTextColor, null));
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void showError(UiError uiError) {
        switch (uiError.getType()) {
            case Network:
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText(R.string.network_error);
                tvEmpty.setTextColor(getResources().getColor(R.color.errorTextColor, null));
                break;
            case ErrorWithCode:
                Toast.makeText(getActivity(), UiTools.exposeErrorText(tvEmpty.getContext(), uiError.getTextId(), uiError.getCode()), Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getActivity(), R.string.unexpected_error_happened, Toast.LENGTH_LONG).show();
        }
    }

    public void showLoading(boolean loading) {
        pbDownload.setVisibility(loading ? View.VISIBLE : View.GONE);
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
        viewModel.searchMovie(title);
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
