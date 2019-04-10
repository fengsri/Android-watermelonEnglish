package com.example.asus.watermelonenglish.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.PracticeActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.bean.Practice;
import com.example.asus.watermelonenglish.bean.PracticeCollection;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.myclass.Pract;

import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus on 2019/2/15.
 */

public class ReviewRecyclerviewAdapter extends RecyclerView.Adapter<ReviewRecyclerviewAdapter.ViewHolder> {
    private List<Pract> practList;
    private Context context;

    public ReviewRecyclerviewAdapter(List<Pract> practList) {
        this.practList = practList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recyclerview_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pract pract = practList.get(position);
        final Practice practice=pract.getPractice();
        holder.imageView.setImageResource(pract.getImageid());
        holder.textView.setText(practice.getPracticeTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PracticeActivity.class);
                intent.putExtra("objectId",practice.getObjectId());
                intent.putExtra("practiceTitle",practice.getPracticeTitle());
                intent.putExtra("practiceTypeId",practice.getPracticeTypeId());
                intent.putExtra("practiceText",practice.getPracticeText());
                intent.putExtra("practiceId",practice.getPracticeId());
                context.startActivity(intent);
            }
        });
        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = new String[] { "微信朋友圈","微信群聊","QQ群聊","QQ空间"};
                // 创建对话框构建器
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // 设置参数
                builder.setIcon(R.drawable.share).setTitle("分享")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    ((MainActivity)(context)).shareText("今天我在复习："+ practice.getPracticeTitle(),0);
                                }else if(which==1){
                                    ((MainActivity)(context)).shareText("今天我在复习："+ practice.getPracticeTitle(),1);
                                }else if(which==2){
                                    ((MainActivity)(context)).shareToQQ("今天我在练习："+ practice.getPracticeTitle());
                                }else if(which==3){
                                    ((MainActivity)(context)).shareToQZone("今天我在练习："+ practice.getPracticeTitle());
                                }
                            }
                        });
                builder.create().show();
            }
        });
        holder.shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = new String[] { "微信朋友圈","微信群聊","QQ群聊","QQ空间"};
                // 创建对话框构建器
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // 设置参数
                builder.setIcon(R.drawable.share).setTitle("分享")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    ((MainActivity)(context)).shareText("今天我在复习："+ practice.getPracticeTitle(),0);
                                }else if(which==1){
                                    ((MainActivity)(context)).shareText("今天我在复习："+ practice.getPracticeTitle(),1);
                                }else if(which==2){
                                    ((MainActivity)(context)).shareToQQ("今天我在练习："+ practice.getPracticeTitle());
                                }else if(which==3){
                                    ((MainActivity)(context)).shareToQZone("今天我在练习："+ practice.getPracticeTitle());
                                }
                            }
                        });
                builder.create().show();
            }
        });
        holder.cangText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoucang(practice.getPracticeId());
            }
        });
        holder.cangImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoucang(practice.getPracticeId());
            }
        });
    }

    public void shoucang(String practiceId){
        User user = BmobUser.getCurrentUser(User.class);
        if(null != user){ //未登录
            String userId=user.getObjectId();
            PracticeCollection p2 = new PracticeCollection();
            p2.setUserId(userId);
            p2.setPracticeId(practiceId);
            p2.setPracticeDate(getTodayDate());
            p2.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"已经收藏",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return practList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        ImageView shareImage;
        TextView shareText;
        ImageView cangImage;
        TextView cangText;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.review_recyclerview_image);
            textView=itemView.findViewById(R.id.review_recyclerview_title);
            shareImage=itemView.findViewById(R.id.review_recyclerview_shareimage);
            shareText=itemView.findViewById(R.id.review_recyclerview_share);
            cangImage=itemView.findViewById(R.id.review_recyclerview_shoucang);
            cangText=itemView.findViewById(R.id.review_recyclerview_shoucangtext);
        }
    }

    public String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int yearTag = calendar.get(Calendar.YEAR);//当前年
        int monthTag = calendar.get(Calendar.MONTH)+1;//当前月
        int dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        return yearTag+"-"+monthTag+"-"+dayTag;
    }

}
