package com.majlathtech.moviebudget.ui.main;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.majlathtech.moviebudget.R;

public class MainActivityNavHost extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainnavhost);
        setupNavigation();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setupNavigation();
    }

    private void setupNavigation() {
        //https://developer.android.com/guide/navigation/navigation-ui#bottom_navigation
        //https://developer.android.com/guide/navigation/navigation-ui#Tie-navdrawer
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navhostFragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    private void navigate(@IdRes int resId) {
        //https://stackoverflow.com/a/63625046
        //https://issuetracker.google.com/issues/142847973
        //https://stackoverflow.com/a/51974492
        Navigation.findNavController(this, R.id.navhostFragment).navigate(resId);//Best
    }
}
