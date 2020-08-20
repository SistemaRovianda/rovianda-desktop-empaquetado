package com.rovianda.app.shared.models;

public class ProductsRequest {

    private int product_id;
    private String name;
    private int quantity;

    public ProductsRequest(){

    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductsRequest{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
