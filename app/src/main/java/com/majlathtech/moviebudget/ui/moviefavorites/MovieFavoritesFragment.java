package com.majlathtech.moviebudget.ui.moviefavorites;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.databinding.FragmentMovieFavoritesBinding;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.UiTools;
import com.majlathtech.moviebudget.ui.error.UiError;

import java.util.List;

import javax.inject.Inject;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieFavoritesFragment extends Fragment {

    @Inject
    MovieFavoriteViewModel viewModel;

    MovieFavoritesItemAdapter movieAdapter;
    FragmentMovieFavoritesBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        injector.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieFavoritesBinding.inflate(getLayoutInflater());

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
        movieAdapter = new MovieFavoritesItemAdapter();
        binding.recyclerView.setAdapter(movieAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observableViewModel();
        viewModel.fetchFavorites();
    }

    private void observableViewModel() {
        viewModel.getFavorites().observe(getViewLifecycleOwner(), this::showFavorites);

        viewModel.getError().observe(getViewLifecycleOwner(), this::showError);
    }

    public void showFavorites(List<MovieDetail> favoriteMovieElements) {
        if (favoriteMovieElements != null && !favoriteMovieElements.isEmpty()) {
            binding.tvEmpty.setVisibility(View.GONE);
            movieAdapter.setListItems(favoriteMovieElements);
        } else {
            binding.tvEmpty.setText(R.string.no_favorites_to_show);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void showError(UiError uiError) {
        Toast.makeText(getActivity(), UiTools.exposeErrorText(binding.tvEmpty.getContext(), uiError.getTextId(), uiError.getCode()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
