package com.example.administrator.catvideo;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2019/4/27.
 */

public class Data {
    private String cat;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Data(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    private String star;
    private String dir;
    private String img;
    private String nm;
    private String pubDesc;
    private String videoName;
    private String videourl;
    private int id;

    public Data(String cat, String nm) {
        this.cat = cat;
        this.nm = nm;
    }

    public Data(String cat, String star, String dir, String img, String nm, String pubDesc, String videoName, String videourl) {
        this.cat = cat;
        this.star = star;
        this.dir = dir;
        this.img = img;
        this.nm = nm;
        this.pubDesc = pubDesc;
        this.videoName = videoName;
        this.videourl = videourl;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getPubDesc() {
        return pubDesc;
    }

    public void setPubDesc(String pubDesc) {
        this.pubDesc = pubDesc;
    }


    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
