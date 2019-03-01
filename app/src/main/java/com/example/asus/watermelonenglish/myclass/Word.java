package com.example.asus.watermelonenglish.myclass;

/**
 * Created by asus on 2019/2/19.
 */

public class Word {
    private int wordId;
    private String wordTypeId;
    private String wordText;
    private String wordTranslate;
    private int wordPic;
    private String wordPronunciation;
    private String wordExample1;
    private String wordExample2;
    private String wordExample3;

    public Word(int wordId, String wordTypeId, String wordText, String wordTranslate, int wordPic, String wordPronunciation, String wordExample1, String wordExample2, String wordExample3) {
        this.wordId = wordId;
        this.wordTypeId = wordTypeId;
        this.wordText = wordText;
        this.wordTranslate = wordTranslate;
        this.wordPic = wordPic;
        this.wordPronunciation = wordPronunciation;
        this.wordExample1 = wordExample1;
        this.wordExample2 = wordExample2;
        this.wordExample3 = wordExample3;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWordTypeId() {
        return wordTypeId;
    }

    public void setWordTypeId(String wordTypeId) {
        this.wordTypeId = wordTypeId;
    }

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public String getWordTranslate() {
        return wordTranslate;
    }

    public void setWordTranslate(String wordTranslate) {
        this.wordTranslate = wordTranslate;
    }

    public int getWordPic() {
        return wordPic;
    }

    public void setWordPic(int wordPic) {
        this.wordPic = wordPic;
    }

    public String getWordPronunciation() {
        return wordPronunciation;
    }

    public void setWordPronunciation(String wordPronunciation) {
        this.wordPronunciation = wordPronunciation;
    }

    public String getWordExample1() {
        return wordExample1;
    }

    public void setWordExample1(String wordExample1) {
        this.wordExample1 = wordExample1;
    }

    public String getWordExample2() {
        return wordExample2;
    }

    public void setWordExample2(String wordExample2) {
        this.wordExample2 = wordExample2;
    }

    public String getWordExample3() {
        return wordExample3;
    }

    public void setWordExample3(String wordExample3) {
        this.wordExample3 = wordExample3;
    }
}
