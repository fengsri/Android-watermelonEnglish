package com.example.asus.watermelonenglish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.fragment.FragmentHome;
import com.example.asus.watermelonenglish.fragment.FragmentPractice;
import com.example.asus.watermelonenglish.fragment.FragmentReview;
import com.example.asus.watermelonenglish.fragment.FragmentSelf;
import com.example.asus.watermelonenglish.myclass.AudioMgr;
import com.example.asus.watermelonenglish.myclass.EnglishWord;
import com.example.asus.watermelonenglish.util.SaveWord;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView home_image;
    private ImageView review_image;
    private ImageView practice_image;
    private ImageView self_image;
    private TextView home_textview;
    private TextView review_textview;
    private TextView practice_textview;
    private TextView self_textview;

    private IWXAPI api;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //初始化
        init();

    }

    //初始化数据
    @Override
    protected void onStart() {
        super.onStart();
        LitePal.getDatabase();
        List<EnglishWord> list= DataSupport.findAll(EnglishWord.class);
        int wordCount= list.size();
        if(wordCount==0){
           // SaveWord.saveEnglistword(this);
        }else{
            Toast.makeText(this,"wordSize:"+wordCount,Toast.LENGTH_SHORT).show();
          //  Toast.makeText(this,list.get(4).toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void init() {
        //第一：默认初始化
        api= WXAPIFactory.createWXAPI(this,"wxf371098a435d7f2b");
        api.registerApp("wxf371098a435d7f2b");

        findViewById(R.id.home1).setOnClickListener(this);
        findViewById(R.id.review).setOnClickListener(this);
        findViewById(R.id.practice).setOnClickListener(this);
        findViewById(R.id.self).setOnClickListener(this);

        home_image = findViewById(R.id.home_image);
        review_image = findViewById(R.id.review_image);
        practice_image = findViewById(R.id.practice_image);
        self_image = findViewById(R.id.self_image);
        home_textview = findViewById(R.id.home_textview);
        review_textview = findViewById(R.id.review_textview);
        practice_textview = findViewById(R.id.practice_textview);
        self_textview = findViewById(R.id.self_textview);

        replace(new FragmentHome());
        onclickSet(1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home1: {/*点击首页*/
                //Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                replace(new FragmentHome());
                onclickSet(1);
                break;
            }
            case R.id.review: {
              //  Toast.makeText(MainActivity.this, "review", Toast.LENGTH_SHORT).show();
                replace(new FragmentReview());
                onclickSet(2);
                break;
            }
            case R.id.practice: {
             //   Toast.makeText(MainActivity.this, "practice", Toast.LENGTH_SHORT).show();
                replace(new FragmentPractice());
                onclickSet(3);
                break;
            }
            case R.id.self: {
             //   Toast.makeText(MainActivity.this, "self", Toast.LENGTH_SHORT).show();
                replace(new FragmentSelf());
                onclickSet(4);
                break;
            }
        }
    }

    //点击设置颜色，图片
    @SuppressLint("ResourceAsColor")
    public void onclickSet(int i) {
        home_image.setImageResource(R.drawable.main_shouye);
        review_image.setImageResource(R.drawable.main_fuxi);
        practice_image.setImageResource(R.drawable.main_lianxi);
        self_image.setImageResource(R.drawable.main_wode);

        home_textview.setTextColor(Color.parseColor("#2b2b2b"));
        review_textview.setTextColor(Color.parseColor("#2b2b2b"));
        practice_textview.setTextColor(Color.parseColor("#2b2b2b"));
        self_textview.setTextColor(Color.parseColor("#2b2b2b"));
        switch (i) {
            case 1: {
                home_image.setImageResource(R.drawable.main_shouye2);
                home_textview.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 2: {
                review_image.setImageResource(R.drawable.main_fuxi2);
                review_textview.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 3: {
                practice_image.setImageResource(R.drawable.main_lianxi2);
                practice_textview.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
            case 4: {
                self_image.setImageResource(R.drawable.main_wode2);
                self_textview.setTextColor(Color.parseColor("#5299f5"));
                break;
            }
        }

    }

    //锁片切换
    public void replace(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction replace = manager.beginTransaction().replace(R.id.main_framelayout, fragment);
        replace.commit();
    }

    //分享文字
    public void shareText(String text,int tag){
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction=String.valueOf(System.currentTimeMillis());
        req.message=msg;
        if(tag==0) {
            req.scene =SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene =SendMessageToWX.Req.WXSceneSession;
        }

        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    //分享图片
    public void shareImage(int res,int tag){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), res);
        WXWebpageObject webpageObject=new WXWebpageObject();
        WXMediaMessage msg=new WXMediaMessage(webpageObject);
        msg.mediaObject=new WXImageObject(bmp);

        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=String.valueOf(System.currentTimeMillis());
        req.message=msg;
        if(tag==0) {
            req.scene =SendMessageToWX.Req.WXSceneTimeline;
        }else {
            req.scene =SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    public void out(){
        User user = BmobUser.getCurrentUser(User.class);
        if(null == user){ //未登录
            finish();
        }
    }

}
