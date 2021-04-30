package com.wjx.android.ninegridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：wangjianxiong 创建时间：2021/4/30
 */
public abstract class NineGridAdapter<T> extends RecyclerView.Adapter<NineGridViewHolder> {

    int mLayoutResId;

    int minCount;

    private List<T> data = new ArrayList<>();

    private OnGridItemClickListener
            mOnGridItemClickListener;

    private Context mContext;

    public NineGridAdapter(@LayoutRes int layoutResId) {
        mLayoutResId = layoutResId;
    }

    public NineGridAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this.data = data;
        mLayoutResId = layoutResId;
    }

    @NonNull
    @Override
    public NineGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
        NineGridViewHolder viewHolder = new NineGridViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnGridItemClickListener != null) {
                    mOnGridItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NineGridViewHolder holder, int position) {
        onBindData(holder.itemView, data.get(position));
    }

    public abstract void onBindData(@NonNull View itemView, @NonNull T item);

    public T getItem(@IntRange(from = 0) int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return minCount > 0 ? Math.min(minCount, data.size()) : data.size();
    }

    @NonNull
    public List<T> getData() {
        return data;
    }

    public void setList(@Nullable List<T> data) {
        this.data = data == null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    public void addData(T data) {

    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public void collapse() {

    }

    public void expand() {

    }

    public Context getContext() {
        return mContext;
    }

    public void setOnItemClickListener(OnGridItemClickListener listener) {
        this.mOnGridItemClickListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }
}

