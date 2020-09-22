package com.monitoring.storage;

import java.sql.*;
import java.util.List;

public abstract class Storage {

    protected static final String DB_DRIVER = "org.postgresql.Driver";
    protected static final String SERVER_PATH = "ec2-54-247-71-245.eu-west-1.compute.amazonaws.com:5432";
    protected static final String DB_NAME = "deqfseqdab4btv";
    protected static final String DB_LOGIN = "nctyahnhqwmzxs";
    protected static final String DB_PASSWORD = "9dff8915dfc180dd6142ebc100b629d36e0b2237e799c61cda3dc6ce4b0cc9be";
    protected static Connection connection;
    protected Statement statement;

    public Storage() {
        if (connection == null) {
            initConnection();
        } else {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        initDriver();
    }

    public abstract void create(Object object);

    public abstract void remove(long id);

    public abstract void update(Object object);

    protected void closeResultSet(ResultSet rs) {
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

    private void initConnection() {
        String url = "jdbc:postgresql://" + SERVER_PATH + "/" + DB_NAME;
        url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(url, DB_LOGIN, DB_PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
