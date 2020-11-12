package com.example.adoublei;

import android.net.Uri;

public class ItemObject {

        private String title;
        private Uri Photo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Uri getPhoto() {
            return Photo;
        }

        public void setPhoto(Uri photo){
            Photo=photo;
        }

        public ItemObject(String title, Uri photo) {
        this.title = title;
        Photo=photo;
        }

    }



