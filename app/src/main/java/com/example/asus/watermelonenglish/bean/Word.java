package com.example.asus.watermelonenglish.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/25.
 */

public class Word extends BmobObject implements Serializable{
    private String wordText;
    private String wordTypeId;
    private String wordId;

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getWordTypeId() {
        return wordTypeId;
    }

    public void setWordTypeId(String wordTypeId) {
        this.wordTypeId = wordTypeId;
    }
}
