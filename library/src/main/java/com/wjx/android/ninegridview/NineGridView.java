package com.wjx.android.ninegridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：wangjianxiong 创建时间：2021/4/6
 * <p>
 * 九宫格布局
 */
public class NineGridView extends LinearLayout {

    private int mSpanCount;

    private int mMaxCount;

    private int mItemSpacing;

    private int mLineSpacing;

    private int mGridTextSpacing;

    private int mMinCount;

    private Boolean mExpandable;

    private Boolean mFoldEnable;

    private NineGridAdapter mNineGridAdapter;

    private GridLayoutManager mGridLayoutManager;

    private SpacingItemDecoration mSpacingItemDecoration;

    private RecyclerView mRecyclerView;

    private TextView mExpandTextView;

    /**
     * 展开:true,默认收起
     */
    private boolean isExpand;

    public NineGridView(Context context) {
        this(context, null);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        mItemSpacing = a.getDimensionPixelSize(R.styleable.NineGridView_item_grid_spacing, 0);
        mLineSpacing = a.getDimensionPixelSize(R.styleable.NineGridView_line_grid_spacing, 0);
        mSpanCount = a.getInt(R.styleable.NineGridView_grid_span_count, 3);
        int maxCount = a.getInt(R.styleable.NineGridView_grid_max_count, 9);
        mMinCount = a.getInt(R.styleable.NineGridView_grid_min_count, 0);
        mExpandable = a.getBoolean(R.styleable.NineGridView_expand_enable, false);
        mFoldEnable = a.getBoolean(R.styleable.NineGridView_fold_enable, false);
        mGridTextSpacing = a.getDimensionPixelSize(R.styleable.NineGridView_grid_text_spacing, 0);
        int textColor = a.getColor(R.styleable.NineGridView_expand_text_color, -1);
        int textSize = a.getDimensionPixelSize(R.styleable.NineGridView_expand_text_size, 16);
        mMaxCount = Math.min(maxCount, 9);
        if (mSpanCount > maxCount) {
            throw new IllegalArgumentException("SpanCount cannot be greater than MaxCount");
        }
        a.recycle();
        setOrientation(VERTICAL);
        setClipChildren(false);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        mGridLayoutManager = new GridLayoutManager(context, mSpanCount);
        mSpacingItemDecoration = new SpacingItemDecoration(mSpanCount, mItemSpacing, mLineSpacing);
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        DefaultItemAnimator itemAnimator = (DefaultItemAnimator) mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.setSupportsChangeAnimations(false);
        }
        attachViewToParent(mRecyclerView, 0, mRecyclerView.getLayoutParams());
        if (mExpandable) {
            mRecyclerView.setHasFixedSize(false);
            mExpandTextView = new TextView(context);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.END;
            layoutParams.topMargin = mGridTextSpacing;
            layoutParams.rightMargin = mGridTextSpacing;
            mExpandTextView.setLayoutParams(layoutParams);
            if (isExpand) {
                mExpandTextView.setText("收起");
            } else {
                mExpandTextView.setText("展开");
            }
            mExpandTextView.setTextSize(textSize);
            if (textColor != -1) {
                mExpandTextView.setTextColor(textColor);
            }
            mExpandTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExpand) {
                        if (mFoldEnable) {
                            fold();
                        }
                    } else {
                        expand();
                    }
                }
            });
            attachViewToParent(mExpandTextView, 1, mExpandTextView.getLayoutParams());
        } else {
            mRecyclerView.setHasFixedSize(true);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRecyclerView.addItemDecoration(mSpacingItemDecoration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRecyclerView.removeItemDecoration(mSpacingItemDecoration);
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
        requestLayout();
    }

    public void setAdapter(@Nullable NineGridAdapter adapter) {
        mNineGridAdapter = adapter;
        if (mNineGridAdapter != null) {
            mNineGridAdapter.setMinCount(mMinCount);
            mNineGridAdapter.setExpandEnable(mExpandable);
        }
        mRecyclerView.setAdapter(adapter);
    }

    public void fold() {
        if (!isExpand) {
            return;
        }
        if (mNineGridAdapter != null) {
            isExpand = false;
            mNineGridAdapter.fold();
            mExpandTextView.setText("展开");
        }
    }

    public void expand() {
        if (isExpand) {
            return;
        }
        if (mNineGridAdapter != null) {
            isExpand = true;
            mNineGridAdapter.expand();
            if (mFoldEnable) {
                mExpandTextView.setVisibility(View.VISIBLE);
                mExpandTextView.setText("收起");
            } else {
                mExpandTextView.setVisibility(View.GONE);
            }
        }
    }

    public void setOnItemClickListener(@NonNull OnGridItemClickListener listener) {
        if (mNineGridAdapter != null) {
            mNineGridAdapter.setOnItemClickListener(listener);
        }
    }

    public int getSpanCount() {
        return mSpanCount;
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public int getItemSpacing() {
        return mItemSpacing;
    }

    public int getLineSpacing() {
        return mLineSpacing;
    }

    /**
     * @return 开启折叠功能时，最小的展示数量
     */
    public int getMinCount() {
        return mMinCount;
    }

    public boolean isSupportExpand() {
        return mExpandable;
    }

    @Nullable
    public NineGridAdapter getAdapter() {
        return mNineGridAdapter;
    }
}
