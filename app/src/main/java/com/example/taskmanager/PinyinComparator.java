package com.example.taskmanager;

import com.example.taskmanager.bean.SortBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortBean> {
    @Override
    public int compare(SortBean sortBean, SortBean t1) {
        if(t1.getTaskF().equals("#")){
            return -1;
        }else if(sortBean.getTaskF().equals("#")){
            return 1;
        }else{
            return sortBean.getTaskF().compareTo(t1.getTaskF());
        }
    }
}
