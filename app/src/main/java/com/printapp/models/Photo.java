package com.printapp.models;

import java.io.Serializable;

public class Photo implements Serializable{

    public double id;
    public double owner_id;
    public String photo_75;
    public String photo_130;
    public String photo_604;
    public int count;

    public Photo() {
        this.id = 1;
        this.id = 3;
        this.photo_75 = "https://pp.vk.me/c633422/v633422464/1da51/BIcvxr2grD0.jpg";
        this.photo_130 = "https://pp.vk.me/c633422/v633422464/1da51/BIcvxr2grD0.jpg";
        this.photo_604 = "https://pp.vk.me/c633422/v633422464/1da51/BIcvxr2grD0.jpg";
        this.count = 0;
    }
}