package com.majlathtech.moviebudget.ui.main;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.majlathtech.moviebudget.R;

public class MainActivityNavHost extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainnavhost);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.search:
                    Navigation.findNavController(MainActivityNavHost.this, R.id.navhostFragment).navigate(R.id.action_to_search);

                    return true;
                case R.id.favorites:
                    Navigation.findNavController(MainActivityNavHost.this, R.id.navhostFragment).navigate(R.id.action_to_favorites);
                    return true;
            }
            return false;
        });
    }

    public void navigate(@IdRes int resId) {
        //https://stackoverflow.com/a/63625046
        //https://issuetracker.google.com/issues/142847973
        //https://stackoverflow.com/a/51974492
        Navigation.findNavController(this, R.id.navhostFragment).navigate(resId);//Best
    }
}
