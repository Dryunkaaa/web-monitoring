package com.monitoring.storage;

import java.sql.*;

public class Storage {

    protected static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
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

    public void closeResultSet(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void initDriver() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void initConnection() {
        String url = "jdbc:mysql://" + SERVER_PATH + "/" + DB_NAME;
        url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(url, DB_LOGIN, DB_PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
