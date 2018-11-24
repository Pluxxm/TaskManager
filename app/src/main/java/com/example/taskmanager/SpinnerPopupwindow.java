package com.example.taskmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.taskmanager.adapter.SpinnerPopAdapter;

import java.util.List;

public class SpinnerPopupwindow extends PopupWindow {
    private View conentView;
    private ListView listView_select_group;
    private SpinnerPopAdapter adapter;
    private Activity context;
    private LinearLayout ll_cancel;

/**
 * @param context 上下文
 * @param string 获取到未打开列表时显示的值
 * @param list 需要显示的列表的集合
 * @param itemsOnClick listview在activity中的点击监听事件
 */
    public SpinnerPopupwindow(final Activity context, final String string
            , final List<String> list, AdapterView.OnItemClickListener itemsOnClick) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context =context;
        conentView = inflater.inflate(R.layout.popupwindow_spinner, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //    this.setWidth(view.getWidth());
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.showAtLocation(conentView, Gravity.CENTER,0,0);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 刷新状态
        this.update();
        this.setOutsideTouchable(false);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
        //解决软键盘挡住弹窗问题
//        this.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        //  this.setAnimationStyle(R.style.AnimationPreview);

        adapter = new SpinnerPopAdapter(context,list);
        listView_select_group = (ListView) conentView.findViewById(R.id.listview_selectgroup);
        listView_select_group.setOnItemClickListener(itemsOnClick);
        listView_select_group.setAdapter(adapter);
        // setAdapter是异步进行的，为了使弹窗能即时刷新，所以使用post+Runnable
        listView_select_group.post(new Runnable() {
            @Override
            public void run() {
                //主要是为了比对未打开列表时显示的值和列表中的值进行默认选中
                for(int j = 0;j<list.size();j++){
                    if(string.equals(list.get(j).toString())){
                        listView_select_group.setItemChecked(j, true);//listview自带的方法
                    }else {
                        listView_select_group.setItemChecked(j, false);
                    }
                }
            }
        });

        ll_cancel = conentView.findViewById(R.id.ll_cancel);
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                darkenBackground(1f);
            }
        });
    }

    //获取选中列表中的数据所对应的position
    public int getText(){
        return listView_select_group.getCheckedItemPosition();
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            //  this.showAsDropDown(parent);
            // this.showAsDropDown(parent,0,10);
            this.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
            darkenBackground(0.9f);//弹出时让页面背景回复给原来的颜色降低透明度，让背景看起来变成灰色
        }
    }
    /**
     * 关闭popupWindow
     */
    public void dismissPopupWindow() {
        this.dismiss();
        darkenBackground(1f);//关闭时让页面背景回复为原来的颜色

    }
    /**
     * 改变背景颜色，主要是在PopupWindow弹出时背景变化，通过透明度设置
     */
    private void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgcolor;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
