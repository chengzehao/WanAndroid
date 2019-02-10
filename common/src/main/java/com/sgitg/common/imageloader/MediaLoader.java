package com.sgitg.common.imageloader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sgitg.common.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

/**
 * 描述：
 *
 * @author 周麟
 * @created  2018/2/25 12:20
 */

public class MediaLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        RequestOptions options=new RequestOptions()
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_100)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }
}
