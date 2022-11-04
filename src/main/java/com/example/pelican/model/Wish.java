package com.example.pelican.model;

public class Wish {

  private int userID;
  private String title;
  private String link;
  private boolean reserved;
  private int reservedBy;

  public Wish(int userID, String title, String link, boolean reserved, int reservedByUser) {
    this.userID = userID;
    this.title = title;
    this.link = link;
    this.reserved = reserved;
    this.reservedBy = reservedBy;
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

  public int getReservedBy() {
    return reservedBy;
  }

  public String reservedString() {
    if (reserved) {
      return "Afreservér";
    } else {
      return "Reservér";
    }
  }

  @Override
  public String toString() {
    return title + ": " + link;
  }
}
