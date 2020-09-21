package com.monitoring.storage;

import java.sql.*;

public class Storage {

//    protected static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    protected static final String DB_DRIVER = "org.postgresql.Driver";
    protected static final String SERVER_PATH = "localhost:3306";
    protected static final String DB_NAME = "webmonitoring";
    protected static final String DB_LOGIN = "root";
    protected static final String DB_PASSWORD = "thequemal7";
    protected Connection connection;
    protected Statement statement;

    public Storage(){
        initDriver();
        initConnection();
    }

    protected void closeResultSet(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initDriver() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private void initConnection() {
//        String url = "jdbc:mysql://" + SERVER_PATH + "/" + DB_NAME;
//        url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//        try {
//            connection = DriverManager.getConnection(url, DB_LOGIN, DB_PASSWORD);
//            statement = connection.createStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    private void initConnection() {
        String url = "jdbc:postgresql://" + "ec2-54-247-71-245.eu-west-1.compute.amazonaws.com:5432/deqfseqdab4btv";
        url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(url, "nctyahnhqwmzxs", "9dff8915dfc180dd6142ebc100b629d36e0b2237e799c61cda3dc6ce4b0cc9be");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
