package com.example.asus.watermelonenglish.bean;

import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by asus on 2019/2/24.
 */

public class User extends BmobUser {
    private String userUseDate;
    private String userSignature;
    private String userHeaderPic;
    private String userSchool;
    private String userLevel;

    private BmobRelation collectionWord;

    public BmobRelation getCollectionWord() {
        return collectionWord;
    }

    public void setCollectionWord(BmobRelation collectionWord) {
        this.collectionWord = collectionWord;
    }

    public String getUserUseDate() {
        return userUseDate;
    }

    public void setUserUseDate(String userUseDate) {
        this.userUseDate = userUseDate;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserHeaderPic() {
        return userHeaderPic;
    }

    public void setUserHeaderPic(String userHeaderPic) {
        this.userHeaderPic = userHeaderPic;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUseDate=" + userUseDate +
                ", userSignature='" + userSignature + '\'' +
                ", userHeaderPic='" + userHeaderPic + '\'' +
                ", userSchool='" + userSchool + '\'' +
                ", userLevel='" + userLevel + '\'' +
                '}';
    }
}
