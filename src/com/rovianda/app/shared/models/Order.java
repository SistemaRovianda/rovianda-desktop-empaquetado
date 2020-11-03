package com.rovianda.app.shared.models;

public class Order {

    private int orderId;
    private String date;
    private String userId;
    private String vendedor;
    private boolean status;

    public Order(){}

    public Order(int orderId, String date, String userId, String vendedor, boolean status){
        this.orderId = orderId;
        this.date = date;
        this.userId = userId;
        this.vendedor = vendedor;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
