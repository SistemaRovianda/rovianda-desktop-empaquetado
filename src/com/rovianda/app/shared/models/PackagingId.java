package com.rovianda.app.shared.models;

public class PackagingId {

    int packagingId;

    public int getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(int packagingId) {
        this.packagingId = packagingId;
    }

    @Override
    public String toString() {
        return "PackagingId{" +
                "packagingId=" + packagingId +
                '}';
    }
}
