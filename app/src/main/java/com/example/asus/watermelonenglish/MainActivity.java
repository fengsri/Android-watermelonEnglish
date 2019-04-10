package com.example.asus.watermelonenglish;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.asus.watermelonenglish.bean.Word;
import com.example.asus.watermelonenglish.fragment.FragmentHome;
import com.example.asus.watermelonenglish.fragment.FragmentPractice;
import com.example.asus.watermelonenglish.fragment.FragmentReview;
import com.example.asus.watermelonenglish.fragment.FragmentSelf;
import com.example.asus.watermelonenglish.myclass.AudioMgr;
import com.example.asus.watermelonenglish.myclass.EnglishWord;
import com.example.asus.watermelonenglish.util.SaveWord;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
    private Tencent mTencent;
    private BaseUiListener mIUiListener=new BaseUiListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //初始化
        mTencent = Tencent.createInstance("101552311", this.getApplicationContext());
        init();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("你要退出软件吗？");
        builder.setIcon(R.mipmap.ic_launcher_round);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //初始化数据
    @Override
    protected void onStart() {
        super.onStart();
        LitePal.getDatabase();
        List<EnglishWord> list = DataSupport.findAll(EnglishWord.class);
        int wordCount = list.size();
        if (wordCount == 0) {
            // SaveWord.saveEnglistword(this);
        } else {
            Toast.makeText(this, "wordSize:" + wordCount, Toast.LENGTH_SHORT).show();
            //  Toast.makeText(this,list.get(4).toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void init() {
        //第一：默认初始化
        api = WXAPIFactory.createWXAPI(this, "wxf371098a435d7f2b");
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

        Intent intent = getIntent();
        if (intent.getIntExtra("ato", 0) == 1) {
            replace(new FragmentSelf());
            onclickSet(4);
        } else {
            replace(new FragmentHome());
            onclickSet(1);
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, mIUiListener);
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
    public void shareText(String text, int tag) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (tag == 0) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }

        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    //分享图片
    public void shareImage(int res, int tag) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), res);
        WXWebpageObject webpageObject = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.mediaObject = new WXImageObject(bmp);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (tag == 0) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        api.sendReq(req);
    }

    public void shareToQQ(String text) {
//        final Bundle params;
//        params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, "打卡");// 标题
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);// 摘要
//        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,"http://www.qq.com/news/1.html");// 内容地址
//        // 分享操作要在主线程中完成
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
//            }
//        });

        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "纯文字分享");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.tencent.mobileqq","com.tencent.mobileqq.activity.JumpActivity"));
        startActivity(intent);
    }

    public void shareToQZone(String text) {
        final Bundle params;
        params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "打卡");// 标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, text);// 摘
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,"http://www.qq.com/news/1.html");// 内容地址
        // 分享操作要在主线程中完成
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
            }
        });
    }

    /**
     * 调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
     */
    public class BaseUiListener implements IUiListener {
        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            //Toast.makeText(LoginActivity.this, "取消登陆", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
         //   Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub
            //Toast.makeText(LoginActivity.this, "登录失败"+arg0, Toast.LENGTH_LONG).show();
        }
    }
}
