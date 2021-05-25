package com.example.myapplication;
import android.app.Application;

public class GlobalClass extends Application {

    private String name = "";
    private String username= "";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameNull(){
        this.name="";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameNull(){
        this.username="";
    }
}
