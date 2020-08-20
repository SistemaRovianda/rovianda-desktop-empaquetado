package com.rovianda.app.shared.models;

public class Presentation {
    private int subOrderId;
    private int productId;
    private int units;
    private int presentation;
    private String typePresentation;
    private String pricePresentation;

    public int getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(int subOrderId) {
        this.subOrderId = subOrderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getPresentation() {
        return presentation;
    }

    public void setPresentation(int presentation) {
        this.presentation = presentation;
    }

    public String getTypePresentation() {
        return typePresentation;
    }

    public void setTypePresentation(String typePresentation) {
        this.typePresentation = typePresentation;
    }

    public String getPricePresentation() {
        return pricePresentation;
    }

    public void setPricePresentation(String pricePresentation) {
        this.pricePresentation = pricePresentation;
    }
}
