package com.example.asus.watermelonenglish.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/25.
 */

public class Word extends BmobUser {
    private String wordText;
    private String wordTypeId;

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public String getWordTypeId() {
        return wordTypeId;
    }

    public void setWordTypeId(String wordTypeId) {
        this.wordTypeId = wordTypeId;
    }
}
