package com.example.asus.watermelonenglish.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.adapter.PracticeRecyclerviewAdapter;
import com.example.asus.watermelonenglish.bean.Practice;
import com.example.asus.watermelonenglish.myclass.Pract;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by asus on 2019/2/8.
 */

public class PracticeFragment1 extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private List<Pract> practList =new ArrayList<>();
    private PracticeRecyclerviewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String bql ="select * from practice where practiceTypeId= ?";//查询所有的游戏得分记录
        new BmobQuery<Practice>().doSQLQuery(bql,new SQLQueryListener<Practice>(){
            @Override
            public void done(BmobQueryResult<Practice> result, BmobException e) {
                if(e ==null){
                    List<Practice> list = (List<Practice>) result.getResults();
                    if(list!=null && list.size()>0){
                       // Toast.makeText(context,list.get(0).toString(),Toast.LENGTH_SHORT).show();
                        for(Practice practice:list){
                            initdata(practice);
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        },"5");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.practice_fragment1,container,false);
        //初始化
        init(view);
        return view;
    }

    private void initdata(Practice practice) {
        int images[]={R.drawable.practice_header1,R.drawable.practice_header2,R.drawable.practice_header3,R.drawable.practice_header4,R.drawable.practice_header5,
                R.drawable.practice_header6,R.drawable.practice_header7,R.drawable.practice_header8,R.drawable.practice_header9,R.drawable.practice_header10,
                R.drawable.practice_header11,R.drawable.practice_header12,R.drawable.practice_header13};
        int rand=(int)(Math.random()*images.length);
        practList.add(new Pract(images[rand],practice));
    }

    private void init(View view) {
        recyclerView=view.findViewById(R.id.translate_recyclerview);
        //设置适配器
        adapter=new PracticeRecyclerviewAdapter(practList);
     //   Toast.makeText(context,"this:"+practList.size(),Toast.LENGTH_SHORT).show();
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
