package com.rovianda.app.shared.models;

public class OutputsProduct {
     private String loteId;
     private int subOrderId;
     private int quantity;
     private int presentationId;
     private double weight;
     private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public OutputsProduct(String loteId, int subOrderId, int quantity, int presentationId, double weight, String productName){
         this.loteId =  loteId;
         this.subOrderId = subOrderId;
         this.quantity = quantity;
         this.presentationId  = presentationId;
         this.weight = weight;
         this.productName=productName;
     }

    public String getLoteId() {
        return loteId;
    }

    public void setLoteId(String loteId) {
        this.loteId = loteId;
    }

    public int getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(int subOrderId) {
        this.subOrderId = subOrderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(int presentationId) {
        this.presentationId = presentationId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "OutputsProduct{" +
                "loteId='" + loteId + '\'' +
                ", subOrderId=" + subOrderId +
                ", quantity=" + quantity +
                ", presentationId=" + presentationId +
                ", weight=" + weight +
                '}';
    }
}
