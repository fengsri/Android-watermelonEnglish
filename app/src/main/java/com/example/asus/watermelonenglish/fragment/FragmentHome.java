package com.example.asus.watermelonenglish.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.WordActivity;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.Word;
import com.example.asus.watermelonenglish.bean.WordCollection;

import org.w3c.dom.Comment;

import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by asus on 2019/2/8.
 */

public class FragmentHome extends Fragment implements View.OnClickListener{
    private Context context;
    private User user;

    private TextView home_todaycount;
    private TextView home_reviewcount;
    private TextView home_leveltext;
    private TextView word_counttext;
    private TextView word_countnew;
    private TextView word_countall;
    private TextView word_counttext2;
    private ProgressBar progressBar;

    private String level=null;
    private int todayCount=0;
    private int reviewCount=0;
    private int wordCount=0;
    private int wordallCount=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = BmobUser.getCurrentUser(User.class);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home,container,false);
        //初始化数据
        initdate();
        //初始化
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String id=user.getObjectId();

        String bql ="select * from wordCollection where userId= ?";//查询所有的游戏得分记录
        new BmobQuery<WordCollection>().doSQLQuery(bql,new SQLQueryListener<WordCollection>(){
            @Override
            public void done(BmobQueryResult<WordCollection> result, BmobException e) {
                if(e ==null){
                    List<WordCollection> list = (List<WordCollection>) result.getResults();
                    if(list!=null && list.size()>0){
                        wordCount=list.size();
                        word_counttext.setText(wordCount+"");
                        progressBar.setMax(wordallCount);
                        progressBar.setProgress(wordCount);
                       // Toast.makeText(context,wordCount+":count",Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },id);

        String bql2 ="select * from wordCollection where userId= ? and wordCollectionDate=?";//查询所有的游戏得分记录
        new BmobQuery<WordCollection>().doSQLQuery(bql2,new SQLQueryListener<WordCollection>(){
            @Override
            public void done(BmobQueryResult<WordCollection> result, BmobException e) {
                if(e ==null){
                    List<WordCollection> list = (List<WordCollection>) result.getResults();
                    if(list!=null && list.size()>0){
                        word_countnew.setText(list.size()+"");
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },id,getTodayDate());

    }
    public String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int yearTag = calendar.get(Calendar.YEAR);//当前年
        int monthTag = calendar.get(Calendar.MONTH)+1;//当前月
        int dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        return yearTag+"-"+monthTag+"-"+dayTag;
    }

    private void initdate() {
        level=user.getUserLevel().trim();
        if(level.equals("英语四级")){
            todayCount=15;
            reviewCount=30;
            wordallCount=1000;
        }
        if(level.equals("英语六级")){
            todayCount=20;
            reviewCount=40;
            wordallCount=1500;
        }
        if(level.equals("英语托福")){
            todayCount=25;
            reviewCount=50;
            wordallCount=2000;
        }
        if(level.equals("英语雅思")){
            todayCount=30;
            reviewCount=60;
            wordallCount=2250;
        }
    }

    private void init(View view) {
        view.findViewById(R.id.home_recite).setOnClickListener(this);
        view.findViewById(R.id.home_review).setOnClickListener(this);
        view.findViewById(R.id.home_practice).setOnClickListener(this);

        word_counttext2=view.findViewById(R.id.home_wordcount_text2);
        word_counttext2.setText(todayCount+"");
        home_todaycount=view.findViewById(R.id.home_todaycount);
        home_todaycount.setText(todayCount+"");
        home_reviewcount=view.findViewById(R.id.home_reviewcount);
        home_reviewcount.setText(reviewCount+"");
        home_leveltext=view.findViewById(R.id.home_leveltext);
        home_leveltext.setText(level);

        word_counttext=view.findViewById(R.id.home_wordcount_text);
        word_countall=view.findViewById(R.id.home_wordallcount_text);
        word_countall.setText("/ "+wordallCount);

        word_countnew=view.findViewById(R.id.home_wordcount_new);
        progressBar=view.findViewById(R.id.home_progressbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_recite:{//背诵单词
                Intent intent=new Intent(context, WordActivity.class);
                intent.putExtra("tag",1);
                intent.putExtra("todayCount",todayCount);
                context.startActivity(intent);
                break;
            }
            case R.id.home_review:{ //复习
                ((MainActivity)context).replace(new FragmentReview());
                ((MainActivity)context).onclickSet(2);
                break;
            }
            case R.id.home_practice:{ //练习
                ((MainActivity)context).replace(new FragmentPractice());
                ((MainActivity)context).onclickSet(3);
                break;
            }

        }
    }
}
