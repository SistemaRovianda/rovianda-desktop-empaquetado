package com.rovianda.app.shared.models;

public class PackagingLots {
    private int productId;
    private String loteId;
    private int quantity;
    private int presentationId;
    private String presentation;
    private String typePresentation;
    private Double pricePresentation;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getLoteId() {
        return loteId;
    }

    public void setLoteId(String loteId) {
        this.loteId = loteId;
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

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getTypePresentation() {
        return typePresentation;
    }

    public void setTypePresentation(String typePresentation) {
        this.typePresentation = typePresentation;
    }

    public Double getPricePresentation() {
        return pricePresentation;
    }

    public void setPricePresentation(Double pricePresentation) {
        this.pricePresentation = pricePresentation;
    }
}
