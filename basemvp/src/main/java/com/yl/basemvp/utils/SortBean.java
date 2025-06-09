package com.yl.basemvp.utils;

public class SortBean {
    private String imgUrl;
    private String name;
    private String pinyin;
    private String jianpin;
    private String word;//首字母
    private String telephone;
    private String phone;

    public SortBean(String name,String phone) {
        this.phone=phone;
        this.name = name;
        this.pinyin=PinYinStringHelper.getPingYin(name);//全拼
        this.word = PinYinStringHelper.getAlpha(name);//大写首字母或特殊字符
        this.jianpin=PinYinStringHelper.getPinYinHeadChar(name);//简拼
    }

    public String getJianpin() {
        return jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
