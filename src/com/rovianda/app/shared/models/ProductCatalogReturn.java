package com.rovianda.app.shared.models;

public class ProductCatalogReturn {
    private int id;
    private String name;
    private String imgS3;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImgS3() {
        return imgS3;
    }

    public void setImgS3(String imgS3) {
        this.imgS3 = imgS3;
    }

}
