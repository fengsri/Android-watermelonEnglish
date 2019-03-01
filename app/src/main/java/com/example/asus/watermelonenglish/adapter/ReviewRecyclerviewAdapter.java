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

import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.PracticeActivity;
import com.example.asus.watermelonenglish.R;
import com.example.asus.watermelonenglish.bean.Practice;
import com.example.asus.watermelonenglish.myclass.Pract;

import java.util.List;

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
                final String[] items = new String[] { "微信朋友圈","微信群聊" };
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
                                }
                            }
                        });
                builder.create().show();
            }
        });
        holder.shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = new String[] { "微信朋友圈","微信群聊" };
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
                                }
                            }
                        });
                builder.create().show();
            }
        });
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
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.review_recyclerview_image);
            textView=itemView.findViewById(R.id.review_recyclerview_title);
            shareImage=itemView.findViewById(R.id.review_recyclerview_shareimage);
            shareText=itemView.findViewById(R.id.review_recyclerview_share);
        }
    }

}
