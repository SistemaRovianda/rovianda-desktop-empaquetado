package com.rovianda.app.shared.models;

import java.util.List;

public class ProductPackaging {
    String registerDate;
    int productId;
    String lotId;
    String expiration;
    List<Product> products;

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductPackaging{" +
                "registerDate='" + registerDate + '\'' +
                ", productId=" + productId +
                ", lotId='" + lotId + '\'' +
                ", expiration='" + expiration + '\'' +
                ", products= " + products +
                "}";
    }
}
