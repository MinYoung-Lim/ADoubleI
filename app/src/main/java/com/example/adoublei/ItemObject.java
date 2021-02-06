package com.example.adoublei;

import android.net.Uri;

public class ItemObject {

        private String title;
        private String Photo;
        private String key;

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

        public ItemObject(String key, String title, String photo) {
            this.key=key;
            this.title = title;
            this.Photo=photo;
        }
        public ItemObject() {}

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
}



