package com.example.asus.watermelonenglish.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.watermelonenglish.R;

/**
 * Created by asus on 2019/2/8.
 */

public class SelfShowFragment3 extends Fragment implements View.OnClickListener{
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.self_show_fragment3,container,false);
        //初始化
        init(view);
        return view;
    }

    private void init(View view) {

    }

    @Override
    public void onClick(View view) {

    }
}
