package com.wjx.android.ninegrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView list = findViewById(R.id.list);
        ListAdapter listAdapter = new ListAdapter(this, getList2());
        list.setAdapter(listAdapter);
    }

    public void second(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("https://img0.baidu.com/it/u=2583275964,2440658935&fm=11&fmt=auto&gp=0.jpg");
        /*list.add("https://img0.baidu.com/it/u=2583275964,2440658935&fm=11&fmt=auto&gp=0.jpg");
        list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0327%252F19478f8aj00qqm90m00f5c0011i01e0m.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623035211&t=5a158f5eb45f471b597515511b3c88e2");
        list.add("https://img0.baidu.com/it/u=2583275964,2440658935&fm=11&fmt=auto&gp=0.jpg");
        list.add("https://b-ssl.duitang.com/uploads/blog/201308/05/20130805105309_5E2zE.jpeg");*/

        return list;
    }

    List<String> getList3() {
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

    List<String> getList4() {
        List<String> list = new ArrayList<>();
        list.add("https://img0.baidu.com/it/u=2583275964,2440658935&fm=11&fmt=auto&gp=0.jpg");
        list.add("https://img0.baidu.com/it/u=2583275964,2440658935&fm=11&fmt=auto&gp=0.jpg");
        list.add(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0327%252F19478f8aj00qqm90m00f5c0011i01e0m.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623035211&t=5a158f5eb45f471b597515511b3c88e2");
        list.add("https://img0.baidu.com/it/u=2583275964,2440658935&fm=11&fmt=auto&gp=0.jpg");
        return list;
    }

    List<List<String>> getList2() {
        List<List<String>> list = new ArrayList<>();
        list.add(getList());
        list.add(getList3());
        list.add(getList4());
        list.add(getList3());
        list.add(getList4());
        list.add(getList());
        return list;
    }
}