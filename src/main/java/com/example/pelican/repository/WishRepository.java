package com.example.pelican.repository;

import com.example.pelican.model.User;
import com.example.pelican.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


import java.sql.*;

@Repository
public class WishRepository {

    @Autowired
    JdbcTemplate template;

    public void createUser(User user) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
                    "pelifar", "1234Fuckmekanikeren");
            String sql = "INSERT INTO user VALUES(?,?,?,?,?,?)";

            template.update(sql, user.getUserID(), user.getEmail(), user.getUserName(), user.getfName(), user.getlName(), user.getPassword());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserById(int deleteID) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
                    "pelifar", "1234Fuckmekanikeren");
            String sql = "DELETE FROM user WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,deleteID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRelation(User user){
      int userID;
      userID = 12345;
      int userID2;
      userID2 = 32414;

      try {
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
            "pelifar", "1234Fuckmekanikeren");
        String sql = "INSERT INTO relationtable VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        preparedStatement.setInt(2, userID2);
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
    e.printStackTrace();
  }

   public void createWish(Wish wish) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
                    "pelifar", "1234Fuckmekanikeren");
            String sql = "INSERT INTO wishlist VALUES(?,?,?,?)";

            template.update(sql, wish.getUserID, wish.getTitle, wish.getLink, wish.getReserved);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteWishByName(String title) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
                    "pelifar", "1234Fuckmekanikeren");
            String sql = "DELETE FROM wishlist WHERE title=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

    public void deleteRelation(int deleteID){
      try{
          Connection connection = DriverManager.getConnection(
                  "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
                  "pelifar", "1234Fuckmekanikeren");
          String sql = "DELETE FROM relationtable WHERE userID=?";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);

          preparedStatement.setInt(1,deleteID);
          preparedStatement.executeUpdate();

      } catch (SQLException e) {
          e.printStackTrace();
      }
    }

    public List<Wish> viewSharedWishLists(User user) {
      ArrayList<Wish> wishes = new ArrayList<>();
      try {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
            "pelifar", "1234Fuckmekanikeren");
        String queryCreate = "?";
        PreparedStatement psts = conn.prepareStatement(queryCreate);
        ArrayList<Integer> arrayList = new ArrayList<>();
        User user1 = new User(2,"mm","Lasse","Lasse","Dall","1234",arrayList);
        String relationString = String.valueOf(relationString(user1));
        psts.setString(1,relationString);
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
