package com.wjx.android.ninegridview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * 作者：wangjianxiong 创建时间：2021/4/6
 */
@RestrictTo(LIBRARY_GROUP)
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int mItemSpacing;

    private final int mLineSpacing;

    private final int mSpanCount;

    public SpacingItemDecoration(int spanCount, int itemSpacing, int lineSpacing) {
        this.mSpanCount = spanCount;
        this.mItemSpacing = itemSpacing;
        this.mLineSpacing = lineSpacing;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
            @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position >= mSpanCount) {
            outRect.top = mLineSpacing;
        }
        outRect.right = mItemSpacing;
    }
}
