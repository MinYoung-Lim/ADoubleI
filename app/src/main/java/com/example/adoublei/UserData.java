package com.example.adoublei;

public class UserData {
    public static String email;
    public static String password;
    public static String name;


    public UserData(String email, String name,String password){
        this.email = email;
        this.name=name;
        this.password=password;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
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
