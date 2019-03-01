package com.example.asus.watermelonenglish.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.TestActivity;
import com.example.asus.watermelonenglish.adapter.ImagerViewPager;
import com.example.asus.watermelonenglish.adapter.MyViewPagerAdapter;
import com.example.asus.watermelonenglish.view.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2019/2/8.
 */

public class FragmentPractice extends Fragment implements View.OnClickListener{
    private Context context;

    private TextView practice_translate;
    private TextView practice_ss;
    private TextView practice_ct;
    private TextView practice_reader;
    private TextView practice_article;
    private TextView practice_move;//移动
    private int mCurrent=0; //当前的viewpager
    private float t=0; //当前的距离
    private float screenWidth=0;//当前的屏幕宽度

    private ViewPager practice_header; //header头部轮播图
    private CircleIndicator header_indicator; //header导航圆
    private ViewPager practice_viewpager;//主体内容

    private List<ImageView> imageViewListHeader=new ArrayList<ImageView>(); //头部轮播图
    private List<Fragment> fragmentList=new ArrayList<Fragment>();//主体内容

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_practice,container,false);
        //初始化数据
        initdata();
        //初始化
        init(view);

        return view;
    }

    /*
    * 初始化view，对view的设置
    * */
    private void init(View view) {
        getscreenwidth();
        practice_translate=view.findViewById(R.id.practice_translate);
        practice_translate.setOnClickListener(this);
        practice_ss=view.findViewById(R.id.practice_ss);
        practice_ss.setOnClickListener(this);
        practice_ct=view.findViewById(R.id.practice_ct);
        practice_ct.setOnClickListener(this);
        practice_reader=view.findViewById(R.id.practice_reader);
        practice_reader.setOnClickListener(this);
        practice_article=view.findViewById(R.id.practice_article);
        practice_article.setOnClickListener(this);
        practice_move=view.findViewById(R.id.practice_daohang_move);

        practice_header=view.findViewById(R.id.practice_header);
        header_indicator=view.findViewById(R.id.practice_header_indicator);
        practice_viewpager=view.findViewById(R.id.practice_viewpager);

        //设置头部轮播图
        ImagerViewPager imagerViewPager=new ImagerViewPager(imageViewListHeader);
        practice_header.setAdapter(imagerViewPager);
        header_indicator.setUpWithViewPager(practice_header);
        //设置主体内容viewpager
        MyViewPagerAdapter viewPagerAdapter=new MyViewPagerAdapter(this.getChildFragmentManager(),fragmentList);
        practice_viewpager.setAdapter(viewPagerAdapter);
        practice_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                move(t,t+((screenWidth/5)*(position-mCurrent)),position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initdata(){
        final int imageId[]={R.drawable.practice_header1,R.drawable.practice_header2,R.drawable.practice_header3,R.drawable.practice_header4,R.drawable.practice_header5};
        for(int i=0;i<5;i++) {//循环5次加载轮播图，并设置轮播图的点击事件
            final ImageView imageView=new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            final int k=(int)(Math.random()*imageId.length);
            Glide.with(context)
                    .load(imageId[k])
                    .placeholder(R.drawable.practice_header1)
                    .error(R.drawable.practice_header2)
                    .into(imageView);
            imageView.setTag(i);
            imageViewListHeader.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, TestActivity.class);
                    intent.putExtra("tag",(int)imageView.getTag());
                    context.startActivity(intent);
                }
            });
        }

        //主体内容
        fragmentList.add(new PracticeFragment1());
        fragmentList.add(new PracticeFragment2());
        fragmentList.add(new PracticeFragment3());
        fragmentList.add(new PracticeFragment4());
        fragmentList.add(new PracticeFragment5());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.practice_translate:{//翻译
                practice_viewpager.setCurrentItem(0);
                break;
            }
            case R.id.practice_ss:{ //单项选择
                practice_viewpager.setCurrentItem(1);
                break;
            }
            case R.id.practice_ct:{ //完形填空
                practice_viewpager.setCurrentItem(2);
                break;
            }
            case R.id.practice_reader:{ //阅读理解
                practice_viewpager.setCurrentItem(3);
                break;
            }
            case R.id.practice_article:{ //作文
                practice_viewpager.setCurrentItem(4);
                break;
            }

        }
    }

    //获取屏幕的宽度
    private void getscreenwidth(){
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth= wm.getDefaultDisplay().getWidth();
    }

    //用于导航移动
    @SuppressLint("ObjectAnimatorBinding")
    private void move(float tx, float t2x, int p){
        ObjectAnimator.ofFloat(practice_move,"translationX",tx,t2x).setDuration(100).start();
        ObjectAnimator.ofFloat(practice_move,"translationX",tx,t2x).setDuration(100).start();
        t=t2x;
        mCurrent=p;
        setColor(p);
    }
    //设置颜色
    public void setColor(int i) {
        practice_translate.setTextColor(Color.parseColor("#2b2b2b"));
        practice_ss.setTextColor(Color.parseColor("#2b2b2b"));
        practice_ct.setTextColor(Color.parseColor("#2b2b2b"));
        practice_reader.setTextColor(Color.parseColor("#2b2b2b"));
        practice_article.setTextColor(Color.parseColor("#2b2b2b"));
        switch (i){
            case 0:{
                practice_translate.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 1:{
                practice_ss.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 2:{
                practice_ct.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 3:{
                practice_reader.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 4:{
                practice_article.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
        }
    }

}
