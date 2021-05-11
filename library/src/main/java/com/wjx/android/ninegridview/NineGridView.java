package com.wjx.android.ninegridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjx.android.ninegridview.adapter.NineGridAdapter;
import com.wjx.android.ninegridview.itemdecoration.SpacingItemDecoration;
import com.wjx.android.ninegridview.listener.OnExpandChangeListener;
import com.wjx.android.ninegridview.listener.OnGridItemClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：wangjianxiong 创建时间：2021/4/6
 * <p>
 * 九宫格布局,支持展开收起
 */
public class NineGridView extends LinearLayout {

    private int mSpanCount;

    private int mMaxCount;

    private int mItemSpacing;

    private int mLineSpacing;

    private int mMinCount;

    private Boolean mExpandable;

    private Boolean mFoldEnable;

    private NineGridAdapter mNineGridAdapter;

    private OnExpandChangeListener mOnExpandChangeListener;

    private SpacingItemDecoration mSpacingItemDecoration;

    private RecyclerView mRecyclerView;

    private TextView mExpandTextView;

    /**
     * 展开:true,默认收起
     */
    private boolean isExpand;

    private CharSequence mExpandText;

    private CharSequence mFoldText;

    private static final String DEFAULT_EXPAND_TEXT = "展开";

    private static final String DEFAULT_FOLD_TEXT = "收起";

    private final NineGridViewDataObserver mObserver = new NineGridViewDataObserver();

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
        mItemSpacing = a.getDimensionPixelSize(R.styleable.NineGridView_item_spacing, 0);
        mLineSpacing = a.getDimensionPixelSize(R.styleable.NineGridView_line_spacing, 0);
        mSpanCount = a.getInt(R.styleable.NineGridView_grid_span_count, 3);
        int maxCount = a.getInt(R.styleable.NineGridView_grid_max_count, 9);
        mMinCount = a.getInt(R.styleable.NineGridView_grid_min_count, 3);
        mExpandable = a.getBoolean(R.styleable.NineGridView_expand_enable, false);
        mFoldEnable = a.getBoolean(R.styleable.NineGridView_fold_enable, false);
        int gridTextSpacing = a
                .getDimensionPixelSize(R.styleable.NineGridView_grid_text_spacing, 0);
        int textColor = a.getColor(R.styleable.NineGridView_expand_text_color, -1);
        int textSize = a.getDimensionPixelSize(R.styleable.NineGridView_expand_text_size, -1);
        mExpandText = a.getText(R.styleable.NineGridView_expand_text);
        mFoldText = a.getText(R.styleable.NineGridView_fold_text);
        mMinCount = mMinCount == 0 ? 3 : mMinCount;
        if (TextUtils.isEmpty(mExpandText)) {
            mExpandText = DEFAULT_EXPAND_TEXT;
        }
        if (TextUtils.isEmpty(mFoldText)) {
            mFoldText = DEFAULT_FOLD_TEXT;
        }
        mMaxCount = Math.min(maxCount, 9);
        if (mSpanCount > maxCount) {
            throw new IllegalArgumentException("SpanCount cannot be greater than MaxCount");
        }
        a.recycle();
        setOrientation(VERTICAL);
        setClipChildren(false);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, mSpanCount);
        mSpacingItemDecoration = new SpacingItemDecoration(mSpanCount, mItemSpacing, mLineSpacing);
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        DefaultItemAnimator itemAnimator = (DefaultItemAnimator) mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.setSupportsChangeAnimations(false);
        }
        attachViewToParent(mRecyclerView, 0, mRecyclerView.getLayoutParams());
        mRecyclerView.setHasFixedSize(false);
        mExpandTextView = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.END;
        layoutParams.topMargin = gridTextSpacing;
        layoutParams.rightMargin = gridTextSpacing;
        mExpandTextView.setLayoutParams(layoutParams);
        if (isExpand) {
            mExpandTextView.setText(mFoldText);
        } else {
            mExpandTextView.setText(mExpandText);
        }
        if (textSize != -1) {
            mExpandTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        if (textColor != -1) {
            mExpandTextView.setTextColor(textColor);
        }
        mExpandTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expandClick();
            }
        });
        attachViewToParent(mExpandTextView, 1, mExpandTextView.getLayoutParams());
        mExpandTextView.setVisibility(View.GONE);

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

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        final SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        isExpand = ss.isExpand;
        expandClick();
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final SavedState ss = new SavedState(superState);
        ss.isExpand = isExpand;
        return ss;
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
        requestLayout();
    }

    public void setAdapter(@Nullable NineGridAdapter adapter) {
        if (mNineGridAdapter != null) {
            mNineGridAdapter.unregisterAdapterDataObserver(mObserver);
        }
        if (adapter != null) {
            mNineGridAdapter = adapter;
            adapter.registerAdapterDataObserver(mObserver);
            if (mExpandable) {
                mNineGridAdapter.refreshData(mMinCount);
            }
            mRecyclerView.setAdapter(mNineGridAdapter);
        } else {
            mExpandTextView.setVisibility(GONE);
        }
    }

    private void expandClick() {
        if (isExpand) {
            if (mFoldEnable) {
                fold();
            }
        } else {
            expand();
        }
    }

    public void fold() {
        if (!mFoldEnable) {
            return;
        }
        if (!isExpand) {
            return;
        }
        if (mNineGridAdapter != null) {
            isExpand = false;
            mNineGridAdapter.fold();
            mExpandTextView.setText(mExpandText);
            if (mOnExpandChangeListener != null) {
                mOnExpandChangeListener.onChange(mExpandTextView, isExpand);
            }
        }
    }

    public void expand() {
        if (!mExpandable) {
            return;
        }
        if (isExpand) {
            return;
        }
        if (mNineGridAdapter != null) {
            isExpand = true;
            mNineGridAdapter.expand();
            if (mFoldEnable) {
                mExpandTextView.setVisibility(View.VISIBLE);
                mExpandTextView.setText(mFoldText);
            } else {
                mExpandTextView.setVisibility(View.GONE);
            }
            if (mOnExpandChangeListener != null) {
                mOnExpandChangeListener.onChange(mExpandTextView, isExpand);
            }
        }
    }

    private void setExpandTextInternal() {
        if (!isExpand) {
            mExpandTextView.setText(mExpandText);
        }
    }

    private void setFoldTextInternal() {
        if (isExpand) {
            mExpandTextView.setText(mExpandText);
        }
    }

    void updateExpandTextState() {
        if (mExpandable) {
            if (mNineGridAdapter.getAllData().size() > mMinCount) {
                mExpandTextView.setVisibility(VISIBLE);
            } else {
                mExpandTextView.setVisibility(GONE);
            }
        } else {
            mExpandTextView.setVisibility(GONE);
        }
    }

    public void setOnItemClickListener(@NonNull OnGridItemClickListener listener) {
        if (mNineGridAdapter != null) {
            mNineGridAdapter.setOnItemClickListener(listener);
        }
    }

    public void setOnExpandChangeListener(OnExpandChangeListener listener) {
        this.mOnExpandChangeListener = listener;
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

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 设置展开时候的文本
     *
     * @param expandText 要展示的文本
     */
    public void setExpandText(CharSequence expandText) {
        mExpandText = expandText;
        setExpandTextInternal();
    }

    /**
     * 设置收起时候的文本
     *
     * @param foldText 要展示的文本
     */
    public void setFoldText(CharSequence foldText) {
        mFoldText = foldText;
        setFoldTextInternal();
    }

    /**
     * 设置展开时候的文本
     *
     * @param expandResId 要显示的字符串资源的资源标识符
     */
    public void setExpandText(@StringRes int expandResId) {
        mExpandText = getContext().getResources().getText(expandResId);
        setExpandTextInternal();
    }

    /**
     * 设置收起时候的文本
     *
     * @param foldResId 要显示的字符串资源的资源标识符
     */
    public void setFoldText(@StringRes int foldResId) {
        mFoldText = getContext().getResources().getText(foldResId);
        setFoldTextInternal();
    }

    private class NineGridViewDataObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
            updateExpandTextState();
        }
    }

    public static class SavedState extends AbsSavedState {

        public boolean isExpand;

        protected SavedState(@NonNull Parcelable superState) {
            super(superState);
        }

        protected SavedState(@NonNull Parcel in, @Nullable ClassLoader loader) {
            super(in, loader);
            isExpand = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeByte((byte) (isExpand ? 1 : 0));
        }

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
