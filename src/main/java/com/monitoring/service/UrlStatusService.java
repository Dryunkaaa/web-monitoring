package com.monitoring.service;

import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.entity.ResponseStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

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

        try {
            InputStream inputStream = connection.getInputStream();

            if (!contentIsInBounds(inputStream, inputUrl)) {
                errorText = "Response size is out of bounds";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!equalsResponseCode(connection, inputUrl.getExceptedResponseCode())) {
            errorText = "Expected code not received";
        } else if (!containsHeaderSubstring(connection, inputUrl.getResponseSubstring())) {
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

    public boolean contentIsInBounds(InputStream contentStream, URL url) {
        try {
            int responseSize = contentStream.available();

            BoundService boundService = new BoundService();

            return boundService.numberIsInRange(responseSize, url.getMinResponseSize(), url.getMaxResponseSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean equalsResponseCode(HttpURLConnection connection, int expectedCode) {
        try {
            int responseCode = connection.getResponseCode();
            return expectedCode == responseCode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean containsHeaderSubstring(HttpURLConnection connection, String headerSubstring) {
        if (headerSubstring.isEmpty()){
            return true;
        }

        String server = connection.getHeaderField(headerSubstring);

        if (server == null) {
            return false;
        }

        return true;
    }

}
