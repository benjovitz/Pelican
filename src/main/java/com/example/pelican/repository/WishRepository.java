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


      public void deleteWishByName(String title) {
        try {
            Connection connection = getConnection();
            String sql = "DELETE FROM wishlist WHERE title=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Wish> viewSharedWishLists(User user) {
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
          wishes.add(new Wish(userID, title, link, reserved));
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
            relationString.append(" AND userID=" + relationList.get(i));
          }
        }
      }
      return relationString;
    }
}
