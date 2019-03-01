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
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.SelfShowActivity;
import com.example.asus.watermelonenglish.bean.Practice;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.WordCollection;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by asus on 2019/2/8.
 */

public class FragmentSelf extends Fragment implements View.OnClickListener{
    private Context context;
    private User user;

    private TextView dayCounttext;
    private TextView wordCounttext;

    private int dayCount=0;
    private int wordCount=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = BmobUser.getCurrentUser(User.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_self,container,false);
        //初始化
        init(view);
        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.fragment_self_image).setOnClickListener(this);
        view.findViewById(R.id.fragment_self_jibenxinxi).setOnClickListener(this);
        view.findViewById(R.id.fragment_self_dirction).setOnClickListener(this);
        view.findViewById(R.id.fragment_self_rank).setOnClickListener(this);
        view.findViewById(R.id.fragment_self_shezhi).setOnClickListener(this);
        dayCounttext=view.findViewById(R.id.fragment_self_day);
        wordCounttext=view.findViewById(R.id.fragment_self_wordcount);

        String startDay=user.getUserUseDate();
        Calendar calendar = Calendar.getInstance();
        int yearTag = calendar.get(Calendar.YEAR);//当前年
        int monthTag = calendar.get(Calendar.MONTH)+1;//当前月
        int dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        String endDay=yearTag+"-"+(monthTag)+"-"+dayTag;

        dayCount=this.longOfTwoDate(startDay,endDay);
        dayCounttext.setText(dayCount+"");
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
                        wordCounttext.setText(wordCount+"");
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                    //  Toast.makeText(context,"查询成功"+list.size(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"查询失败",Toast.LENGTH_SHORT).show();
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_self_image:{//头像
                break;
            }
            case R.id.fragment_self_jibenxinxi:{ //基本信息
                Intent intent=new Intent(context, SelfShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("key",0);
                intent.putExtra("data",bundle);
                context.startActivity(intent);
                break;
            }
            case R.id.fragment_self_dirction:{ //选择方向
                Intent intent=new Intent(context, SelfShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("key",1);
                intent.putExtra("data",bundle);
                context.startActivity(intent);
                break;
            }
            case R.id.fragment_self_rank:{ //排名
                Intent intent=new Intent(context, SelfShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("key",2);
                intent.putExtra("data",bundle);
                context.startActivity(intent);
                break;
            }
            case R.id.fragment_self_shezhi:{ //设置
                Intent intent=new Intent(context, SelfShowActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("key",3);
                intent.putExtra("data",bundle);
                context.startActivity(intent);
                break;
            }
        }
    }

    public int longOfTwoDate(String first, String second){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date firstdate = null;
        Date seconddate = null;
        try {
            firstdate = format.parse(first);
            seconddate = format.parse(second);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstdate);
        int cnt = 0;
        while (calendar.getTime().compareTo(seconddate) != 0) {
            calendar.add(Calendar.DATE, 1);
            cnt++;
        }
        return cnt;
    }

}
