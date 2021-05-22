package com.rovianda.app.shared.models;

public class ProductReturn {

     private String  lotId;
     private String date;
     private int productId;
     private int presentationId;
     private long units;
     private Float weight;

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

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

    public int getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(int presentationId) {
        this.presentationId = presentationId;
    }

    public long getUnits() {
        return units;
    }

    public void setUnits(long units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Devolution{" +
                "lotId='" + lotId + '\'' +
                ", date='" + date + '\'' +
                ", productId=" + productId +
                ", presentationId=" + presentationId +
                ", units=" + units +
                '}';
    }
}
