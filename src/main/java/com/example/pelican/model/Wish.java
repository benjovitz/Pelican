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

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public boolean isReserved() {
    return reserved;
  }

  public void setReserved(boolean reserved) {
    this.reserved = reserved;
  }

  @Override
  public String toString() {
    return title + ": " + link;
  }

  public String reservedString() {
    if (isReserved()) {
      return "Afreservér";
    } else {
      return "Reservér";
    }
  }
}
