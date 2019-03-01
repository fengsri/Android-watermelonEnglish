package com.example.asus.watermelonenglish.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.watermelonenglish.MainActivity;
import com.example.asus.watermelonenglish.myclass.EnglishWord;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2019/2/23.
 */

public class SaveWord {
    public static void  saveEnglistword(final Context context){
        List<EnglishWord> wordList=getEnglishWordList(context);
        saveEnglishWords(wordList);
    }

    private static List<EnglishWord> getEnglishWordList(final Context context) {
        List<EnglishWord> list=new ArrayList<>();

        try {
            String data=getFromAssets("cet4.xml",context);
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(data));
            int evetType=xmlPullParser.getEventType();
            String word="";
            String trans="";
            String phonetic="";
            String tags="";

            while (evetType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (evetType){
                    //开始解析
                    case XmlPullParser.START_TAG:{
                        if("word".equals(nodeName)){
                            word=xmlPullParser.nextText();
                        }else if("trans".equals(nodeName)){
                            trans=xmlPullParser.nextText();
                        }else if("phonetic".equals(nodeName)){
                            phonetic=xmlPullParser.nextText();
                        }else if("tags".equals(nodeName)){
                            tags=xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if("item".equals(nodeName)){
                            list.add(new EnglishWord(word,trans,phonetic,tags));
                            Log.e("word:",word+" "+trans+"  "+phonetic+"  "+tags);
                        }
                        break;
                    }
                    default:
                        break;
                }
                Toast.makeText(context,word,Toast.LENGTH_SHORT).show();
                break;
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String getFromAssets(String fileName, Context context) {
        String line = "";
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName),"gb2312");
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    private static void saveEnglishWords(List<EnglishWord> wordList) {
        for(int i=0;i<wordList.size();i++){
            wordList.get(i).save();
        }
    }

}
