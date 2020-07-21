package com.rovianda.app.shared.models;

public class Unity {

    public Unity(boolean value, String tag){
        this.value= value;
        this.tag = tag;

    }

    boolean value;
    String tag;

    public boolean isValue() {
        return value;
    }

    public String getTag() {
        return tag;
    }
}
