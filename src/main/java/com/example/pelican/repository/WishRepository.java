package com.example.pelican.repository;

import com.example.pelican.model.User;
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





  public WishRepository() {
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
