package com.majlathtech.moviebudget.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jakewharton.rxrelay3.PublishRelay;
import com.majlathtech.moviebudget.R;
import com.majlathtech.moviebudget.ui.moviefavorites.MovieFavoritesFragment;
import com.majlathtech.moviebudget.ui.movielist.MovieSearchFragment;

public class MainActivity extends FragmentActivity {
    public static final String SEARCH_SCREEN = "SEARCH_SCREEN";
    public static final String FAVORITE_SCREEN = "FAVORITE_SCREEN";
    private String currentDisplayedFragment = SEARCH_SCREEN;
    //Notify components in this activity if favorites changed:
    public final PublishRelay<Object> favoritesChanged = PublishRelay.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(currentDisplayedFragment, false);
    }

    private void replaceFragment(String tag, boolean addToBackStack) {
        currentDisplayedFragment = tag;
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            switch (tag) {
                case SEARCH_SCREEN:
                    fragment = new MovieSearchFragment();
                    break;
                case FAVORITE_SCREEN:
                    fragment = new MovieFavoritesFragment();
                    break;
            }
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction()
                    .add(R.id.container, fragment);

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        }
    }

    public void replaceFragment(String tag) {
        replaceFragment(tag, true);
    }
}
