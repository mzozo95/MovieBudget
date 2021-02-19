package com.majlathtech.moviebudget.ui.movielist;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    MovieListItemAdapter ma;

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.pbDownload)
    ProgressBar pbDownload;

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
        pbDownload.setVisibility(View.GONE);
        if (movieList != null && movieList.size() != 0) {
            Toast.makeText(getActivity(), R.string.list_downloaded, Toast.LENGTH_LONG).show();
            if (ma == null) {
                ma = new MovieListItemAdapter(movieList, getActivity());
            }
            ma.setListItems(movieList);
            recyclerView.setAdapter(ma);
        } else {
            showError(getString(R.string.no_content_available));
        }
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
        pbDownload.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        pbDownload.setVisibility(View.VISIBLE);
        presenter.searchMovie(etSearch.getText().toString());
        if (ma != null) {
            ma.setListItems(new ArrayList<MovieDetail>());
            ma.notifyDataSetChanged();
        }
    }
}
