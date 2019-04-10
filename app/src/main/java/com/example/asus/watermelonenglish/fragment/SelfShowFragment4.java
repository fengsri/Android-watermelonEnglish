package com.example.asus.watermelonenglish.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.watermelonenglish.LoginActivity;
import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.RegisteActivity2;
import com.example.asus.watermelonenglish.SelfShowActivity;
import com.example.asus.watermelonenglish.WordActivity;
import com.example.asus.watermelonenglish.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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
        fschool.setText("设置我的学校("+school+")");
        flevel=view.findViewById(R.id.self_fragment4_level);
        flevel.setText("设置我的方向（"+level+")");

        view.findViewById(R.id.self_fragment4_school_l).setOnClickListener(this);
        view.findViewById(R.id.self_fragment4_level_l).setOnClickListener(this);
        view.findViewById(R.id.self_fragment4_nandu).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.self_fragment_logout:{
                BmobUser.logOut();
                Intent intent=new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                this.getActivity().finish();
                break;
            }
            case R.id.self_fragment4_school_l:{
                showEditShool();
                break;
            }
            case R.id.self_fragment4_level_l:{
                showEditLevel();
                break;
            }
            case R.id.self_fragment4_nandu:{
                showEditNandu();
                break;
            }
            default:
                break;
        }
    }

    private void showEditNandu() {
        final String[] items = new String[]{"四级难度", "六级难度","雅思难度","托福难度"};
        // 创建对话框构建器yy
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        // 设置参数
        builder.setIcon(R.drawable.fangxiang).setTitle("设置等级")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String level = "英语四级";
                        if (which == 0) {
                            level = "英语四级";
                        } else if (which == 1) {
                            level = "英语六级";
                        }else if(which ==2){
                            level = "英语雅思";
                        }else if(which ==3){
                            level = "英语托福";
                        }
                        user.setUserLevel(level);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
        builder.create().show();
    }

    private void showEditLevel() {
        final String[] items = new String[]{"英语四级", "英语六级","英语雅思","英语托福"};
        // 创建对话框构建器yy
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        // 设置参数
        builder.setIcon(R.drawable.fangxiang).setTitle("设置等级")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String level = "英语四级";
                        if (which == 0) {
                            level = "英语四级";
                        } else if (which == 1) {
                            level = "英语六级";
                        }else if(which ==2){
                            level = "英语雅思";
                        }else if(which ==3){
                            level = "英语托福";
                        }
                        user.setUserLevel(level);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        flevel.setText("设置我的方向（"+level+")");
                    }
                });
        builder.create().show();
    }

    private void showEditShool() {
        View view = LayoutInflater.from(context).inflate(R.layout.self_show_fragment_itemdialog1,null,false);

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setTitle("修改学校");
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText editText = view.findViewById(R.id.itemdialog1_edit);
        view.findViewById(R.id.itemdialog1_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String school = editText.getText().toString().trim();
                if(school.equals("")){
                    Toast.makeText(context,"学校不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    int tg1=1;
                    int tg2=1;
                    if(!school.contains("大学")){
                        tg1=0;
                    }
                    if(!school.contains("学院")){
                        tg2=0;
                    }
                    if(tg1==0 && tg2==0) {
                        Toast.makeText(context, "学校不正确！", Toast.LENGTH_SHORT).show();
                    }else{
                        user.setUserSchool(school);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
                                    fschool.setText("设置我的学校("+school+")");
                                } else {
                                    Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                }
            }
        });
    }
}
