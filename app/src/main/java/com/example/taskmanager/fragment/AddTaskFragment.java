package com.example.taskmanager.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taskmanager.DataUtil;
import com.example.taskmanager.ItemClickActivity;
import com.example.taskmanager.MainActivity;
import com.example.taskmanager.R;
import com.example.taskmanager.SpinnerPopupwindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddTaskFragment extends Fragment {
    private LinearLayout fragment_back; //点击即取消编辑
    private TextView tv_addtip; //显示选择的当前任务组 可点击
    private ImageView iv_addtip; //选择任务添加的文件夹
    private EditText et_addtip; //任务添加输入框
    private Button btn_cancel; //取消按钮
    private Button btn_finish; //确认按钮
    private List<String> taskGroup; //所有的任务组
    private SpinnerPopupwindow spinnerPopupwindow;
    private FloatingActionButton fab; //调整它的显示与隐藏
    private String value; //选中的任务组的名字

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addtask, container, false);

        initData();
        initView(view);
        setOnClickListener();

        return view;

    }

    private void initData() {
        taskGroup = new ArrayList<>();
        taskGroup.addAll(DataUtil.dataUtilInstance.getGroup());

        value = taskGroup.get(0);
    }

    private void initView(View v) {
        fragment_back = (LinearLayout)v.findViewById(R.id.fragment_back);
        tv_addtip = (TextView)v.findViewById(R.id.tv_addtip);
        iv_addtip = (ImageView)v.findViewById(R.id.iv_addtip);
        et_addtip = (EditText)v.findViewById(R.id.et_addtip);
        btn_cancel = (Button)v.findViewById(R.id.btn_cancel);
        btn_finish = (Button)v.findViewById(R.id.btn_finish);

        MainActivity mainActivity = (MainActivity)getActivity();
        fab = mainActivity.findViewById(R.id.id_fabtn);
    }

    private void setOnClickListener(){
        //点击背后半透明取消编辑
        fragment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                Fragment fragment=fragmentManager.findFragmentByTag("add");
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.remove(fragment);
                ft.commit();
                fab.show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                Fragment fragment=fragmentManager.findFragmentByTag("add");
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.remove(fragment);
                ft.commit();
                fab.show();
            }
        });
        //选择任务组
        tv_addtip.setText(taskGroup.get(0));
        tv_addtip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerPopupwindow = new SpinnerPopupwindow(getActivity()
                        , taskGroup.get(0), taskGroup, itemsOnClick);
                spinnerPopupwindow.showPopupWindow(tv_addtip);
            }
        });
        //选择任务组
        iv_addtip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerPopupwindow = new SpinnerPopupwindow(Objects.requireNonNull(getActivity())
                        , taskGroup.get(0), taskGroup, itemsOnClick);
                spinnerPopupwindow.showPopupWindow(tv_addtip);
            }
        });

        et_addtip.setHint("在"+value+"中添加任务");
        //确认按钮
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                Fragment fragment=fragmentManager.findFragmentByTag("add");
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.remove(fragment);
                ft.commit();
                fab.show();

                //TODO 获取当前editView的值传到对应任务组的taskTodo
                if(!et_addtip.getText().toString().isEmpty()){
                    String tip = et_addtip.getText().toString();
                    //TODO 网络请求
                    List<String> taskTodo = new ArrayList<>(DataUtil.dataUtilInstance.getTaskTodo());
                    taskTodo.add(tip);
                    DataUtil.dataUtilInstance.setTaskTodo(taskTodo);
                    Log.d("Add",DataUtil.dataUtilInstance.getTaskTodo().toString());
                }
            }
        });
    }

    private AdapterView.OnItemClickListener itemsOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            value = taskGroup.get(spinnerPopupwindow.getText());
            tv_addtip.setText(value);
            et_addtip.setHint("在"+value+"中添加任务");
            spinnerPopupwindow.dismissPopupWindow();
        }
    };

}
