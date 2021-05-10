package com.wjx.android.ninegridview.adapter;

import android.view.View;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作者：wangjianxiong 创建时间：2021/5/10
 */
public abstract class NineGridAdapter<T> {

    private final int mLayoutRes;

    private final List<T> data;

    public NineGridAdapter(int layoutRes, List<T> data) {
        mLayoutRes = layoutRes;
        this.data = data;
    }

    @LayoutRes
    int getItemLayoutId() {
        return mLayoutRes;
    }

    @Nullable
    List<T> getData() {
        return data;
    }

    public abstract void onBindData(@NonNull View itemView, int position, @NonNull T item);

}
