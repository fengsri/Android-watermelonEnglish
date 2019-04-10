package com.example.asus.watermelonenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.watermelonenglish.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by asus on 2019/2/24.
 */

public class LoginUser extends AppCompatActivity implements View.OnClickListener{
    private EditText name;
    private EditText password;
    private TextView fg;
    private TextView registe;
    private Button button;

    private String name_str;
    private String password_str;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_user);
        name=findViewById(R.id.login_user_name);
        password=findViewById(R.id.login_user_password);

        fg=findViewById(R.id.login_user_fg);
        fg.setOnClickListener(this);
        registe=findViewById(R.id.login_user_registe);
        registe.setOnClickListener(this);
        button=findViewById(R.id.login_user_bt);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_user_registe:{
                Intent intent=new Intent(LoginUser.this,RegisteActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.login_user_fg:{

                break;
            }
            case R.id.login_user_bt:{
                name_str=name.getText().toString();
                password_str=password.getText().toString();

                //此处替换为你的用户名密码
                BmobUser.loginByAccount(name_str, password_str, new LogInListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Intent intent=new Intent(LoginUser.this,MainActivity.class);
                            intent.putExtra("ato",0);
                            startActivity(intent);
                            finish();
                        } else {
                            name.setText("");
                            password.setText("");
                           Toast.makeText(LoginUser.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            default:
                break;
        }
    }
}
