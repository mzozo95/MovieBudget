package com.majlathtech.moviebudget.ui.movielist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.ui.movielist.adapter.MovieListItemAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.majlathtech.moviebudget.MovieBudgetApplication.injector;

public class MovieListFragment extends Fragment implements MovieListScreen {
    @Inject
    MovieListPresenter presenter;

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @Inject
    public MovieListFragment() {
        injector.inject(this);
    }

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachScreen(this);
    }

    @Override
    public void onStop() {
        presenter.detachScreen();
        super.onStop();
    }

    @Override
    public void showMovies(List<MovieDetail> movieList) {
        if (movieList != null) {
            MovieListItemAdapter ma = new MovieListItemAdapter(movieList, getActivity());
            recyclerView.setAdapter(ma);
        }
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        presenter.searchMovie(etSearch.getText().toString());
        MovieListItemAdapter ma = new MovieListItemAdapter(new ArrayList<MovieDetail>(), getActivity());
        recyclerView.setAdapter(ma);
    }
}
