package com.example.asus.watermelonenglish.util;

import com.example.asus.watermelonenglish.bean.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2019/3/27.
 */

public class WordUtil {
    public static List<Word> getSuijiList(List<Word> wordList){
        List<Word> words = new ArrayList<>();
        for(int i=0;i<50;i++){
            int rand = (int)((Math.random())*wordList.size());
            words.add(wordList.get(rand));
        }
        return words;
    }
    public static List<Word> getGaopingList(List<Word> wordList){
        List<Word> words = new ArrayList<>();
        for(int i=0;i<200;i++){
            int rand = (int)(Math.random()*wordList.size());
            words.add(wordList.get(rand));
        }
        return words;
    }

    public static List<Word> getTodayList(List<Word> wordList,int start,int end){
        List<Word> words = new ArrayList<>();
        if(end>wordList.size()){
            for (int i =(wordList.size()+start-end); i < wordList.size(); i++) {
                words.add(wordList.get(i));
            }
        }else {
            for (int i = start; i < end; i++) {
                words.add(wordList.get(i));
            }
        }
        return words;
    }

    public static List<Word> getFuxiList(List<Word> wordList,int start,int end){
        List<Word> words = new ArrayList<>();
        if(end>wordList.size()){
            for (int i =(wordList.size()+start-end); i < wordList.size(); i++) {
                words.add(wordList.get(i));
            }
        }else {
            for (int i = start; i < end; i++) {
                words.add(wordList.get(i));
            }
        }
        return words;
    }
}
