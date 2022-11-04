package com.example.pelican.model;

import java.util.ArrayList;

public class Wishlist {

  private String userName;
  private ArrayList<Wish> wishes;

  public Wishlist(String userName, ArrayList<Wish> wishes) {
    this.userName = userName;
    this.wishes = wishes;
  }

  public Wishlist(){}

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public ArrayList<Wish> getWishes() {
    return wishes;
  }

  public void setWishes(ArrayList<Wish> wishes) {
    this.wishes = wishes;
  }

  @Override
  public String toString() {
    return userName + " " + wishes;
  }
}
