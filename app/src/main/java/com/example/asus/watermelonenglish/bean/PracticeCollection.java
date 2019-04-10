package com.example.asus.watermelonenglish.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/3/10.
 */

public class PracticeCollection extends BmobObject {
    private String practiceId;
    private String userId;
    private String practiceDate;

    public String getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(String practiceId) {
        this.practiceId = practiceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) {
        this.practiceDate = practiceDate;
    }
}
