package com.majlathtech.moviebudget.ui;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bumptech.glide.Glide;
import com.majlathtech.moviebudget.R;

public class UiTools {
    public static void loadImage(@NonNull ImageView targetView, @NonNull String imageUrl) {
        Glide.with(targetView.getContext())
                .load(imageUrl)
                .error(R.drawable.ic_error_image)
                .placeholder(R.drawable.ic_image_placeholder)
                .centerInside()
                .into(targetView);
    }

    public static String exposeErrorText(@NonNull Context context, @StringRes int resourceId, @Nullable String errorCode) {
        String result;
        try {
            result = context.getString(resourceId) + context.getString(R.string.error_code_with_value).replace("{code}", errorCode);
        } catch (Exception e) {
            result = context.getString(resourceId);
        }
        return result;
    }
}
