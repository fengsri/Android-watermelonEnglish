package com.example.asus.watermelonenglish.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.watermelonenglish.LoginActivity;
import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.SelfShowActivity;
import com.example.asus.watermelonenglish.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/8.
 */

public class SelfShowFragment4 extends Fragment implements View.OnClickListener{
    private Context context;
    private User user;
    private LinearLayout logout;
    private TextView fschool;
    private TextView flevel;
    private String school;
    private String level;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = BmobUser.getCurrentUser(User.class);
        school=user.getUserSchool();
        level=user.getUserLevel();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.self_show_fragment4,container,false);
        //初始化
        init(view);
        return view;
    }

    private void init(View view) {
        logout=view.findViewById(R.id.self_fragment_logout);
        logout.setOnClickListener(this);
        fschool=view.findViewById(R.id.self_fragment4_school);
        fschool.setText("设置我的学习("+school+")");
        flevel=view.findViewById(R.id.self_fragment4_level);
        flevel.setText("设置我的方向（"+level+")");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.self_fragment_logout:{
                BmobUser.logOut();
                ((SelfShowActivity)(context)).out();
                Intent intent=new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
