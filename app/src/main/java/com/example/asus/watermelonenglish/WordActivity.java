package com.example.asus.watermelonenglish;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.Word;
import com.example.asus.watermelonenglish.bean.WordCollection;
import com.example.asus.watermelonenglish.myclass.AudioMgr;
import com.example.asus.watermelonenglish.util.UserUtil;
import com.example.asus.watermelonenglish.util.WordUtil;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;
import com.youdao.sdk.ydtranslate.WebExplain;

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
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus on 2019/2/17.
 */

public class WordActivity extends AppCompatActivity implements View.OnClickListener{
    private int Tag;
    private ImageView scImage;//收藏
    private TextView scText;//收藏
    private int tt=0;

    private ImageView fayingImage; //发音
    private TextView fayingText; //发音
    private CollapsingToolbarLayout collapsingToolbar;  //头部文字
    private ImageView imageView; //头部图片

    private TextView wordTextview; //当前翻译word
    private TextView translateText1;//翻译1
    private TextView translateText2;//翻译2
    private TextView exmpleText;//扩展


    private FloatingActionButton floatButton; //斩

    private Button shang;//上一个
    private TextView word_count;
    private Button xia;//下

    private String from="中文";
    private String to="英文";
    private Translate translateWord;//获取的翻译
    private List<Word> list=new ArrayList<Word>();
    private String wordText="";
    private Word word;

    private User user;
    int todayCount=0;
    int cCount=1;
    int wordTypeId=1;

    String wordCollectionId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        user = BmobUser.getCurrentUser(User.class);
        //初始化
        init();
    }
    private void init() {
        Toolbar toolbar=findViewById(R.id.fuxi_word_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("");
        //头部
        collapsingToolbar=findViewById(R.id.collapsing_toolbar);
        imageView=findViewById(R.id.fuxi_word_image);

        //收藏
        scImage=findViewById(R.id.word_header_scimage);
        scImage.setOnClickListener(this);
        scText=findViewById(R.id.word_header_sctext);
        scText.setOnClickListener(this);

        //发音
        fayingImage=findViewById(R.id.word_header_fyimage);
        fayingImage.setOnClickListener(this);
        fayingText=findViewById(R.id.word_header_fytext);
        fayingText.setOnClickListener(this);

        //翻译
        wordTextview=findViewById(R.id.fuxi_word_textview); //翻译的word
        translateText1=findViewById(R.id.fuxi_word_textview2);
        translateText2=findViewById(R.id.fuxi_word_textview3);
        exmpleText=findViewById(R.id.fuxi_word_textview5); //扩展

        //斩
        floatButton=findViewById(R.id.fuxi_word_float); //斩
        floatButton.setOnClickListener(this);

        shang=findViewById(R.id.word_shang);
        shang.setOnClickListener(this);
        xia=findViewById(R.id.word_xia);
        xia.setOnClickListener(this);
        word_count=findViewById(R.id.word_count);

        wordTypeId = UserUtil.getWordType(user);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        Tag=intent.getIntExtra("tag",0);
        if(Tag==0){  //当前由搜索跳转
            int dto=intent.getIntExtra("dto",1);
            wordText=intent.getStringExtra("input");
            shang.setVisibility(View.INVISIBLE);
            xia.setVisibility(View.INVISIBLE);
            word_count.setVisibility(View.INVISIBLE);
            if(dto==1){
                from="中文";
                to="英文";
            }else{
                from="英文";
                to="中文";
            }
        }else{  //当前由复习跳转
            int sway = intent.getIntExtra("sway",1);
            todayCount=intent.getIntExtra("todayCount",0);
            word_count.setText(cCount+"/"+todayCount);
            getWordData(sway);
        }
        getTranslate(wordText);
    }


    public void getWordData(final int sway){
        BmobQuery<Word> query1 = new BmobQuery<>();
        query1.setLimit(4000).findObjects(new FindListener<Word>() {
            @Override
            public void done(List<Word> list1, BmobException e) {
               // Toast.makeText(WordActivity.this,"查询成功:"+list1.size(),Toast.LENGTH_SHORT).show();
                if(sway ==1){
                        list = WordUtil.getTodayList(list1,(UserUtil.longOfTwoDate(user))*todayCount,(UserUtil.longOfTwoDate(user)+1)*todayCount);
                      //  Toast.makeText(WordActivity.this,"s  +  e:"+(UserUtil.longOfTwoDate(user))*todayCount+"  ,   "+(UserUtil.longOfTwoDate(user)+1)*todayCount,Toast.LENGTH_SHORT).show();
                }else if(sway==2){
                        list = WordUtil.getTodayList(list1,(UserUtil.longOfTwoDate(user))*todayCount,(UserUtil.longOfTwoDate(user)+1)*todayCount);
                      //  Toast.makeText(WordActivity.this,"s  +  e:"+(UserUtil.longOfTwoDate(user))*todayCount+"  ,   "+(UserUtil.longOfTwoDate(user)+1)*todayCount,Toast.LENGTH_SHORT).show();
                }else if(sway==3){
                        list = WordUtil.getGaopingList(list1);
                }else if(sway==4){
                        list = WordUtil.getSuijiList(list1);
                }
              //  Toast.makeText(WordActivity.this,"查询成功:"+list.size(),Toast.LENGTH_SHORT).show();
                if(list!=null && list.size()>0){
                    wordText=list.get(0).getWordText();
                    word = list.get(0);
                    getTranslate(wordText);
                }else{
                    Log.i("smile", "查询成功，无数据返回");
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return true;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.word_header_scimage:{
                if(tt==0) {
                    setSCColor(++tt);
                    shoucang(word.getWordTypeId());
                    Toast.makeText(WordActivity.this,"收藏",Toast.LENGTH_SHORT).show();
                }
                else {
                    setSCColor(--tt);
                    canshoucang();
                    Toast.makeText(WordActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.word_header_sctext:{
                if(tt==0) {
                    setSCColor(++tt);
                    Toast.makeText(WordActivity.this,"收藏",Toast.LENGTH_SHORT).show();
                }
                else {
                    setSCColor(--tt);
                    Toast.makeText(WordActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.word_header_fyimage:{
                ConnectivityManager connectivityManager;//用于判断是否有网络
                connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);//获取当前网络的连接服务
                NetworkInfo info = connectivityManager.getActiveNetworkInfo(); //获取活动的网络连接信息
                if (info != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playVoice(translateWord.getSpeakUrl());
                            Toast.makeText(WordActivity.this, "正在发音中", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(WordActivity.this, "当前网络无法用", Toast.LENGTH_SHORT).show();
                }
                setFaYingColor();
                break;
            }
            case R.id.word_header_fytext:{
                ConnectivityManager connectivityManager;//用于判断是否有网络
                connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);//获取当前网络的连接服务
                NetworkInfo info = connectivityManager.getActiveNetworkInfo(); //获取活动的网络连接信息
                if (info != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playVoice(translateWord.getSpeakUrl());
                            Toast.makeText(WordActivity.this, "正在发音中", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(WordActivity.this, "当前网络无法用", Toast.LENGTH_SHORT).show();
                }
                setFaYingColor();
                break;
            }
            case R.id.fuxi_word_float:{
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(floatButton, "rotation", 0, 360).setDuration(500);
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        floatButton.setImageResource(R.drawable.word_zan2);
                        shoucang(word.getWordTypeId());
                    }
                });
                objectAnimator.start();
                break;
            }
            case R.id.word_xia:{
                if(cCount<todayCount && list!=null && list.size()>0) {
                    cCount++;
                    wordText = list.get(cCount-1).getWordText();
                    word = list.get(cCount);
                    floatButton.setImageResource(R.drawable.word_zan);
                    getTranslate(wordText);
                    word_count.setText(cCount+"/"+todayCount);
                    tt=0;
                    setSCColor(tt);
                }
                break;
            }
            case R.id.word_shang:{
                if(cCount>1 && list!=null && list.size()>0) {
                    cCount--;
                    wordText = list.get(cCount-1).getWordText();
                    word = list.get(cCount);
                    floatButton.setImageResource(R.drawable.word_zan);
                    getTranslate(wordText);
                    word_count.setText(cCount+"/"+todayCount);
                    tt=0;
                    setSCColor(tt);
                }
                break;
            }
        }
    }

    private void intiData() {
        final int images[]={R.drawable.word_header_bg1,R.drawable.word_header_bg2,R.drawable.word_header_bg3,R.drawable.word_header_bg4,R.drawable.word_header_bg5,R.drawable.word_header_bg6,R.drawable.word_header_bg7,R.drawable.word_header_bg8,R.drawable.word_header_bg9};
        int rand=(int)(Math.random()*images.length);
        Glide.with(this).load(images[rand]).into(imageView);
        collapsingToolbar.setTitle(wordText);
        wordTextview.setText(wordText);
        translateText1.setText(listStr(translateWord.getExplains())+"");
        exmpleText.setText(webMeans(translateWord));
    }

    public void shoucang(String wordId){
        String userId=user.getObjectId();
        WordCollection p2 = new  WordCollection();
        p2.setUserId(userId);
        p2.setWordId(wordId);
        p2.setWordCollectionDate(UserUtil.getTodayDate());
        if(userId!=null){
            p2.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        wordCollectionId = objectId;
                        Toast.makeText(WordActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(WordActivity.this,"已经收藏",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void canshoucang(){
        WordCollection p2 = new  WordCollection();
        p2.setObjectId(wordCollectionId);
        p2.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                  //  Toast.makeText(WordActivity.this,"",Toast.LENGTH_SHORT).show();
                }else{
                   // toast("删除失败：" + e.getMessage());
                }
            }
        });
    }

    //进行翻译
    private void getTranslate(String input) {
        //注册应用ID ，建议在应用启动时，初始化，所有功能的使用都需要该初始化，调用一次即可，demo中在TranslateActivity类中
        YouDaoApplication.init(this, "6c8f597c03a9e45d");

        //查词对象初始化，请设置source参数为app对应的名称（英文字符串）
        Language langFrom = LanguageUtils.getLangByName(from);
        //若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
        //Language langFrom = LanguageUtils.getLangByName("自动");
        Language langTo = LanguageUtils.getLangByName(to);

        TranslateParameters tps = new TranslateParameters.Builder()
                .source("ydtranslate-demo")
                .from(langFrom).to(langTo).build();

        Translator translator = Translator.getInstance(tps);

        //查询，返回两种情况，一种是成功，相关结果存储在result参数中，另外一种是失败，失败信息放在TranslateErrorCode中，TranslateErrorCode是一个枚举类，整个查询是异步的，为了简化操作，回调都是在主线程发生。

        translator.lookup(input, "requestId", new TranslateListener() {

            @Override
            public void onError(TranslateErrorCode translateErrorCode, String s) {

            }
            @Override
            public void onResult(final Translate translate, final String s, final String s1) {
                translateWord=translate;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intiData();
                    }
                });
            }
            @Override
            public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

            }
        });
    }
    //发音
    public synchronized void playVoice(String speakUrl) {
        YouDaoLog.e(AudioMgr.PLAY_LOG + "TranslateDetailActivity click to playVoice speakUrl = " + speakUrl);
        if (!TextUtils.isEmpty(speakUrl) && speakUrl.startsWith("http")) {
            AudioMgr.startPlayVoice(speakUrl, new AudioMgr.SuccessListener() {
                @Override
                public void success() {
                    YouDaoLog.e(AudioMgr.PLAY_LOG + "TranslateDetailActivity playVoice success");
                }
                @Override
                public void playover() {
                    YouDaoLog.e(AudioMgr.PLAY_LOG + "TranslateDetailActivity playover");
                }
            });
        }
    }

    //设置发音时的颜色
    public void setFaYingColor(){
        fayingText.setTextColor(Color.parseColor("#5299f5"));
        fayingImage.setImageResource(R.drawable.word_header_fy);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fayingText.setTextColor(Color.parseColor("#2b2b2b"));
                fayingImage.setImageResource(R.drawable.word_header_fy2);
            }
        },2000);
    }
    //设置收藏时的颜色
    public void setSCColor(int t){
        if(t==1) {
            scText.setTextColor(Color.parseColor("#5299f5"));
            scImage.setImageResource(R.drawable.review_shoucang2);
        }else{
            scText.setTextColor(Color.parseColor("#2b2b2b"));
            scImage.setImageResource(R.drawable.review_shoucang);
        }
    }
    //解析翻译
    private String listStr(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (String s : list) {
                sb.append(s).append("\n");
            }
        }
        return sb.toString();
    }
    public String webMeans(Translate translate) {
        StringBuilder sb = new StringBuilder();
        List<WebExplain> explains = translate.getWebExplains();
        if (explains != null) {
            for (WebExplain s : explains) {
                sb.append(s.getKey()).append(":").append(listStr(s.getMeans())).append("\n");
            }
        }
        return sb.toString();
    }
}

