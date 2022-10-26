package com.example.pelican.model;

import java.util.ArrayList;

public class User {


  private int userID;
  private String email;
  private String userName;
  private String fName;
  private String lName;
  private String password;
  private ArrayList<Integer> sharedWishlists;


  public User(int userID, String email, String userName, String fName, String lName, String password, ArrayList<Integer> sharedWishlists) {
    this.userID = userID;
    this.email = email;
    this.userName = userName;
    this.fName = fName;
    this.lName = lName;
    this.password = password;
    this.sharedWishlists = sharedWishlists;
  }

  public int getUserID() {
    return userID;
  }

  public void setSharedWishlists(ArrayList<Integer> sharedWishlists) {
    this.sharedWishlists = sharedWishlists;
  }

  public ArrayList<Integer> getSharedWishlists() {
    return sharedWishlists;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public String getlName() {
    return lName;
  }

  public void setlName(String lName) {
    this.lName = lName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
        "userID=" + userID +
        ", email='" + email + '\'' +
        ", userName='" + userName + '\'' +
        ", fName='" + fName + '\'' +
        ", lName='" + lName + '\'' +
        ", password='" + password + '\'' +
        ", sharedWishlists=" + sharedWishlists +
        '}';
  }
}
