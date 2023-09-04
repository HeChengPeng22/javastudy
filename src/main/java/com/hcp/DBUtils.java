package com.hcp;

import java.sql.*;

/**
 * 数据库连接工具
 */
public class DBUtils {

    private final static String URL = "jdbc:mysql://localhost:3306/javaStudy02";

    private final static String USERNAME = "root";

    private final static String PASSWORD = "123456";

    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
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

    public static ResultSet executeQuery(String sql,String... params )throws SQLException{

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i+1,params[i]);
            }

            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public static int executeUpdate(String sql,Object... params){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        try {
            connection = getConnection();
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
