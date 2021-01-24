package com.example.adoublei.masking;

import android.widget.Switch;

public class MaskingItem {

    private String title;
    private boolean option;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getOption() { return option; }

    public void setOption(boolean option){ this.option = true; }

    public MaskingItem() {
        this.title = "이름";
        this.option = true;
    }

    public MaskingItem(String title, boolean option) {
        this.title = title;
        this.option = option;
    }
}
