package com.example.adoublei.masking;

import android.widget.Switch;

public class MaskingItem {

    private String title;
    private Boolean flag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getChecked() {
        return flag;
    }

    public void setChecked(Boolean flag) { this.flag = flag; }


    public MaskingItem() {
        this.title = "이름";
        this.flag = false;
    }
}