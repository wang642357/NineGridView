package com.wjx.android.ninegrid;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjx.android.ninegridview.adapter.NineGridAdapter;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * 作者：wangjianxiong 创建时间：2021/5/8
 */
public class ImageAdapter extends NineGridAdapter<String> {


    private Context mContext;

    public ImageAdapter(Context context, int layoutRes, List<String> data) {
        super(layoutRes, data);
        mContext = context;
    }

    @Override
    public void onBindData(@NonNull View itemView, int position, @NonNull String item) {
        ImageView content = itemView.findViewById(R.id.content);
        Glide.with(mContext)
                .load(item)
                .into(content);
    }

}
