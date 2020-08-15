package com.rovianda.app.shared.models;

public class UserId {
     String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserId{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
