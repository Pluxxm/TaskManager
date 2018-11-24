package com.example.taskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.taskmanager.R;

import java.util.List;

public class SpinnerPopAdapter extends BaseAdapter {
    private List<String> content;
    private Context context;
    private LayoutInflater mInflater;

    public SpinnerPopAdapter(Context context,List<String> content){
        this.context = context;
        this.content = content;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return content == null? 0:content.size();
    }

    @Override
    public Object getItem(int i) {
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = mInflater.inflate(R.layout.popupwindow_spinner_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_spinner = (TextView) view.findViewById(R.id.tv_spinner);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String spinnerText = content.get(i);
        viewHolder.tv_spinner.setText(spinnerText);
        return view;
    }
    public static class ViewHolder{
        TextView tv_spinner;
    }
}
