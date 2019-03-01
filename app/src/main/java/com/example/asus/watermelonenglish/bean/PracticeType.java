package com.example.asus.watermelonenglish.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/25.
 */

public class PracticeType extends BmobUser {
    private String practiceTypeId;
    private String practiceTypeName;

    public String getPracticeTypeId() {
        return practiceTypeId;
    }

    public void setPracticeTypeId(String practiceTypeId) {
        this.practiceTypeId = practiceTypeId;
    }

    public String getPracticeTypeName() {
        return practiceTypeName;
    }

    public void setPracticeTypeName(String practiceTypeName) {
        this.practiceTypeName = practiceTypeName;
    }
}
