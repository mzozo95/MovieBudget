package com.majlathtech.moviebudget.ui.movielist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.service.MovieService;
import com.majlathtech.moviebudget.network.service.RxSchedulers;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.ui.error.UiError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
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
    Observer<List<MovieDetail>> moviesObserver;
    @Mock
    Observer<UiError> errorObserver;

    @Before
    public void init() {
        viewModel = new MovieSearchViewModel(movieService, favoriteDao, RxSchedulers.TEST_SCHEDULER);
    }

    //Fetch favorites

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

    //Search movies

    @Test
    public void testSearchMovieShowsAndHidesLoading() {
        when(movieService.searchMovie(any())).thenReturn(Single.just(new ArrayList<>()));
        viewModel.getLoading().observeForever(loadingObserver);

        viewModel.searchMovie("title");

        assertTrue(viewModel.getLoading().hasObservers());
        verify(loadingObserver).onChanged(true);
        verify(loadingObserver).onChanged(false);
    }

    @Test
    public void testSearchMovieShowsAndHidesLoadingOnError() {
        when(movieService.searchMovie(any())).thenReturn(Single.error(new Throwable("Error path")));
        viewModel.getLoading().observeForever(loadingObserver);

        viewModel.searchMovie("title");

        verify(loadingObserver).onChanged(true);
        verify(loadingObserver).onChanged(false);
    }

    @Test
    public void testSearchMovieNullInput() {
        viewModel.getMovies().observeForever(moviesObserver);

        viewModel.searchMovie(null);

        verify(moviesObserver).onChanged(new ArrayList<>());
    }

    @Test
    public void testSearchMovieEmptyInput() {
        viewModel.getMovies().observeForever(moviesObserver);

        viewModel.searchMovie("");

        verify(moviesObserver).onChanged(new ArrayList<>());
    }

    @Test
    public void testSearchMovieSuccess() {
        List<MovieDetail> data = new ArrayList<>();
        data.add(new MovieDetail());
        data.add(new MovieDetail());
        when(movieService.searchMovie(any())).thenReturn(Single.just(data));
        viewModel.getMovies().observeForever(moviesObserver);

        viewModel.searchMovie("someTitle");

        verify(moviesObserver).onChanged(data);
    }

    @Test
    public void testAddFavorites() {
        MovieDetail data = new MovieDetail();
        data.setId(12);
        when(favoriteDao.insertAll(any())).thenReturn(Completable.complete());
        ArgumentCaptor<MovieDetail> movieCaptor = ArgumentCaptor.forClass(MovieDetail.class);

        viewModel.addToFavorites(data);

        verify(favoriteDao).insertAll(movieCaptor.capture());
        assertEquals(movieCaptor.getValue().getId(), 12);
    }

    @Test
    public void testRemoveFavorites() {
        MovieDetail data = new MovieDetail();
        data.setId(12);
        when(favoriteDao.delete(any())).thenReturn(Completable.complete());
        ArgumentCaptor<MovieDetail> movieCaptor = ArgumentCaptor.forClass(MovieDetail.class);

        viewModel.removeFromFavorites(data);

        verify(favoriteDao).delete(movieCaptor.capture());
        assertEquals(movieCaptor.getValue().getId(), 12);
    }
}