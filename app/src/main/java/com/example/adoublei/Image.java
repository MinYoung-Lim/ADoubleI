package com.example.adoublei;

public class Image {
    private String imageClassify;
    private int imageResourceID;

    public int getImageResourceID(){
        return imageResourceID;
    }
    public String getImageClassify(){
        return imageClassify;
    }
    public void setImageResourceID(int imageResourceID){
        this.imageResourceID=imageResourceID;
    }
    public void setImageClassify(String imageClassify){
        this.imageClassify=imageClassify;
    }

}
