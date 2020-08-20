package com.rovianda.app.shared.models;

public class OptionOrder {
    private String tag;
    private boolean value;

    public OptionOrder(String tag, boolean value){
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
