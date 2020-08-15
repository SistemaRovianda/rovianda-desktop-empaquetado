package com.rovianda.app.shared.models;

public class Product {
    int presentationId;
    int units;
    Double weight;
    String observations;

    public int getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(int presentationId) {
        this.presentationId = presentationId;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Product{" +
                "presentationId=" + presentationId +
                ", units=" + units +
                ", weight=" + weight +
                ", observations='" + observations + '\'' +
                '}';
    }
}
