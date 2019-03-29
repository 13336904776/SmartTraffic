package com.example.mrzhang.smarttraffic.bean;

import java.io.Serializable;

public class CarViolaBean implements Serializable {
    private int imgId;
    private String name;
    private String path;

    public CarViolaBean() {
    }

    public CarViolaBean(int imgId, String name, String path) {
        this.imgId = imgId;
        this.name = name;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CarViolaBean{" +
                "imgId=" + imgId +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
