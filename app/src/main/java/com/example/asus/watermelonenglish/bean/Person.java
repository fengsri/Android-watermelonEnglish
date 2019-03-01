package com.example.asus.watermelonenglish.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.b.name;

/**
 * Created by asus on 2019/2/24.
 */

public class Person extends BmobObject {
    private String name;
    private String email;
    private String school;
    private String level;
    private String headerPic;
    private Date firstDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHeaderPic() {
        return headerPic;
    }

    public void setHeaderPic(String headerPic) {
        this.headerPic = headerPic;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }
}
