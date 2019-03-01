package com.example.asus.watermelonenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.watermelonenglish.fragment.SelfShowFragment1;
import com.example.asus.watermelonenglish.fragment.SelfShowFragment2;
import com.example.asus.watermelonenglish.fragment.SelfShowFragment3;
import com.example.asus.watermelonenglish.fragment.SelfShowFragment4;

/**
 * Created by asus on 2019/2/17.
 */

public class SelfShowActivity extends AppCompatActivity {
    private int key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_show);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //初始化数据
        initdata();
        //初始化view
        initview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.self_menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.self_fragment_shezhi:{
                replace(new SelfShowFragment4());
                this.setTitle("设置");
                break;
            }
            case android.R.id.home:{
             //   Toast.makeText(SelfShowActivity.this,"aaa",Toast.LENGTH_SHORT).show();
                finish();
                break;
            }
        }
        return true;
    }

    private void initview() {
        switch (key){
            case 0:{
                replace(new SelfShowFragment1());
                this.setTitle("基本信息");
                break;
            }
            case 1:{
                replace(new SelfShowFragment2());
                this.setTitle("所选方向");
                break;
            }
            case 2:{
                replace(new SelfShowFragment3());
                this.setTitle("我的排名");
                break;
            }
            case 3:{
                replace(new SelfShowFragment4());
                this.setTitle("设置");
                break;
            }
        }
    }

    private void initdata() {
        Intent intent=this.getIntent();
        Bundle bundle=intent.getBundleExtra("data");
        key=(int)bundle.get("key");
    }
    //锁片切换
    private void replace(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction replace = manager.beginTransaction().replace(R.id.self_show_fragment, fragment);
        replace.commit();
    }
    public void out(){
        finish();
    }

}
