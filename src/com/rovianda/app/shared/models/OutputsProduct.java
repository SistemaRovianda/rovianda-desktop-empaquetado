package com.rovianda.app.shared.models;

public class OutputsProduct {
     private String loteId;
     private int subOrderId;
     private int quantity;
     private int presentationId;

     public OutputsProduct(String loteId, int subOrderId, int quantity,int presentationId){
         this.loteId =  loteId;
         this.subOrderId = subOrderId;
         this.quantity = quantity;
         this.presentationId  = presentationId;
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

    @Override
    public String toString() {
        return "OutputsProduct{" +
                "loteId='" + loteId + '\'' +
                ", subOrderId=" + subOrderId +
                ", quantity=" + quantity +
                ", presentationId=" + presentationId +
                '}';
    }
}
