package com.example.asus.watermelonenglish.myclass;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.asus.watermelonenglish.bean.Practice;

/**
 * Created by asus on 2019/2/15.
 */

public class Pract {
    private int imageid;
    private Practice practice;

    public Pract(int imageid, Practice practice) {
        this.imageid = imageid;
        this.practice = practice;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }
}
