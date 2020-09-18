package com.monitoring.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionService {

    public HttpURLConnection openConnection(String path) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
