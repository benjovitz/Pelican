package com.example.pelican.repository;

import com.example.pelican.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class WishRepository {

    @Autowired
    JdbcTemplate template;

  private ArrayList<User> users;

  public ArrayList<User> userList() {
      ArrayList<User> userList = new ArrayList<>();

      try {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://pelican.mysql.database.azure.com:3306/Pelican",
            "pelifar", "1234Fuckmekanikeren");
        PreparedStatement psts = conn.prepareStatement("SELECT * from user");
        ResultSet resultSet = psts.executeQuery();

        while (resultSet.next()){
          int userID = resultSet.getInt(1);
          String email = resultSet.getString(2);
          String userName = resultSet.getString(3);
          String fName = resultSet.getString(4);
          String lName = resultSet.getString(5);
          String password = resultSet.getString(6);
          //Add shareList!
          ArrayList<Integer> sharedWishLists = new ArrayList<>();
          userList.add(new User(userID, email, userName, fName, lName, password, sharedWishLists));
        }
      } catch (SQLException sqle) {
        System.out.println("Connection to server failed");
        sqle.printStackTrace();
      }
      return userList;
  }

  public WishRepository() {
    users = userList();
  }

  public void addUserToShare(int activeUserID, int shareWithUserID) {
    User shareWithUser = findUserByID(shareWithUserID);
    ArrayList<Integer> newShareList = shareWithUser.getSharedWishlists();
    newShareList.add(activeUserID);
    shareWithUser.setSharedWishlists(newShareList);
    }

  public User findUserByID(int userID) {
    for (User user : users) {
      if (user.getUserID() == userID) {
        return user;
      }
    }
    return null;
  }

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



}
