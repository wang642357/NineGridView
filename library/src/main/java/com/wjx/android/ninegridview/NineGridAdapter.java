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

    private List<T> foldData = new ArrayList<>();

    private OnGridItemClickListener mOnGridItemClickListener;

    private Context mContext;

    private boolean mExpandEnable;

    public NineGridAdapter(@LayoutRes int layoutResId) {
        mLayoutResId = layoutResId;
    }

    @NonNull
    @Override
    public NineGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SquareLayout squareLayout = new SquareLayout(parent.getContext());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutResId, squareLayout, false);
        squareLayout.addView(view);
        NineGridViewHolder viewHolder = new NineGridViewHolder(squareLayout);
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
        onBindData(holder.itemView, position, data.get(position));
    }

    public abstract void onBindData(@NonNull View itemView, int position, @NonNull T item);

    public T getItem(@IntRange(from = 0) int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    public List<T> getData() {
        return data;
    }

    public void setList(@Nullable List<T> dataList) {
        List<T> result = dataList == null ? new ArrayList<>() : dataList;
        if (mExpandEnable) {
            if (minCount >= result.size()) {
                data.clear();
                data.addAll(result);
            } else {
                data.clear();
                foldData.clear();
                List<T> list = result.subList(0, minCount);
                List<T> list2 = result.subList(minCount, result.size());
                data.addAll(list);
                foldData.addAll(list2);
            }
        } else {
            data.clear();
            foldData.clear();
            data.addAll(result);
        }
        //如果需要展开收起，需要对传入的数据进行截断，并添加至
        notifyDataSetChanged();
    }

    public void addData(@NonNull T data) {
        this.data.add(data);
        compatibilityDataSizeChanged(1);
    }

    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        this.data.add(position, data);
        notifyItemInserted(position);
        compatibilityDataSizeChanged(1);
    }

    public void removeAt(@IntRange(from = 0) int position) {
        if (position >= data.size()) {
            return;
        }
        this.data.remove(position);
        notifyItemRemoved(position);
        compatibilityDataSizeChanged(0);
    }

    protected void compatibilityDataSizeChanged(int size) {
        if (this.data.size() == size) {
            notifyDataSetChanged();
        }
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public void setExpandEnable(boolean expandEnable) {
        mExpandEnable = expandEnable;
    }

    /**
     * 折叠宫格，展示最小数量
     */
    void fold() {
        if (minCount <= 0) {
            return;
        }
        if (minCount >= data.size()) {
            return;
        }
        int itemCount = data.size() - minCount;
        List<T> visibleData = data.subList(0, minCount);
        List<T> goneData = data.subList(minCount, data.size());
        data = visibleData;
        foldData = goneData;
        notifyItemRangeRemoved(minCount, itemCount);
        compatibilityDataSizeChanged(0);
    }

    /**
     * 展开宫格，展示最大数量
     */
    void expand() {
        int positionStart = data.size();
        int itemCount = foldData.size();
        data.addAll(foldData);
        notifyItemRangeInserted(positionStart, itemCount);
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

