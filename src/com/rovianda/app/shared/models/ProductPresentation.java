package com.rovianda.app.shared.models;

public class ProductPresentation {
    private int presentationId;
    private int presentation;
    private String typePresentation;

    public int getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(int presentationId) {
        this.presentationId = presentationId;
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
}
