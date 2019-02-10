package com.sgitg.common.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sgitg.common.R;
import com.sgitg.common.imageloader.ImageLoader;

import java.util.ArrayList;

/**
 * PhotographyAdapter 通用拍照模块适配器
 *
 * @author 周麟
 * @created 2018/1/4 10:34
 */
public class PhotographyAdapter extends RecyclerView.Adapter<PhotographyAdapter.ViewHolder> {
    private ArrayList<String> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private int maxNum = 6;

    public PhotographyAdapter(Context mContext, int maxNum) {
        this.maxNum = maxNum;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list.add(createAddEntry());
    }

    public void reloadList(ArrayList<String> data) {
        if (data != null) {
            list.clear();
            list.addAll(data);
            list.add(createAddEntry());
        } else {
            list.clear();
        }
        notifyDataSetChanged();
    }

    public void appendPhoto(String entry) {
        if (entry != null) {
            list.add(list.size() - 1, entry);
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getData() {
        return new ArrayList<>(list.subList(0, list.size() - 1));
    }

    private String createAddEntry() {
        return "Add";
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_photography, viewGroup, false), i);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i == list.size() - 1) {
            if (list.size() >= maxNum + 1) {
                viewHolder.mImageView.setVisibility(View.GONE);
            } else {
                viewHolder.mImageView.setImageResource(R.mipmap.get_photo);
            }
        } else {
            ImageLoader.displayImage(viewHolder.mImageView, list.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView mImageView;
        private int position;

        ViewHolder(View itemView, int position) {
            super(itemView);
            this.position = position;
            mImageView = itemView.findViewById(R.id.image);
            mImageView.setOnClickListener(this);
            mImageView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mlistener != null) {
                mlistener.onItemClicked(position);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(view, getAdapterPosition());
            }
            return true;
        }
    }

    private OnItmeClickListener mlistener;

    public void setOnItemClickListener(OnItmeClickListener mlistener) {
        this.mlistener = mlistener;
    }

    public interface OnItmeClickListener {
        /**
         * 条目单击回调
         *
         * @param position 点击条目位置
         */
        void onItemClicked(int position);

    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        /**
         * 条目长按回调
         *
         * @param view     被长按条目视图
         * @param position 被长按条目位置
         */
        void onItemLongClick(View view, int position);
    }
}
