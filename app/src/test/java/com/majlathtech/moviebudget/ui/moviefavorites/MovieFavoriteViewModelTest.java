package com.majlathtech.moviebudget.ui.moviefavorites;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.majlathtech.moviebudget.network.model.MovieDetail;
import com.majlathtech.moviebudget.network.service.RxSchedulers;
import com.majlathtech.moviebudget.repository.FavoriteDao;
import com.majlathtech.moviebudget.ui.error.UiError;

import junit.framework.TestCase;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MovieFavoriteViewModelTest extends TestCase {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    MovieFavoriteViewModel viewModel;

    @Mock
    FavoriteDao favoriteDao;

    @Mock
    Observer<List<MovieDetail>> favoritesObserver;
    @Mock
    Observer<UiError> errorObserver;

    @Before
    public void init() {
        viewModel = new MovieFavoriteViewModel(favoriteDao, RxSchedulers.TEST_SCHEDULER);
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

    @Test
    public void testFetchFavoritesError() {
        when(favoriteDao.getAll()).thenReturn(Single.error(new Throwable("Error path")));
        viewModel.getFavorites().observeForever(favoritesObserver);
        viewModel.getError().observeForever(errorObserver);

        viewModel.fetchFavorites();

        verify(errorObserver).onChanged(any());
    }

    @Test
    public void testDeleteAndReFetchFavorites() {
        MovieDetail data = new MovieDetail();
        data.setId(12);
        when(favoriteDao.delete(any())).thenReturn(Completable.complete());
        when(favoriteDao.getAll()).thenReturn(Single.just(new ArrayList<MovieDetail>()));

        ArgumentCaptor<MovieDetail> movieCaptor = ArgumentCaptor.forClass(MovieDetail.class);

        viewModel.delete(data);

        verify(favoriteDao).delete(movieCaptor.capture());
        assertEquals(movieCaptor.getValue().getId(), 12);
        verify(favoriteDao).getAll();
    }

}