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
import com.example.asus.watermelonenglish.bean.Practice;
import com.example.asus.watermelonenglish.bean.PracticeTopic;
import com.example.asus.watermelonenglish.bean.PracticeType;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by asus on 2019/2/18.
 */

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton cang;
    private int tag=0;
    private TextView title;
    private TextView text;
    private EditText myanswer;
    private TextView answertext;

    private Button tijiaobt;
    private Button answerbt;

    private String objectId;
    private String practiceTitle;
    private String practiceTypeId;
    private String practiceText;
    private String practiceId;
    private String practiceTypeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        initdata();
        init();
    }

    private void initdata() {
        Intent intent=getIntent();
        objectId=intent.getStringExtra("objectId");
        practiceTitle=intent.getStringExtra("practiceTitle");
        practiceTypeId=intent.getStringExtra("practiceTypeId");
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
        answertext=findViewById(R.id.practice_answer);

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
                        }
                    });
                    objectAnimator.start();
                    Toast.makeText(PracticeActivity.this,"收藏",Toast.LENGTH_SHORT).show();
                    tag++;
                }else{
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(cang, "rotation", 0, 360).setDuration(500);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            cang.setImageResource(R.drawable.cang);
                        }
                    });
                    objectAnimator.start();
                    Toast.makeText(PracticeActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    tag--;
                }
                break;
            }
            case R.id.practice_tj:{
                answertext.setVisibility(View.VISIBLE);
                getanswer();
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

    public void getanswer(){
        String bql ="select * from practiceTopic where practiceId= ?";
        new BmobQuery<PracticeTopic>().doSQLQuery(bql,new SQLQueryListener<PracticeTopic>(){
            @Override
            public void done(BmobQueryResult<PracticeTopic> result, BmobException e) {
                if(e ==null){
                    List<PracticeTopic> list = (List<PracticeTopic>) result.getResults();
                    if(list!=null && list.size()>0){
                        PracticeTopic practiceTopic=list.get(0);
                        answertext.setText("答案：\n"+practiceTopic.getRight());
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
}