package com.majlathtech.moviebudget.ui;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.majlathtech.moviebudget.R;

public class UiTools {
    public static void loadImage(ImageView targetView, String imageUrl){
        Glide.with(targetView.getContext())
                .load(imageUrl)
                .error(R.drawable.ic_error_image)
                .placeholder(R.drawable.ic_image_placeholder)
                .centerInside()
                .into(targetView);
    }
}
