package com.thic.mynotesjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class ImageHelper {

    public static void forView(Context context, ImageView imageView, String url){

        Glide.with(context)
            .load("http://192.168.1.38:8888/services/"+url)
                .centerCrop()
                .into(imageView);
    }
}
