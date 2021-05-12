# NineGridView [![](https://jitpack.io/v/wang642357/NineGridView.svg)](https://jitpack.io/#wang642357/NineGridView)
Android 九宫格控件，当图片过多时支持展开收起
## 导入
### 在你的项目Project下的build.gradle中添加：

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
### 添加依赖：
```
dependencies {
    implementation 'com.github.wang642357:NineGridView:1.0.0-beta02'
}
```
## 使用方法
### 1.xml布局文件
> 不要使用`wrap_content`属性
```xml
 <com.wjx.android.ninegridview.NineGridView
            app:expand_enable="true"
            app:fold_enable="true"
            app:expand_text_size="15sp"
            app:expand_text_color="#007aff"
            app:expand_text="展开"
            app:fold_text="收起"
            app:grid_min_count="0"
            app:grid_text_spacing="10dp"
            android:layout_width="match_parent"
            android:id="@+id/nine_grid"
            android:layout_height="wrap_content" />
```
### 2.接口说明
#### 在项目加入如下代码
```java
public class MyAdapter extends NineGridAdapter<String> {

    //传入布局资源id
    public ImageAdapter(int layoutRes) {
        super(layoutRes);
    }

    @Override
    public void onBindData(@NonNull View itemView, int position, @NonNull String item) {
      //绑定每项数据
    }
}
```

```java
//设置适配器
MyAdapter imageAdapter = new MyAdapter(R.layout.layout_image);
//填充数据
imageAdapter.setList(mDataList.get(position));
nineGridView.setAdapter(imageAdapter);
//展开
nineGridView.expand()
//折叠
nineGridView.fold()
```
#### 设置监听
```java
nineGridView.setOnExpandChangeListener(new OnExpandChangeListener() {
            @Override
            public void onChange(View expandView, boolean isExpand) {
              //监听展开收起
            }
        });
nineGridView.setOnItemClickListener(new OnGridItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
              //监听item点击事件
            }
        });
```



## 自定义属性说明
```xml
    <declare-styleable name="NineGridView">
        <!--每行展示的数量，默认为3-->
        <attr name="grid_span_count" format="integer" />
        <!--列间距-->
        <attr name="item_spacing" format="dimension|integer" />
        <!--行间距-->
        <attr name="line_spacing" format="dimension|integer" />
        <!--展示最大数量-->
        <attr name="grid_max_count" format="integer" />
        <!--开启展开收起功能时的最小数量-->
        <attr name="grid_min_count" format="integer" />
        <!--展开文本和九宫格间距-->
        <attr name="grid_text_spacing" format="dimension" />
        <!--是否支持展开功能-->
        <attr name="expand_enable" format="boolean" />
        <!--是否支持收起功能，只有expand_enable为true时才生效-->
        <attr name="fold_enable" format="boolean" />
        <!--展开文本字体颜色-->
        <attr name="expand_text_color" format="color" />
        <!--展开文本字体大小-->
        <attr name="expand_text_size" format="dimension" />
        <!--展开文本-->
        <attr name="expand_text" format="string|dimension" />
        <!--收起文本-->
        <attr name="fold_text" format="string|dimension" />
    </declare-styleable>
```

