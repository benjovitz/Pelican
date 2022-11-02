package com.example.pelican.repository;

import com.example.pelican.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class UserRepository {

    private ArrayList<User> users;
    private int currentUser;

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

    public UserRepository(){
        users = userList();
        currentUser = 0;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> userList() {
        ArrayList<User> userList = new ArrayList<>();

        try {
            Connection conn = getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT * from user");
            ResultSet resultSet = psts.executeQuery();

            while (resultSet.next()){
                int userID = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String userName = resultSet.getString(3);
                String fName = resultSet.getString(4);
                String lName = resultSet.getString(5);
                String password = resultSet.getString(6);
                String queryCreate = "SELECT relatedUserID FROM relationtable WHERE userID=?";
                PreparedStatement rpsts = conn.prepareStatement(queryCreate);
                rpsts.setInt(1, userID);
                ResultSet relationResultSet = rpsts.executeQuery();
                ArrayList<Integer> sharedWishLists = new ArrayList<>();

                while (relationResultSet.next()) {
                    sharedWishLists.add(relationResultSet.getInt(1));
                }
                userList.add(new User(userID, email, userName, fName, lName, password, sharedWishLists));
            }

        } catch (SQLException sqle) {
            System.out.println("Connection to server failed");
            sqle.printStackTrace();
        }
        return userList;
    }

    public boolean loginCheck(String username,String password){
        boolean b = true;
        User u = findUserByUserName(username);
        if(u.getPassword().equals(password)){
            currentUser = u.getUserID();
            return b;
        } else{
            b=false;
            return b;
        }
    }
    public void insertUser(String email, String userName, String fName, String lName, String password) {
        try {
            Connection connection = getConnection();
            String sql = "INSERT INTO user (email, userName, fName, lName, password) VALUES(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, fName);
            preparedStatement.setString(4, lName);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteUserById(int deleteID) {
        try {
            Connection connection = getConnection();
            String sql = "DELETE FROM user WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,deleteID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User findUserByID(int userID) {
        for (User user : users) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;
    }

    public User findUserByUserName(String userName) {
        for (User user:users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public User findUserByEmail(String email) {
        for (User user:users) {
            if (user.getUserName().equals(email)) {
                return user;
            }
        }
        return null;
    }


    public void addRelation(User user){
        int userID;
        userID = currentUser;
        int userID2;
        userID2 = user.getUserID();

        try {
            Connection connection = getConnection();
            String sql = "INSERT INTO relationtable VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID2);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addUserToShare(int activeUserID, int shareWithUserID) {
        User shareWithUser = findUserByID(shareWithUserID);
        ArrayList<Integer> newShareList = shareWithUser.getSharedWishlists();
        newShareList.add(activeUserID);
        shareWithUser.setSharedWishlists(newShareList);
    }


    public void deleteRelation(int deleteID){
        try{
            Connection connection = getConnection();
            String sql = "DELETE FROM relationtable WHERE userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,deleteID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
