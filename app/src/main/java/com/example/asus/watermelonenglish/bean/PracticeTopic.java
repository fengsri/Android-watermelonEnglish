package com.example.asus.watermelonenglish.bean;

import java.lang.reflect.Array;
import java.util.Arrays;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/25.
 */

public class PracticeTopic  extends BmobObject {

    private String right;
    private String practiceTopicId;
    private String practiceId;

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getPracticeTopicId() {
        return practiceTopicId;
    }

    public void setPracticeTopicId(String practiceTopicId) {
        this.practiceTopicId = practiceTopicId;
    }

    public String getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(String practiceId) {
        this.practiceId = practiceId;
    }

    @Override
    public String toString() {
        return "PracticeTopic{" +
                ", right='" + right + '\'' +
                ", practiceTopicId='" + practiceTopicId + '\'' +
                ", practiceId='" + practiceId + '\'' +
                '}';
    }
}
