package com.example.asus.watermelonenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.asus.watermelonenglish.fragment.SelfShowFragment4;
/**
 * Created by asus on 2019/2/24.
 */

public class TestActivity extends AppCompatActivity {
    private WebView webview;
    private int tag=0;
    private String url="http://www.xsfanwen.com/Class/liujixiazai/liujixiazai01.html";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        tag=intent.getIntExtra("tag",0);
        webview=findViewById(R.id.webview);
        this.setTitle("English");
        switch (tag){
            case 0:{
                url="https://class.hujiang.com/category/12538435979?utm_campaign=%E5%9B%BD%E5%86%85%E8%80%83%E8%AF%95-%E9%9B%B6%E5%88%B0%E5%9B%9B%E5%85%AD&utm_adgroup=%E5%B8%A6%E5%8D%95%E8%AF%8D&utm_source=baidu_gnks&utm_medium=cpc&sem_source=sem_baidu_gnks&utm_adkeyword=%E8%8B%B1%E8%AF%AD%E7%BD%91%E7%AB%99%E5%AD%A6%E4%B9%A0";
                break;
            }
            case 1:{
                url="https://www.likeshuo.com/lp/Eight/Framework_g.html?utm_from=sem-shenma&tid=629-534-60&audience=354126";
                break;
            }
            case 2:{
                url="http://www.51talk.com/landing/bdpz1_024974.html?utm_source=baidu&utm_medium=search_cpc&utm_term=%E8%8B%B1%E8%AF%AD%E5%AD%A6%E4%B9%A0%E7%BD%91%E7%AB%99&utm_campaign=%E9%80%9A%E7%94%A8%E8%AF%8D-%E8%8B%B1%E8%AF%AD&utm_adgroup=%E8%8B%B1%E8%AF%AD-%E7%BD%91%E7%AB%99&utm_account=huangjj03&utm_channel=huangjj03_dabao&utm_content=search_cpc&bd_vid=8590894740977346447#B_vid=8590894740977346447";
                break;
            }
            case 3:{
                url="http://www.kekenet.com/";
               break;
            }
            case 4:{
                url="https://www.koolearn.com/?a_id=ff8080811b686e11011b686e11d40000&kid=261afab949ae4d02838e3c200c37e96b&utm_source=baidu&utm_medium=pcdt&utm_campaign=%E8%A1%8C%E4%B8%9A%E5%AE%9A%E6%8A%95%E6%B5%8B%E8%AF%95-%E6%96%B0%E5%A2%9E&utm_content=%E9%98%BF%E5%8D%A1%E7%B4%A2&utm_term=www.acadsoc.com.cn&pt_k=101705171714&pt_c=27654227440&pt_m=1&pt_a=bdtg_1235910&pt_d=pc&ctx=&basePath=http%3A%2F%2Fun.koolearn.com%3A80%2F";
                break;
            }
            default:
                break;
        }
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
    }

}
