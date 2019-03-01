package com.example.asus.watermelonenglish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by asus on 2019/2/9.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        init();
    }

    private void init() {
        findViewById(R.id.login_weixin).setOnClickListener(this);
        findViewById(R.id.login_qq).setOnClickListener(this);
        findViewById(R.id.login_zh).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_qq:{
               // this.startActivity(new Intent(this,MainActivity.class));
                Toast.makeText(LoginActivity.this,"不能登录",Toast.LENGTH_SHORT).show();
               // finish();
                break;
            }
            case R.id.login_weixin:{
//                this.startActivity(new Intent(this,MainActivity.class));
//                finish();
                Toast.makeText(LoginActivity.this,"不能登录",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.login_zh:{
                this.startActivity(new Intent(this,LoginUser.class));
                break;
            }
        }

    }
}
