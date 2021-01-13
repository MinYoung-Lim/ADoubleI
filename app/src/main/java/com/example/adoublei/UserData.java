package com.example.adoublei;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserData {
    public static String email;
    public static String password;
    public static String name;
    public static String uid;

    public UserData(String email, String name,String password,String uid){
        this.email = email;
        this.name=name;
        this.password=password;
        this.uid=uid;
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
    public String getUid(){ return uid;}
    public void setUid(String uid){ this.uid=uid;}


}
