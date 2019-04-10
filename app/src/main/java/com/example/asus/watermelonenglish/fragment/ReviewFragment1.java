package com.example.asus.watermelonenglish.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.WordActivity;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.Word;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by asus on 2019/2/8.
 */

public class ReviewFragment1 extends Fragment implements View.OnClickListener{
    private Context context;
    private User user;
    private CardView review_slzw;
    private CardView review_sc;
    private CardView review_gpch;
    private CardView review_sj;

    private ImageView imageView_clock;

    private TextView cardText1;
    private TextView cardText2;
    private TextView cardText3;
    private TextView cardText4;


    private TextView textView_year;
    private TextView textView_month;
    private TextView textView_day;

    private TextView textView_nnain;
    private TextView textView_nmonth;
    private TextView textView_day3;

    private ImageView tbImageview;

    private String year="";
    private String month="";
    private String day="";
    private int dayTag=0;
    private int yearTag=0;
    private int monthTag=0;
    private int cardt1=0;
    private int cardt2=0;
    private int cardt3=0;
    private int cardt4=0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = BmobUser.getCurrentUser(User.class);
        String date=user.getUserUseDate();
        String []strs=date.split("-");
        if(strs.length==3) {
            year = strs[0];
            month=strs[1];
            day=strs[2];
        }
        String level=user.getUserLevel().trim();
        if(level.equals("英语四级")){
            cardt1=15;
            cardt2=30;
        }
        if(level.equals("英语六级")){
            cardt1=20;
            cardt2=40;
        }
        if(level.equals("英语雅思")){
            cardt1=25;
            cardt2=50;
        }
        if(level.equals("英语托福")){
            cardt1=30;
            cardt2=60;
        }
        cardt3=200;
        cardt4=50;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.review_fragment1,container,false);
        //初始化日期
        initdate();
        //初始化
        init(view);
        return view;
    }

    private void initdate() {
        Calendar calendar = Calendar.getInstance();
        yearTag = calendar.get(Calendar.YEAR);//当前年
        monthTag = calendar.get(Calendar.MONTH)+1;//当前月
        dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
    }

    private void init(View view) {
        review_slzw=view.findViewById(R.id.review_slzw);
        review_slzw.setOnClickListener(this);
        review_sc=view.findViewById(R.id.review_sc);
        review_sc.setOnClickListener(this);
        review_gpch=view.findViewById(R.id.review_gpch);
        review_gpch.setOnClickListener(this);
        review_sj=view.findViewById(R.id.review_sj);
        review_sj.setOnClickListener(this);

        imageView_clock=view.findViewById(R.id.review_clock);
        imageView_clock.setOnClickListener(this);

        textView_year=view.findViewById(R.id.rf_year1);
        textView_year.setText(year);
        textView_month=view.findViewById(R.id.rf_month1);
        textView_month.setText(month);
        textView_day=view.findViewById(R.id.rf_day1);
        textView_day.setText(day);

        textView_nnain=view.findViewById(R.id.rf_year2);
        textView_nnain.setText(yearTag+"");
        textView_nmonth=view.findViewById(R.id.rf_month2);
        textView_nmonth.setText(monthTag+"");
        textView_day3=view.findViewById(R.id.rf_day2);
        textView_day3.setText(dayTag+"");

        tbImageview=view.findViewById(R.id.word_date_tbimage);
        tbImageview.setOnClickListener(this);

        cardText1=view.findViewById(R.id.rf_card_text1);
        cardText1.setText(cardt1+"");
        cardText2=view.findViewById(R.id.rf_card_text2);
        cardText2.setText(cardt2+"");
        cardText3=view.findViewById(R.id.rf_card_text3);
        cardText3.setText(cardt3+"");
        cardText4=view.findViewById(R.id.rf_card_text4);
        cardText4.setText(cardt4+"");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        ObjectAnimator objectAnimator = null;
        ObjectAnimator objectAnimator2=null;
        final Intent intent=new Intent((MainActivity)(context), WordActivity.class);
        intent.putExtra("tag",1);
        Bundle bundle=null;
        String todayCount="";
        int sway = 1;
        switch (view.getId()){
            case R.id.review_slzw:{
                objectAnimator = ObjectAnimator.ofFloat(review_slzw, "scaleX", 0.7F, 1F).setDuration(500);
                objectAnimator2 = ObjectAnimator.ofFloat(review_slzw, "scaleY", 0.7F, 1F).setDuration(500);
                bundle=new Bundle();
                todayCount=cardText1.getText().toString();
                sway = 1;
                break;
            }
            case R.id.review_sc:{
                objectAnimator = ObjectAnimator.ofFloat(review_sc, "scaleX", 0.7F, 1F).setDuration(500);
                objectAnimator2 = ObjectAnimator.ofFloat(review_sc, "scaleY", 0.7F, 1F).setDuration(500);
                bundle=new Bundle();
                todayCount=cardText2.getText().toString();
                sway = 2;
                break;
            }
            case R.id.review_gpch:{
                objectAnimator = ObjectAnimator.ofFloat(review_gpch, "scaleX", 0.7F, 1F).setDuration(500);
                objectAnimator2 = ObjectAnimator.ofFloat(review_gpch, "scaleY", 0.7F, 1F).setDuration(500);
                bundle=new Bundle();
                todayCount=cardText3.getText().toString();
                sway = 3;
                break;
            }
            case R.id.review_sj:{
                objectAnimator = ObjectAnimator.ofFloat(review_sj, "scaleX", 0.7F, 1F).setDuration(500);
                objectAnimator2 = ObjectAnimator.ofFloat(review_sj, "scaleY", 0.7F, 1F).setDuration(500);
                bundle=new Bundle();
                todayCount=cardText4.getText().toString();
                sway = 4;
                break;
            }
            case R.id.review_clock:{
                DatePickerDialog dialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                    }
                },yearTag,monthTag,dayTag);
                dialog.show();
                break;
            }
            case R.id.word_date_tbimage:{
                ObjectAnimator objectAnimatortb =ObjectAnimator.ofFloat(tbImageview, "rotation", 0, 360).setDuration(500);
                objectAnimatortb.start();
                break;
            }
        }
        if(bundle!=null) {
            intent.putExtra("sway",sway);
            intent.putExtra("todayCount",Integer.parseInt(todayCount));
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    context.startActivity(intent);
                }
            });
            objectAnimator.start();
            objectAnimator2.start();
        }
    }
}
