package com.example.asus.watermelonenglish.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/25.
 */

public class WordType extends BmobObject {
    private String wordTypeName;
    private String wordId;

    public String getWordTypeName() {
        return wordTypeName;
    }

    public void setWordTypeName(String wordTypeName) {
        this.wordTypeName = wordTypeName;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }
}
