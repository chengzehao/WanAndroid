package com.sgitg.common.common;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sgitg.common.R;
import com.sgitg.common.imageloader.ImageLoader;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/11/21 10:01
 */
public class PhotosAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PhotosAdapter() {
        super(R.layout.item_photography);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageLoader.displayImage((ImageView) helper.getView(R.id.image), item);
    }
}
