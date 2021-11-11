package com.majlathtech.moviebudget.ui.movielist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.databinding.FragmentMovieSearchBinding;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.UiTools;
import com.majlathtech.moviebudget.ui.error.UiError;
import com.majlathtech.moviebudget.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieSearchFragment extends Fragment implements MovieSearchItemAdapter.OnItemChangedListener {

    @Inject
    MovieSearchViewModel viewModel;

    MovieSearchItemAdapter movieAdapter;
    FragmentMovieSearchBinding binding;
    Disposable favoriteSubsDisposable;

    @Override
    public void onAttach(@NonNull Context context) {
        injector.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieSearchBinding.inflate(getLayoutInflater());

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
        movieAdapter = new MovieSearchItemAdapter();
        movieAdapter.setListener(this);
        binding.recyclerView.setAdapter(movieAdapter);

        binding.btnSearch.setOnClickListener(view1 -> {
            hideKeyboard();
            searchMovies(binding.etSearch.getText().toString());
        });

        binding.btnFavorites.setOnClickListener(view12 -> {
            if (getActivity() instanceof MainActivity) {
                hideKeyboard();
                ((MainActivity) getActivity()).replaceFragment(MainActivity.FAVORITE_SCREEN);
            }
        });

        observableViewModel();

        //in case a favorite is changed in the Favorites screen, that will fire an event on favoritesChanged, note: onResume / onStart is not called here when favorites screen is popped
        viewModel.fetchFavorites();
        if (getActivity() instanceof MainActivity) {
            favoriteSubsDisposable = ((MainActivity) getActivity()).favoritesChanged.subscribe(o -> viewModel.fetchFavorites());
        }

        return binding.getRoot();
    }

    @Override
    public void onFavoriteAdded(MovieDetail favorite) {
        viewModel.addToFavorites(favorite);
    }

    @Override
    public void onFavoriteDeleted(MovieDetail favorites) {
        viewModel.removeFromFavorites(favorites);
    }

    private void observableViewModel() {
        viewModel.getFavorites().observe(getViewLifecycleOwner(), this::showFavorites);
        viewModel.getMovies().observe(getViewLifecycleOwner(), this::showMovies);
        viewModel.getLoading().observe(getViewLifecycleOwner(), this::showLoading);
        viewModel.getError().observe(getViewLifecycleOwner(), this::showError);
    }

    private void showFavorites(List<MovieDetail> favoriteMovieElements) {
        movieAdapter.setFavorites(new HashSet<>(favoriteMovieElements));
    }

    private void showMovies(List<MovieDetail> movieList) {
        if (movieList.isEmpty()) {
            binding.tvEmpty.setText(R.string.no_items_to_show);
            binding.tvEmpty.setTextColor(getResources().getColor(R.color.defaultTextColor, null));
            binding.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            movieAdapter.setListItems(movieList);
        }
    }

    private void showError(UiError uiError) {
        switch (uiError.getType()) {
            case Network:
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.tvEmpty.setText(R.string.network_error);
                binding.tvEmpty.setTextColor(getResources().getColor(R.color.errorTextColor, null));
                break;
            case ErrorWithCode:
                Toast.makeText(getActivity(), UiTools.exposeErrorText(binding.tvEmpty.getContext(), uiError.getTextId(), uiError.getCode()), Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getActivity(), R.string.unexpected_error_happened, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoading(boolean loading) {
        binding.pbDownload.setVisibility(loading ? View.VISIBLE : View.GONE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (favoriteSubsDisposable != null) {
            favoriteSubsDisposable.dispose();
        }
        binding = null;
    }
}
