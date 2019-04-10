package com.example.asus.watermelonenglish.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/25.
 */

public class Practice extends BmobObject {
    private String practiceTitle;
    private String practiceTypeId;
    private String practiceText;
    private String practiceId;

    public String getPracticeTitle() {
        return practiceTitle;
    }

    public void setPracticeTitle(String practiceTitle) {
        this.practiceTitle = practiceTitle;
    }

    public String getPracticeTypeId() {
        return practiceTypeId;
    }

    public void setPracticeTypeId(String practiceTypeId) {
        this.practiceTypeId = practiceTypeId;
    }

    public String getPracticeText() {
        return practiceText;
    }

    public void setPracticeText(String practiceText) {
        this.practiceText = practiceText;
    }

    public String getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(String practiceId) {
        this.practiceId = practiceId;
    }

    @Override
    public String toString() {
        return "Practice{" +
                "practiceTitle='" + practiceTitle + '\'' +
                ", practiceTypeId='" + practiceTypeId + '\'' +
                ", practiceText='" + practiceText + '\'' +
                ", practiceId='" + practiceId + '\'' +
                '}';
    }
}
