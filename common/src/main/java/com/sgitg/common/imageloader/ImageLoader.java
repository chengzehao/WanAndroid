package com.sgitg.common.imageloader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sgitg.common.R;

/**
 * ImageLoader
 *
 * @author 周麟
 * @created 2018/1/4 11:20
 */
public class ImageLoader {
    public static void displayImage(ImageView iv, String url) {
        RequestOptions options=new RequestOptions()
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_grey_300)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(iv.getContext()).load(url).apply(options).into(iv);
    }
}