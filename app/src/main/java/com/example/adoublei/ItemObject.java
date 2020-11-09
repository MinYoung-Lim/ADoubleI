package com.example.adoublei;

import android.net.Uri;

public class ItemObject {



        private String title;
        private Uri photo;

        public ItemObject(String title, Uri photo) {
            this.title = title;
            this.photo = photo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Uri getPhoto() {
            return photo;
        }

        public void setPhoto(Uri photo) {
            this.photo = photo;
        }
    }


