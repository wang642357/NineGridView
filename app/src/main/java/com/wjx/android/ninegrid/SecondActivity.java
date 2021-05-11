package com.wjx.android.ninegrid;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjx.android.ninegrid.databinding.ActivitySecondBinding;
import com.wjx.android.ninegridview.adapter.NineGridAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：wangjianxiong 创建时间：2021/5/11
 */
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySecondBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.nineGrid.setAdapter(new NineGridAdapter<String>(R.layout.layout_image, getList()) {
            @Override
            public void onBindData(@NonNull View itemView, int position, @NonNull String item) {
                ImageView content = itemView.findViewById(R.id.content);
                Glide.with(SecondActivity.this)
                        .load(item)
                        .into(content);
            }
        });
        mBinding.expand.setOnClickListener(this);
        mBinding.fold.setOnClickListener(this);
        mBinding.confirmExpand.setOnClickListener(this);
        mBinding.confirmFold.setOnClickListener(this);
        mBinding.expandText.setOnClickListener(this);
        mBinding.foldText.setOnClickListener(this);
    }

    List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add(
                "http://imagetest.minyoufang.com/minyoufang/business/2021/0409/11/bf12febb-accc-c958-ea94-08d8fb06f3a9?_t=20210409112414961");
        list.add("https://b-ssl.duitang.com/uploads/blog/201308/05/20130805105309_5E2zE.jpeg");
        list.add(
                "http://imagetest.minyoufang.com/minyoufang/business/2021/0409/11/2c9ce33d-9121-c87f-98b4-08d8fb06f3f8?_t=20210409112415476");
        list.add(
                "http://imagetest.minyoufang.com/minyoufang/business/2021/0409/11/d62818d5-ce58-ca5d-f909-08d8fb06f419?_t=20210409112415679");
        list.add(
                "http://imagetest.minyoufang.com/minyoufang/business/2021/0409/11/bb752c94-dc87-cf75-959a-08d8fb06f436?_t=20210409112415883");
        list.add(
                "http://imagetest.minyoufang.com/minyoufang/business/2021/0409/11/545ba4a6-c70f-c911-f65a-08d8fb06f457?_t=20210409112416101");
        list.add(
                "http://imagetest.minyoufang.com/minyoufang/business/2021/0409/11/a887d7e7-dcc4-c18d-929a-08d8fb06f474?_t=20210409112416289");
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expand:
                mBinding.nineGrid.expand();
                break;
            case R.id.fold:
                mBinding.nineGrid.fold();
                break;
            case R.id.confirm_expand:
                String expandText = mBinding.expandText.getText().toString().trim();
                if (TextUtils.isEmpty(expandText)) {
                    return;
                }
                mBinding.nineGrid.setExpandText(expandText);
                break;
            case R.id.confirm_fold:
                String foldText = mBinding.foldText.getText().toString().trim();
                if (TextUtils.isEmpty(foldText)) {
                    return;
                }
                mBinding.nineGrid.setFoldText(foldText);
                break;
        }
    }
}
