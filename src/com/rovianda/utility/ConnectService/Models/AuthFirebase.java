package com.rovianda.utility.ConnectService.Models;

public class AuthFirebase {
    String email;
    String password;
    protected boolean returnSecureToken = true;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
