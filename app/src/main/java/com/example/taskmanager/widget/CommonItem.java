package com.example.taskmanager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taskmanager.R;

/**
 * Created by 76952 on 2018/12/3.
 */

public class CommonItem  extends RelativeLayout {
    private ImageView iv_icon;
    private TextView tv_title;

    private void initView(Context context) {
        View.inflate(context, R.layout.common_item, this);
        iv_icon = (ImageView) this.findViewById(R.id.item_iv);
        tv_title = (TextView) this.findViewById(R.id.item_title);
    }

    // 重写构造方法 ,在构造方法中初始化控件,并引用自定义属性
    public CommonItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CommonItem);
        String title = attributes.getString(R.styleable.CommonItem_item_title);
        setTitle(title);
        int leftImgResource = attributes.getResourceId(R.styleable.CommonItem_item_img, -1);
        if (leftImgResource != -1) {
            setIvResource(leftImgResource);
        }
    }

    public CommonItem(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs, defStyle);
        initView(context);
    }
    public void setIvResource(int resId){
        iv_icon.setImageResource(resId);
    }

    public void setTitle(int resid){
        tv_title.setText(resid);
    }
    public void setTitle(String resid){
        tv_title.setText(resid);
    }

}
