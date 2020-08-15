package com.rovianda.app.shared.models;

public class Reprocessing {

    private String date;
    private int productId;
    private String lotId;
    private double weight;
    private String allergen;
    private String area;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Reprocessing{" +
                "date='" + date + '\'' +
                ", productId=" + productId +
                ", lotId='" + lotId + '\'' +
                ", weight=" + weight +
                ", allergen='" + allergen + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
