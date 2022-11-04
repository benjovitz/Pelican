package com.example.pelican.repository;

import com.example.pelican.model.User;
import com.example.pelican.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


import java.sql.*;
import java.util.List;

@Repository
public class WishRepository {

    @Autowired
    JdbcTemplate template;

  public Connection getConnection() {
    try {
      Connection conn = DriverManager.getConnection(
          "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
          "pelifar", "1234Atlantisfindesfaktisk");
      return conn;
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    return null;
  }

    public void insertWish(int userID,String title,String link){
      try{
          Connection connection = getConnection();
          String sql = "INSERT INTO wishlist VALUES (?,?,?,DEFAULT)";

          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1,userID);
          preparedStatement.setString(2,title);
          preparedStatement.setString(3,link);

          preparedStatement.executeUpdate();
      } catch (SQLException e){
          throw new RuntimeException(e);
      }
    }


      public void deleteWishByTitleAndUserID(String title, int userID) {
        try {
            Connection connection = getConnection();
            String sql = "DELETE FROM wishlist WHERE title=? AND userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,title);
            preparedStatement.setInt(2,userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

  public void reserveWishByTitleAndUserID(String title, int userID, int reservedBy) {
    try {
      Connection connection = getConnection();
      String sql = "SELECT * FROM wishlist WHERE title=? AND userID=?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, title);
      preparedStatement.setInt(2, userID);

      ResultSet resultSet = preparedStatement.executeQuery();

      int reserved = 0;

      while (resultSet.next()) {
        reserved = resultSet.getInt(4);
      }

      String queryCreate = "UPDATE wishlist SET reserved=?, reservedBy=? WHERE title=? AND userID=?";
      PreparedStatement ps = connection.prepareStatement(queryCreate);

      if (reserved == 1) {
        ps.setBoolean(1, false);
        ps.setInt(2,0);
      } else {
        ps.setBoolean(1, true);
        ps.setInt(2, reservedBy);
      }
      ps.setString(3, title);
      ps.setInt(4, userID);

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

    public ArrayList<Wish> viewSharedWishLists(User user) {
      ArrayList<Wish> wishes = new ArrayList<>();
      try {
        Connection conn = getConnection();
        String queryCreate = String.valueOf(relationString(user));
        PreparedStatement psts = conn.prepareStatement(queryCreate);
        ResultSet resultSet = psts.executeQuery();

        while (resultSet.next()) {
          int userID = resultSet.getInt(1);
          String title = resultSet.getString(2);
          String link = resultSet.getString(3);
          Boolean reserved = resultSet.getBoolean(4);
          int reservedBy = resultSet.getInt(5);
          wishes.add(new Wish(userID, title, link, reserved, reservedBy));
        }
        } catch(SQLException sqle){
          System.out.println("Connection to database failed");
          sqle.printStackTrace();
        }
        return wishes;
      }

  public ArrayList<Wish> viewWishList(User user) {
    ArrayList<Wish> wishes = new ArrayList<>();
    try {
      Connection conn = getConnection();
      String queryCreate = "SELECT * FROM wishlist WHERE userID=?";
      PreparedStatement psts = conn.prepareStatement(queryCreate);
      psts.setInt(1, user.getUserID());
      ResultSet resultSet = psts.executeQuery();

      while (resultSet.next()) {
        int userID = resultSet.getInt(1);
        String title = resultSet.getString(2);
        String link = resultSet.getString(3);
        Boolean reserved = resultSet.getBoolean(4);
        int reservedBy = resultSet.getInt(5);
        wishes.add(new Wish(userID, title, link, reserved, reservedBy));
      }

    } catch (SQLException sqle) {
      System.out.println("Connection to database failed");
      sqle.printStackTrace();
    }
    return wishes;
  }

    public StringBuilder relationString(User user) {
      StringBuilder relationString = new StringBuilder();
      ArrayList<Integer> relationList = user.getSharedWishlists();
      if (relationList.size() == 0) {
        return relationString;
      } else {
        for (int i = 0; i < relationList.size(); i++) {
          if (i == 0) {
            relationString.append("SELECT * FROM wishlist WHERE userID=" + relationList.get(i));
          } else {
            relationString.append(" OR userID=" + relationList.get(i));
          }
        }
      }
      return relationString;
    }
}
