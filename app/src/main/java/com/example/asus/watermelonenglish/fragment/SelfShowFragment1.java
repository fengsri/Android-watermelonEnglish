package com.example.asus.watermelonenglish.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.SelfShowActivity;
import com.example.asus.watermelonenglish.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/8.
 */

public class SelfShowFragment1 extends Fragment implements View.OnClickListener{
    private Context context;
    private User user;

    private TextView fname;
    private TextView fschool;
    private TextView flevel;

    private String name;
    private String school;
    private String level;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = BmobUser.getCurrentUser(User.class);
        name=user.getUsername();
        school=user.getUserSchool();
        level=user.getUserLevel();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.self_show_fragment1,container,false);
        //初始化
        init(view);
        return view;
    }

    private void init(View view) {
        fname=view.findViewById(R.id.self_fragment1_name);
        fschool=view.findViewById(R.id.self_fragment1_school);
        flevel=view.findViewById(R.id.self_fragment1_level);
        fname.setText(name);
        fschool.setText(school);
        flevel.setText(level);
    }

    @Override
    public void onClick(View view) {

    }
}
