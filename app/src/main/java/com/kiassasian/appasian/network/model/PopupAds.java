package com.kiassasian.appasian.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopupAds {


    @SerializedName("popup_enable")
    @Expose
    private  String enable;

    @SerializedName("popup_title")
    @Expose
    private  String title;
    @SerializedName("popup_desc")
    @Expose
    private  String desc;
    @SerializedName("popup_img_url")
    @Expose
    private  String imgurl;
    @SerializedName("popup_apk")
    @Expose
    private  String apk;


    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }
}
