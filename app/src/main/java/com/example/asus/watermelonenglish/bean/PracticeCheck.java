package com.example.asus.watermelonenglish.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2019/3/11.
 */

public class PracticeCheck extends BmobObject {
    private String practiceId;
    private String practiceText;

    public String getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(String practiceId) {
        this.practiceId = practiceId;
    }

    public String getPracticeText() {
        return practiceText;
    }

    public void setPracticeText(String practiceText) {
        this.practiceText = practiceText;
    }
}
