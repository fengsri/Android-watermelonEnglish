package com.example.asus.watermelonenglish;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.asus.watermelonenglish.bean.User;
import com.example.asus.watermelonenglish.bean.WordCollection;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.name;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by asus on 2019/2/9.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Tencent mTencent;
    String token;
    String expires_in;
    String uniqueCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        mTencent = Tencent.createInstance("101552311", this.getApplicationContext());
        init();
    }

    private void init() {
        findViewById(R.id.login_weixin).setOnClickListener(this);
        findViewById(R.id.login_qq).setOnClickListener(this);
        findViewById(R.id.login_zh).setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());

        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示！");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_qq:{
                //QQ登录
//                if (!mTencent.isSessionValid())
//                {
                    mTencent.login(this, "all",loginListener );
                //}
                break;
            }
            case R.id.login_weixin:{

                Toast.makeText(LoginActivity.this,"不能登录",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.login_zh:{
                this.startActivity(new Intent(this,LoginUser.class));
                finish();
                break;
            }
        }
    }

    public IUiListener loginListener = new BaseUiListener(){
        @Override
        public void onComplete(Object arg0) {
            // TODO Auto-generated method stub
            super.onComplete(arg0);
          //  Toast.makeText(LoginActivity.this,arg0.toString(),Toast.LENGTH_SHORT).show();
            uniqueCode = ((JSONObject) arg0).optString("openid"); //QQ的openid
            try {
                token = ((JSONObject) arg0).getString("access_token");
                expires_in = ((JSONObject) arg0).getString("expires_in");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //获取QQ返回的用户信息（里面的参数直接照搬，别乱改，上面也是）
            QQToken qqtoken = mTencent.getQQToken();
            mTencent.setOpenId(uniqueCode);
            mTencent.setAccessToken(token, expires_in);
            UserInfo info = new UserInfo(getApplicationContext(), qqtoken);
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    String nickname = ((JSONObject) o).optString("nickname");
                    String sexStr = ((JSONObject) o).optString("sex");
                    String headImg = ((JSONObject) o).optString("figureurl_qq_2");
                  //  Toast.makeText(LoginActivity.this,"nickname:"+nickname+ "  sexStr:"+ sexStr+"  headImg:"+headImg,Toast.LENGTH_SHORT).show();
                    getsize(nickname,sexStr,headImg);
                }
                @Override
                public void onError(UiError uiError) {
                }
                @Override
                public void onCancel() {
                }
            });
        }
    };

    public void getsize(final String nickname, String sexStr, final String headImg){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        dialog.setTitle("");
        dialog.show();

        BmobUser.loginByAccount(nickname, nickname, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                } else {
                    Intent intent=new Intent(LoginActivity.this,RegisteActivity2.class);
                    intent.putExtra("name",nickname );
                    intent.putExtra("password",nickname );
                    intent.putExtra("headerPic",headImg );
                    LoginActivity.this.startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            }
        });

    }

    /**
     * 调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
     * */
    public class BaseUiListener implements IUiListener {

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            Toast.makeText(LoginActivity.this, "取消登陆", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            if (null == response) {
                Toast.makeText(LoginActivity.this, "返回为空,登录失败", Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(LoginActivity.this, "返回为空,登录失败", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(LoginActivity.this, "登录失败"+arg0, Toast.LENGTH_LONG).show();
        }
    }

}
