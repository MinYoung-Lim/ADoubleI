package com.example.adoublei;

public class UserData {
    public static String password;
    public static String name;


    public UserData(String name, String password){
        this.name=name;
        this.password=password;
    }

    public String getUserName(){
        return name;
    }
    public  void setUserName(String name){
        this.name=name;
    }
    public String getPassword(){
        return password;
    }
    public  void setPassword(String password){
        this.password=password;
    }
}
