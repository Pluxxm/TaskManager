package com.example.taskmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager.ChangeBackgroundActivity;
import com.example.taskmanager.R;
import com.example.taskmanager.widget.CommonItem;

/**
 * Created by 76952 on 2018/12/3.
 */

public class GenerateFragment extends Fragment {

    private CommonItem changeBabkground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.generate_fragment, null);

        changeBabkground = (CommonItem)view.findViewById(R.id.change_background);
        changeBabkground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeBackgroundActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
