package com.example.asus.watermelonenglish.myclass;

import org.litepal.crud.DataSupport;

/**
 * Created by asus on 2019/2/23.
 */

public class EnglishWord extends DataSupport{
    private String text;
    private String trans;
    private String pronunce;
    private String type;

    public EnglishWord(String text, String trans, String pronunce, String type) {
        this.text = text;
        this.trans = trans;
        this.pronunce = pronunce;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getPronunce() {
        return pronunce;
    }

    public void setPronunce(String pronunce) {
        this.pronunce = pronunce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EnglishWord{" +
                "text='" + text + '\'' +
                ", trans='" + trans + '\'' +
                ", pronunce='" + pronunce + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
