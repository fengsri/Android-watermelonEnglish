package com.example.asus.watermelonenglish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.asus.watermelonenglish.bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/24.
 */

public class LuncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_luncher);
        Bmob.initialize(this, "b73504c6580b52655febd75a7f88af5c");
        int tag=0;
        User user = BmobUser.getCurrentUser(User.class);
        if(null == user){ //未登录
            tag=0;
        }else{  //登录
            tag=1;
        }
        final int finalTag = tag;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(finalTag ==0){
                    Intent intent=new Intent(LuncherActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    startActivity(new Intent(LuncherActivity.this,MainActivity.class));
                    finish();
                }
            }
        },1500);
    }
}
