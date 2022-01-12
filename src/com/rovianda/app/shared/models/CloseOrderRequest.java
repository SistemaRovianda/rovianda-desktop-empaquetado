package com.rovianda.app.shared.models;

public class CloseOrderRequest {

    private String dateOrderAtemp;
    private int orderId;

    public String getDateOrderAtemp() {
        return dateOrderAtemp;
    }

    public void setDateOrderAtemp(String dateOrderAtemp) {
        this.dateOrderAtemp = dateOrderAtemp;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
