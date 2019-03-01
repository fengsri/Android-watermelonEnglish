package com.example.asus.watermelonenglish.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.WordActivity;
import com.example.asus.watermelonenglish.adapter.MyViewPagerAdapter;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2019/2/8.
 */

public class FragmentReview extends Fragment implements View.OnClickListener {
    private Context context;
    private EditText review_input;
    private ImageView review_search;
    private TextView daohang_textview1;
    private TextView daohang_textview2;
    private TextView daohangtiao;
    private RelativeLayout review_daohang;
    private ViewPager review_viewpager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private float t = 0;
    private int mCurrent = 0;
    private float moveWidth = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_review, container, false);
        //初始化数据
        initdata();
        //初始化
        init(view);
        return view;
    }

    /*
    *初始化数据
     *  */
    private void initdata() {
        fragmentList.add(new ReviewFragment1());
        fragmentList.add(new ReviewFragment2());

    }

    private void init(View view) {
        review_input = view.findViewById(R.id.review_input);
        review_search = view.findViewById(R.id.review_search);
        review_search.setOnClickListener(this);
        daohang_textview1 = view.findViewById(R.id.review_dh_textview1);
        daohang_textview1.setOnClickListener(this);
        daohang_textview2 = view.findViewById(R.id.review_dh_textview2);
        daohang_textview2.setOnClickListener(this);
        daohangtiao = view.findViewById(R.id.review_dht);
        review_daohang = view.findViewById(R.id.review_daohang);
        review_viewpager = view.findViewById(R.id.review_viewpager);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this.getChildFragmentManager(), fragmentList);
        review_viewpager.setAdapter(adapter);
        getscreenwidth();
        review_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                move(t, t + ((moveWidth / 2) * (position - mCurrent)), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.review_dh_textview1: {
                review_viewpager.setCurrentItem(0);
                break;
            }
            case R.id.review_dh_textview2: {
                review_viewpager.setCurrentItem(1);
                break;
            }
            case R.id.review_search: {
                final String w = review_input.getText().toString();
                if (w.equals("")) {
                    Toast.makeText(context,"请输入单词",Toast.LENGTH_SHORT).show();
                } else {
                    final String[] items = new String[]{"英文->中文", "中文->英文"};
                    // 创建对话框构建器yy
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // 设置参数
                    builder.setIcon(R.drawable.fangxiang).setTitle("翻译")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, WordActivity.class);
                                    intent.putExtra("tag", 0);
                                    intent.putExtra("input",w);
                                    review_input.setText("");
                                    if (which == 0) {
                                      intent.putExtra("dto",1);
                                    } else if (which == 1) {
                                        intent.putExtra("dto",1);
                                    }
                                    context.startActivity(intent);
                                }
                            });
                    builder.create().show();
                }
            }
            default:
            break;
    }

}

    //获取移动的宽度
    private void getscreenwidth() {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        review_daohang.measure(width, height);
        moveWidth = review_daohang.getMeasuredWidth();
       // Toast.makeText((MainActivity) (context), moveWidth + "", Toast.LENGTH_SHORT).show();
    }

    //用于导航移动
    @SuppressLint("ObjectAnimatorBinding")
    private void move(float tx, float t2x, int p) {
        ObjectAnimator.ofFloat(daohangtiao, "translationX", tx, t2x).setDuration(100).start();
        ObjectAnimator.ofFloat(daohangtiao, "translationX", tx, t2x).setDuration(100).start();
        t = t2x;
        mCurrent = p;
        setColor(p);
    }

    //设置颜色
    public void setColor(int i) {
        daohang_textview1.setTextColor(Color.parseColor("#2b2b2b"));
        daohang_textview2.setTextColor(Color.parseColor("#2b2b2b"));
        switch (i) {
            case 0: {
                daohang_textview1.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 1: {
                daohang_textview2.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
        }
    }


}
