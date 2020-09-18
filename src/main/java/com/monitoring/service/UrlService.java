package com.monitoring.service;

import com.monitoring.entity.ResponseStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlService {

    private ConnectionService connectionService;

    public UrlService(){
        connectionService = new ConnectionService();
    }

    public ResponseStatus checkOptions(com.monitoring.domain.URL inputUrl) {

        long startTime = System.currentTimeMillis();
        HttpURLConnection connection = connectionService.openConnection(inputUrl.getPath());
        long time = System.currentTimeMillis() - startTime;

        if (time > inputUrl.getCriticalResponseTime()) {
            return ResponseStatus.Critical;
        } else if (time >= inputUrl.getWarningResponseTime() && time < inputUrl.getCriticalResponseTime()) {
            return ResponseStatus.Warning;
        }

        if (!isInRange(connection, inputUrl) || !equalsResponseCode(connection, inputUrl)
                || containsHeaderSubstring(connection, inputUrl)) {

            return ResponseStatus.Critical;
        }

        if (connection != null) {
            connection.disconnect();
        }

        return ResponseStatus.OK;
    }

    private boolean isInRange(HttpURLConnection connection, com.monitoring.domain.URL inputUrl) {
        try {
            InputStream response = connection.getInputStream();
            int responseSize = response.available();

            if (responseSize < inputUrl.getMinResponseSize() || responseSize > inputUrl.getMaxResponseSize()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean equalsResponseCode(HttpURLConnection connection, com.monitoring.domain.URL inputUrl) {
        try {
            int responseCode = connection.getResponseCode();
            if (inputUrl.getExceptedResponseCode() != responseCode) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean containsHeaderSubstring(HttpURLConnection connection, com.monitoring.domain.URL inputUrl) {
        String server = connection.getHeaderField(inputUrl.getResponseSubstring());

        if (!inputUrl.getResponseSubstring().isEmpty() && server == null) {
            return false;
        }

        return true;
    }
    
}
