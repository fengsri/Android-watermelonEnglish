package com.example.asus.watermelonenglish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.watermelonenglish.bean.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by asus on 2019/2/24.
 */

public class RegisteActivity2 extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private EditText registe_name;
    private EditText registe_email;
    private EditText registe_school;
    private Spinner registe_level;
    private Button registe_bt;
    private List list=new ArrayList<String>();
    private String name;
    private String password;
    private String email;
    private String school;
    private String level;
    private String headerPic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registe2);
        init();
    }


    private void init() {
        registe_name=findViewById(R.id.registe_name2);
        registe_email=findViewById(R.id.registe_email2);
        registe_school=findViewById(R.id.registe_school2);
        registe_level=findViewById(R.id.registe_leve2);
        registe_bt=findViewById(R.id.registe_bt2);
        registe_bt.setOnClickListener(this);
         /*设置数据源*/
        list.add("我的等级");
        list.add("英语四级");
        list.add("英语六级");
        list.add("英语托福");
        list.add("英语雅思");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        registe_level.setAdapter(adapter);

        /*soDown的监听器*/
        registe_level.setOnItemSelectedListener(this);

        Intent intent=getIntent();
        headerPic=intent.getStringExtra("headerPic");
        name = intent.getStringExtra("name");
        registe_name.setText(name);
        password=name;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registe_bt2:{
                name=registe_name.getText().toString();
                email=registe_email.getText().toString();
                school=registe_school.getText().toString();
                Calendar calendar = Calendar.getInstance();
                int yearTag = calendar.get(Calendar.YEAR);//当前年
                int monthTag = calendar.get(Calendar.MONTH)+1;//当前月
                int dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
                String date=yearTag+"-"+monthTag+"-"+dayTag;

                email=registe_email.getText().toString();
                if(email.equals("")){
                    Toast.makeText(RegisteActivity2.this,"邮箱不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String regex = "^(\\w)+@(\\w)+(\\.)\\w+\\.?\\w+";
                    if(!email.matches(regex)){
                        Toast.makeText(RegisteActivity2.this,"邮箱格式不正确！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                school=registe_school.getText().toString();
                if(school.equals("")){
                    Toast.makeText(RegisteActivity2.this,"学校不能为空！",Toast.LENGTH_SHORT).show();
                    break;
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
                        Toast.makeText(RegisteActivity2.this, "学校不正确！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
                dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("");
                dialog.show();

                final User user = new User();
                user.setUsername(name);
                user.setPassword(password);
                user.setUserSchool(school);
                user.setUserHeaderPic("null");
                user.setUserLevel(level);
                user.setEmail(email);
                user.setUserHeaderPic(headerPic);
                user.setUserSignature("null");
                user.setUserUseDate(date);
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            login(name,password);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(RegisteActivity2.this,"登录失败",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisteActivity2.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                break;
            }
            default:
                break;
        }
    }

    public void login(String name_str,String password_str){
        BmobUser.loginByAccount(name_str, password_str, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Intent intent=new Intent(RegisteActivity2.this,MainActivity.class);
                    intent.putExtra("ato",0);
                    startActivity(intent);
                    finish();
                   // Toast.makeText(RegisteActivity2.this,user.toString(),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisteActivity2.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        level=(String)list.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
