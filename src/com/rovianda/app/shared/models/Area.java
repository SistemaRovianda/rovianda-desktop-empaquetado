package com.rovianda.app.shared.models;

public class Area {
    String areaTag;
    String area;

    public Area(String  area, String areaTag){
        this.area= area;
        this.areaTag = areaTag;
    }

    public String getAreaTag() {
        return areaTag;
    }

    public void setAreaTag(String areaTag) {
        this.areaTag = areaTag;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
