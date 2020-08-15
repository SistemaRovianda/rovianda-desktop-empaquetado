package com.rovianda.app.shared.models;

public class ProductsRequest {

    private String Product;
    private int quantity;

    public ProductsRequest(){

    }

    public ProductsRequest(String Product, int quantity){
        this.Product = Product;
        this.quantity = quantity;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
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
                "Product='" + Product + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
