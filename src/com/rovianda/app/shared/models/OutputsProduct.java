package com.rovianda.app.shared.models;

public class OutputsProduct {
     private String loteId;
     private int subOrderId;
     private int quantity;

     public OutputsProduct(String loteId, int subOrderId, int quantity){
         this.loteId =  loteId;
         this.subOrderId = subOrderId;
         this.quantity = quantity;
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

    @Override
    public String toString() {
        return "OutputsProduct{" +
                "loteId='" + loteId + '\'' +
                ", subOrderId=" + subOrderId +
                ", quantity=" + quantity +
                '}';
    }
}
