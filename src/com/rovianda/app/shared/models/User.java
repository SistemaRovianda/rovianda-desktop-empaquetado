package com.rovianda.app.shared.models;

public class User {
    private String uuid;
    private String name;
    private String firstSurname;
    private String lastSurname;
    private String email;
    private String rol;
    private String job;

    private static User instance;

    public static void initialInstance(User user){
        if(user!= null)
            instance = user;
    }

    public static User getInstance(){
        return instance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uiid) {
        this.uuid = uiid;
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

    @Override
    public String toString() {
        return "User{" +
                "uiid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", firstSurname='" + firstSurname + '\'' +
                ", lastSurname='" + lastSurname + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
