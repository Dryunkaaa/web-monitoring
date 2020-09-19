package com.monitoring.service;

import com.monitoring.entity.Message;
import com.monitoring.entity.ResponseStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.monitoring.domain.URL;

public class UrlStatusService {

    private ConnectionService connectionService;

    public UrlStatusService() {
        connectionService = new ConnectionService();
    }

    public Message getStatus(URL inputUrl) {
        long startTime = System.currentTimeMillis();
        HttpURLConnection connection = connectionService.openConnection(inputUrl.getPath());
        long time = System.currentTimeMillis() - startTime;

        if (time > inputUrl.getCriticalResponseTime()) {
            return new Message(ResponseStatus.Critical, "Response time is too long");
        } else if (time >= inputUrl.getWarningResponseTime() && time < inputUrl.getCriticalResponseTime()) {
            return new Message(ResponseStatus.Warning, "Response time is too long");
        }

        String errorText = "";

        if (!contentIsInRange(connection, inputUrl)) {
            errorText = "Response size is out of bounds";
        } else if (!equalsResponseCode(connection, inputUrl)) {
            errorText = "Expected code not received";
        } else if (!containsHeaderSubstring(connection, inputUrl)) {
            errorText = "Expected header substring not received";
        }

        if (connection != null) {
            connection.disconnect();
        }

        if (errorText.isEmpty()) {
            return new Message(ResponseStatus.OK, "All is OK");
        }

        return new Message(ResponseStatus.Critical, errorText);
    }

    private boolean contentIsInRange(HttpURLConnection connection, URL inputUrl) {
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

    private boolean equalsResponseCode(HttpURLConnection connection, URL inputUrl) {
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

    private boolean containsHeaderSubstring(HttpURLConnection connection, URL inputUrl) {
        String server = connection.getHeaderField(inputUrl.getResponseSubstring());

        if (!inputUrl.getResponseSubstring().isEmpty() && server == null) {
            return false;
        }

        return true;
    }

}
