package com.example.asus.watermelonenglish.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.WordCollection;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by asus on 2019/2/8.
 */

public class SelfShowFragment3 extends Fragment implements View.OnClickListener{
    private Context context;
    private TextView count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.self_show_fragment3,container,false);
        //初始化
        init(view);
        return view;
    }

    private void init(View view) {
        count=view.findViewById(R.id.self_fragment3_count);
        getcount();
    }
    public void getcount(){
        BmobQuery<User> query = new BmobQuery<User>();
        query.count(User.class, new CountListener() {
            @Override
            public void done(Integer count1, BmobException e) {
                if(e==null){
                    count.setText("当前使用量"+count1+"人");
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

//        String bql ="select * from _User";//查询所有的游戏得分记录
//        new BmobQuery<User>().doSQLQuery(bql,new SQLQueryListener<User>(){
//            @Override
//            public void done(BmobQueryResult<User> result, BmobException e) {
//                if(e ==null){
//                    List<User> list = (List<User>) result.getResults();
//                    if(list!=null && list.size()>0){
//                        int c=list.size();
//                        count.setText("当前使用量"+c+"人");
//                    }else{
//                        Log.i("smile", "查询成功，无数据返回");
//                    }
//                    //  Toast.makeText(context,"查询成功"+list.size(),Toast.LENGTH_SHORT).show();
//                }else{
//                    //Toast.makeText(context,"查询失败",Toast.LENGTH_SHORT).show();
//                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
//                }
//            }
//        });
    }

    @Override
    public void onClick(View view) {

    }
}
