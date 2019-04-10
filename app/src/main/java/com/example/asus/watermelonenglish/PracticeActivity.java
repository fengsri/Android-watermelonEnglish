package com.example.asus.watermelonenglish;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.watermelonenglish.bean.Person;
import com.example.asus.watermelonenglish.bean.Practice;
import com.example.asus.watermelonenglish.bean.PracticeCheck;
import com.example.asus.watermelonenglish.bean.PracticeCollection;
import com.example.asus.watermelonenglish.bean.PracticeTopic;
import com.example.asus.watermelonenglish.bean.PracticeType;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.WordType;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus on 2019/2/18.
 */

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton cang;
    private int tag=0;
    private TextView title;
    private TextView text;
    private EditText myanswer;
    private TextView answerCheck;
    private TextView answertext;

    private Button tijiaobt;
    private Button answerbt;

    private String objectId;
    private String practiceTitle;
    private String practiceTypeId;
    private String practiceText;
    private String practiceId;
    private String practiceTypeName;

    private User user;
    private String userId=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        initdata();
        init();
    }

    private void initdata() {
        user = BmobUser.getCurrentUser(User.class);
        if(null != user){ //未登录
            userId=user.getObjectId();
        }

        Intent intent=getIntent();
        objectId=intent.getStringExtra("objectId");
        practiceTitle=intent.getStringExtra("practiceTitle");
        practiceTypeId=intent.getStringExtra("practiceTypeId").trim();
        practiceText=intent.getStringExtra("practiceText");
        practiceId=intent.getStringExtra("practiceId");

        String bql ="select * from practiceType where practiceTypeId= ?";
        new BmobQuery<PracticeType>().doSQLQuery(bql,new SQLQueryListener<PracticeType>(){
            @Override
            public void done(BmobQueryResult<PracticeType> result, BmobException e) {
                if(e ==null){
                    List<PracticeType> list = (List<PracticeType>) result.getResults();
                    if(list!=null && list.size()>0){
                        PracticeType practiceType=list.get(0);
                        practiceTypeName=practiceType.getPracticeTypeName();
                        title.setText(practiceTypeName);
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },practiceTypeId);
    }

    private void init() {
        Toolbar toolbar=findViewById(R.id.practice_header_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar=findViewById(R.id.practice_collapsing_toolbar);
        collapsingToolbar.setTitle(practiceTitle);

        ImageView imageView=findViewById(R.id.practice_header_image);
        TextView textView=findViewById(R.id.practice_word_textview);

        Glide.with(this).load(R.drawable.word_header_bg2).into(imageView);

        cang=findViewById(R.id.practice_word_float);
        cang.setOnClickListener(this);

        text=findViewById(R.id.practice_word_textview);
        text.setText(practiceText);
        title=findViewById(R.id.practice_word_texttitle);

        myanswer=findViewById(R.id.practice_my_answer);
        if(practiceTypeId.equals("1")){
            myanswer.setHint("输入答案");
        }
        if(practiceTypeId.equals("5")){
            myanswer.setHint("输入答案");
        }
        answertext=findViewById(R.id.practice_answer);
        answerCheck = findViewById(R.id.practice_answer_check);
        tijiaobt=findViewById(R.id.practice_tj);
        tijiaobt.setOnClickListener(this);
        answerbt=findViewById(R.id.practice_daan);
        answerbt.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.practice_word_float:{
                if(tag==0){
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(cang, "rotation", 0, 360).setDuration(500);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            cang.setImageResource(R.drawable.word_zan2);
                            shoucang(tag);
                            tag++;
                        }
                    });
                    objectAnimator.start();
                }else{
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(cang, "rotation", 0, 360).setDuration(500);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            cang.setImageResource(R.drawable.cang);
                            shoucang(tag);
                            tag--;
                        }
                    });
                    objectAnimator.start();
                    Toast.makeText(PracticeActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.practice_tj:{
               // answertext.setVisibility(View.VISIBLE);
                String text = myanswer.getText().toString().trim();
                tijiao(text);
                break;
            }
            case R.id.practice_daan:{
                answertext.setVisibility(View.VISIBLE);
                getanswer();
                break;
            }
            default:
                break;
        }
    }

    public void tijiao(String text){
        if(practiceTypeId.equals("1")){
            getanswer();
        }else if(practiceTypeId.equals("5")){
            getanswer();
        }else{
            checkanswer(text);
        }
        shoucang(0);
    }

    private void checkanswer(String text) {
        String regex ="";
        if(practiceTypeId.equals("2")){
            regex = "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z]";
        }else if(practiceTypeId.equals("3")) {
            regex = "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]"+
                    "|"+
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]";
        }else if(practiceTypeId.equals("4")){ //匹配
            regex = "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]," +
                    "[0-9]{2}-[A-Za-z],[0-9]{2}-[A-Za-z]";
        }

        if(text.equals("")){
            Toast.makeText(PracticeActivity.this, "请输入答案！", Toast.LENGTH_SHORT).show();
        }else {
            if (!text.matches(regex)) {
                Toast.makeText(PracticeActivity.this, "题未做完 或者 答案格式不正确！", Toast.LENGTH_SHORT).show();
            } else {
                answerCheck.setVisibility(View.VISIBLE);
                checktext(text);
            }
        }
    }
    public void checktext(String text){
        final String ans[]=text.split(",");

        String bql ="select * from PracticeCheck where practiceId= ?";
        new BmobQuery<PracticeCheck>().doSQLQuery(bql,new SQLQueryListener<PracticeCheck>(){
            @Override
            public void done(BmobQueryResult<PracticeCheck> result, BmobException e) {
                if(e ==null){
                    List<PracticeCheck> list = (List<PracticeCheck>) result.getResults();
                    if(list!=null && list.size()>0){
                        PracticeCheck practiceCheck=list.get(0);
                        String btext=practiceCheck.getPracticeText();
                        String ans2[] = btext.split(",");
                        check(ans,ans2);
                        Toast.makeText(PracticeActivity.this,"答案提交成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Toast.makeText(PracticeActivity.this,"失败",Toast.LENGTH_SHORT).show();
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },practiceId);
    }

    public void check(String ans1[],String ans2[]){
        String buff="";
        for(int i=0;i<ans1.length;i++){
            String s1=ans1[i].trim().split("-")[1].toLowerCase();
            String s2=ans2[i].trim().split("-")[1].toLowerCase();
            if(i==0){
                buff=ans2[i].trim().split("-")[0];
            }else{
                buff = buff+"\t\t"+ans2[i].trim().split("-")[0];
            }
            if(s1.equals(s2)){
                buff=buff+"√";
            }else{
                buff=buff+"×";
            }
        }
        answerCheck.setText("检查：\n"+buff);

    }

    public void shoucang(int tag){
        PracticeCollection p2 = new PracticeCollection();
        p2.setUserId(userId);
        p2.setPracticeId(practiceId);
        p2.setPracticeDate(getTodayDate());
        if(tag==0 && userId!=null){
            p2.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        Toast.makeText(PracticeActivity.this,"成功",Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(PracticeActivity.this,"已经收藏",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else if(tag==1 && userId!=null){
            p2.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Toast.makeText(PracticeActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    }else{
                    }
                }
            });
        }
    }

    public void getanswer(){
        String bql ="select * from practiceTopic where practiceId= ?";
        new BmobQuery<PracticeTopic>().doSQLQuery(bql,new SQLQueryListener<PracticeTopic>(){
            @Override
            public void done(BmobQueryResult<PracticeTopic> result, BmobException e) {
                if(e ==null){
                    List<PracticeTopic> list = (List<PracticeTopic>) result.getResults();
                    if(list!=null && list.size()>0){
                        PracticeTopic practiceTopic=list.get(0);
                        answertext.setText("答案：\n"+"id:"+practiceId+"\n"+practiceTopic.getRight());
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Toast.makeText(PracticeActivity.this,"失败",Toast.LENGTH_SHORT).show();
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },practiceId);
    }
    public String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int yearTag = calendar.get(Calendar.YEAR);//当前年
        int monthTag = calendar.get(Calendar.MONTH)+1;//当前月
        int dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        return yearTag+"-"+monthTag+"-"+dayTag;
    }
}