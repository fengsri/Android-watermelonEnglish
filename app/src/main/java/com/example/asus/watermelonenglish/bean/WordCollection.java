package com.example.asus.watermelonenglish.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by asus on 2019/2/27.
 */

public class WordCollection  extends BmobUser {
    private  String wordId;
    private String userId;
    private String wordCollectionDate;

    public String getWordCollectionDate() {
        return wordCollectionDate;
    }

    public void setWordCollectionDate(String wordCollectionDate) {
        this.wordCollectionDate = wordCollectionDate;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
