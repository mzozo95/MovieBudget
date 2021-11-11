package com.majlathtech.moviebudget.ui.movielist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.network.service.RxSingleSchedulers;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.ui.error.UiError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieSearchViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    MovieSearchViewModel viewModel;

    @Mock
    MovieService movieService;
    @Mock
    FavoriteDao favoriteDao;

    @Mock
    Observer<Boolean> loadingObserver;
    @Mock
    Observer<List<MovieDetail>> favoritesObserver;
    @Mock
    Observer<UiError> errorObserver;

    @Before
    public void init() {
        viewModel = new MovieSearchViewModel(movieService, favoriteDao, RxSingleSchedulers.TEST_SCHEDULER);
    }

    @Test
    public void testFetchFavoritesShowsAndHidesLoading() {
        when(favoriteDao.getAll()).thenReturn(Single.just(new ArrayList<>()));
        viewModel.getLoading().observeForever(loadingObserver);

        viewModel.fetchFavorites();

        assertTrue(viewModel.getLoading().hasObservers());
        verify(loadingObserver).onChanged(true);
        verify(loadingObserver).onChanged(false);
    }

    @Test
    public void testFetchFavoritesShowsAndHidesLoadingOnError() {
        when(favoriteDao.getAll()).thenReturn(Single.error(new Throwable("Error path")));
        viewModel.getLoading().observeForever(loadingObserver);

        viewModel.fetchFavorites();

        verify(loadingObserver).onChanged(true);
        verify(loadingObserver).onChanged(false);
    }

    @Test
    public void testFetchFavoritesError() {
        when(favoriteDao.getAll()).thenReturn(Single.error(new Throwable("Error path")));
        viewModel.getFavorites().observeForever(favoritesObserver);
        viewModel.getError().observeForever(errorObserver);

        viewModel.fetchFavorites();

        verify(errorObserver).onChanged(any());
    }

    @Test
    public void testFetchFavoritesShowsAllFavorites() {
        List<MovieDetail> data = new ArrayList<>();
        data.add(new MovieDetail());
        data.add(new MovieDetail());
        when(favoriteDao.getAll()).thenReturn(Single.just(data));
        viewModel.getFavorites().observeForever(favoritesObserver);

        viewModel.fetchFavorites();

        verify(favoritesObserver).onChanged(data);
    }
}