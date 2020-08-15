package com.rovianda.app.shared.models;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Order {

    private String userId;
    private int noOrder;
    private String vendedor;

    public Order(){

    }

    public Order(String userId, String vendedor, int noOrder){
        this.userId = userId;
        this.vendedor = vendedor;
        this.noOrder = noOrder;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNoOrder() {
        return noOrder;
    }

    public void setNoOrder(int noOrder) {
        this.noOrder = noOrder;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", noOrder=" + noOrder +
                ", vendedor='" + vendedor + '\'' +
                '}';
    }
}
