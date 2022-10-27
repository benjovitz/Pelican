package com.example.pelican.model;

public class Wish {

  private int userID;
  private String title;
  private String link;
  private boolean reserved;

  public Wish(int userID, String title, String link, boolean reserved) {
    this.userID = userID;
    this.title = title;
    this.link = link;
    this.reserved = reserved;
  }
}
