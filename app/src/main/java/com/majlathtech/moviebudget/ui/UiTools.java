package com.majlathtech.moviebudget.ui;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class UiTools {
    public static void loadImage(ImageView targetView, String imageUrl){
        Glide.with(targetView.getContext())
                .load(imageUrl)
                .error(android.R.drawable.sym_call_missed)
                .placeholder(android.R.drawable.ic_menu_recent_history)
                .centerInside()
                .into(targetView);
    }
}
