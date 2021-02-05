package com.example.adoublei;

import android.net.Uri;

public class ItemObject {

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

        public ItemObject(String title, String photo) {
        this.title = title;
        this.Photo=photo;
        }
        public ItemObject() {}

    }



