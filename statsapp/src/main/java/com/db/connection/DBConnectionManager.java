package com.db.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

    public Connection getConnection(String url, String userName, String password)
    {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return null;
        }
        
        System.out.println("MySQL JDBC Driver Registered!");
        
        try {
            con = DriverManager
                    .getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return con;
        
    }   
}
