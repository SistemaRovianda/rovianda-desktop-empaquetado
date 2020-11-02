package com.rovianda.app.shared.models;

public class PresentationReturn {
    private int id;
    private String presentation;
    private String presentationType;
    private double presentationPricePublic;
    private double presentationPriceMin;
    private double presentationPriceLiquidation;
    private boolean status;
    private String typePrice;
    private String keySae;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getPresentationType() {
        return presentationType;
    }

    public void setPresentationType(String presentationType) {
        this.presentationType = presentationType;
    }

    public double getPresentationPricePublic() {
        return presentationPricePublic;
    }

    public void setPresentationPricePublic(double presentationPricePublic) {
        this.presentationPricePublic = presentationPricePublic;
    }

    public double getPresentationPriceMin() {
        return presentationPriceMin;
    }

    public void setPresentationPriceMin(double presentationPriceMin) {
        this.presentationPriceMin = presentationPriceMin;
    }

    public double getPresentationPriceLiquidation() {
        return presentationPriceLiquidation;
    }

    public void setPresentationPriceLiquidation(double presentationPriceLiquidation) {
        this.presentationPriceLiquidation = presentationPriceLiquidation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(String typePrice) {
        this.typePrice = typePrice;
    }

    public String getKeySae() {
        return keySae;
    }

    public void setKeySae(String keySae) {
        this.keySae = keySae;
    }
}
