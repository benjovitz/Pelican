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
}
