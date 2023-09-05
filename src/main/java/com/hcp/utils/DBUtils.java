package com.hcp.utils;


import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库连接工具
 */
public class DBUtils {
//    private final static String URL = "jdbc:mysql://192.168.2.229:3306/javaStudy02?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false";
//
//    private final static String USERNAME = "root";
//
//    private final static String PASSWORD = "123456";

    static Connection connection;
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;


    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    static {
        Properties pp = new Properties();
        try (InputStream input =DBUtils.class.getClassLoader()
                .getResourceAsStream("db.properties");){
            pp.load(input);
            Class.forName(pp.getProperty("db.driver"));
            connection = DriverManager.getConnection(pp.getProperty("db.url"),pp.getProperty("db.username"),pp.getProperty("db.password"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 释放资源/关闭连接
     */
    public static void closeConnection(){
        try{
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String sql,Object... params )throws SQLException{

        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }

            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public static int executeUpdate(String sql,Object... params){
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1,params[i]);
            }
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }


}
