package com.example.asus.watermelonenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.watermelonenglish.bean.Person;
import com.example.asus.watermelonenglish.bean.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by asus on 2019/2/24.
 */

public class RegisteActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private EditText registe_name;
    private EditText registe_email;
    private EditText registe_pd;
    private EditText registe_pd2;
    private EditText registe_school;
    private Spinner registe_level;
    private Button registe_bt;
    private List list=new ArrayList<String>();
    private String name;
    private String email;
    private String password;
    private String password2;
    private String school;
    private String level;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registe);

        init();

    }


    private void init() {
        registe_name=findViewById(R.id.registe_name);
        registe_email=findViewById(R.id.registe_email);
        registe_school=findViewById(R.id.registe_school);
        registe_level=findViewById(R.id.registe_level);
        registe_bt=findViewById(R.id.registe_bt);
        registe_pd=findViewById(R.id.registe_pd);
        registe_pd2=findViewById(R.id.registe_pd2);
        registe_bt.setOnClickListener(this);
         /*设置数据源*/
        list.add("我的等级");
        list.add("英语四级");
        list.add("英语六级");
        list.add("英语托福");
        list.add("英语雅思");
        /*新建适配器*/
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        /*spDown加载适配器*/
        registe_level.setAdapter(adapter);

        /*soDown的监听器*/
        registe_level.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registe_bt:{
                name=registe_name.getText().toString();
                if(name.equals("")) {
                    Toast.makeText(RegisteActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String re="[A-Za-z0-9]{4,10}";
                    if(!name.matches(re)){
                        Toast.makeText(RegisteActivity.this,"用户名由4-10个字母数字构成！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                email=registe_email.getText().toString();
                if(email.equals("")){
                    Toast.makeText(RegisteActivity.this,"邮箱不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String regex = "^(\\w)+@(\\w)+(\\.)\\w+\\.?\\w+";
                    if(!email.matches(regex)){
                        Toast.makeText(RegisteActivity.this,"邮箱格式不正确！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                password=registe_pd.getText().toString();
                password2=registe_pd2.getText().toString();
                if(password.equals("")){
                    Toast.makeText(RegisteActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String re="[A-Za-z0-9]{4,10}";
                    if(!password.matches(re)){
                        Toast.makeText(RegisteActivity.this,"密码由4-10个字母数字构成！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(password2.equals("")){
                    Toast.makeText(RegisteActivity.this,"确认密码不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    String re="[A-Za-z0-9]{4,10}";
                    if(!password2.matches(re)){
                        Toast.makeText(RegisteActivity.this,"确认密码由4-10个字母数字构成！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(!password2.equals(password)){
                    Toast.makeText(RegisteActivity.this,"密码不一致！",Toast.LENGTH_SHORT).show();
                    break;
                }

                school=registe_school.getText().toString();
                if(school.equals("")){
                    Toast.makeText(RegisteActivity.this,"学校不能为空！",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegisteActivity.this, "学校不正确！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }



                Calendar calendar = Calendar.getInstance();
                int yearTag = calendar.get(Calendar.YEAR);//当前年
                int monthTag = calendar.get(Calendar.MONTH)+1;//当前月
                int dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
                String date=yearTag+"-"+monthTag+"-"+dayTag;

                final User user = new User();
                user.setUsername(name);
                user.setPassword(password);
                user.setUserSchool(school);
                user.setUserHeaderPic("null");
                user.setUserLevel(level);
                user.setEmail(email);
                user.setUserSignature("null");
                user.setUserUseDate(date);
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Intent intent=new Intent(RegisteActivity.this,LoginUser.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisteActivity.this,"注册失败:用户名或邮箱已存在",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                final User user = new User();
//                user.setUsername(name);
//                user.setPassword(password);
//                user.setUserSchool(school);
//                user.setUserHeaderPic("null");
//                user.setUserLevel(level);
//                user.setEmail(email);
//                user.setUserSignature("null");
//                user.setUserUseDate(date);
//                Toast.makeText(RegisteActivity.this,"前："+user.toString(),Toast.LENGTH_SHORT).show();
//
//                user.signUp(new SaveListener<User>() {
//                    @Override
//                    public void done(User user, BmobException e) {
//                        if (e == null) {
//
//                           Toast.makeText(RegisteActivity.this,user.toString(),Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(RegisteActivity.this,"注册失败"+e.toString(),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        level=(String)list.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
