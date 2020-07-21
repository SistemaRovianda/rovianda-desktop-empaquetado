package com.rovianda.app.shared.models;

public class User {
    private String uiid;
    private String name;
    private String firstSurname;
    private String lastSurname;
    private String email;
    private String rol;
    private String job;

    private static User instance;

    public static void initialInstance(User user){
            instance = user;
    }

    public static User getInstance(){
        return instance;
    }

    public String getUiid() {
        return uiid;
    }

    public void setUiid(String uiid) {
        this.uiid = uiid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getLastSurname() {
        return lastSurname;
    }

    public void setLastSurname(String lastSurname) {
        this.lastSurname = lastSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getFullName(){
        return this.name+" "+this.firstSurname+" "+this.lastSurname;
    }
}
