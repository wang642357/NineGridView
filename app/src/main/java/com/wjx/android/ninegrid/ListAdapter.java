package com.wjx.android.ninegrid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wjx.android.ninegridview.NineGridView;
import com.wjx.android.ninegridview.listener.OnExpandChangeListener;
import com.wjx.android.ninegridview.listener.OnGridItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：wangjianxiong 创建时间：2021/5/8
 */
class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mContext;

    private List<List<String>> mDataList;

    public ListAdapter(Context context, List<List<String>> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ImageAdapter imageAdapter = new ImageAdapter(mContext, R.layout.layout_image,
                mDataList.get(position));
        holder.mNineGridView.setAdapter(imageAdapter);
        holder.mNineGridView.setOnItemClickListener(new OnGridItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.mNineGridView.setOnExpandChangeListener(new OnExpandChangeListener() {
            @Override
            public void onChange(View expandView, boolean isExpand) {
                Toast.makeText(mContext, "" + isExpand, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        NineGridView<String> mNineGridView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            mNineGridView = itemView.findViewById(R.id.nine_grid);
        }
    }
}
