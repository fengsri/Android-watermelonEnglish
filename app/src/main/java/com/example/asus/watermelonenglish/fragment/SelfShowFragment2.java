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
import android.widget.Toast;

import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.WordCollection;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by asus on 2019/2/8.
 */

public class SelfShowFragment2 extends Fragment implements View.OnClickListener{
    private Context context;
    private User user;
    private TextView flevel;
    private TextView fcount;
    private String level;
    private int wordcount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = BmobUser.getCurrentUser(User.class);
        level=user.getUserLevel();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.self_show_fragment2,container,false);
        //初始化
        init(view);
        getcount();
        return view;
    }

    private void init(View view) {
        flevel=view.findViewById(R.id.self_fragment2_level);
        flevel.setText("当前"+level);
        fcount=view.findViewById(R.id.self_fragment2_count);
    }

    public void getcount(){
        String id=user.getObjectId();
        String bql ="select * from WordCollection where userId= ?";//查询所有的游戏得分记录
        new BmobQuery<WordCollection>().doSQLQuery(bql,new SQLQueryListener<WordCollection>(){
            @Override
            public void done(BmobQueryResult<WordCollection> result, BmobException e) {
                if(e ==null){
                    List<WordCollection> list = (List<WordCollection>) result.getResults();
                    if(list!=null && list.size()>0){
                        wordcount=list.size();
                        fcount.setText("当前掌握词汇量"+wordcount+"个");
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                    //  Toast.makeText(context,"查询成功"+list.size(),Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(context,"查询失败",Toast.LENGTH_SHORT).show();
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },id);
    }

    @Override
    public void onClick(View view) {

    }
}
