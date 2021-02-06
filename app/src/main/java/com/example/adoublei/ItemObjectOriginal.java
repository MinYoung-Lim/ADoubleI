package com.example.adoublei;

public class ItemObjectOriginal {
    private String title;
    private String Photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo){

        this.Photo=photo;
    }

    public ItemObjectOriginal(String title, String photo) {
        this.title = title;
        this.Photo=photo;
    }
    public ItemObjectOriginal() {}


}
