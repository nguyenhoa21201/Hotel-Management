package com.manager.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSource {
    private static String DB_URL = "jdbc:mysql://localhost:3306/managerhotel";
    private static String USER_NAME = "root";
    private static String PASSWORD = "1234";

    @SneakyThrows
    public static Connection getConnection() throws SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
        System.out.println("connect database success");
        return conn;
    }
    public Connection getDatasource() throws SQLException {
       return getConnection();
    }
}
