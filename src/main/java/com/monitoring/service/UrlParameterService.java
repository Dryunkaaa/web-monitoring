package com.monitoring.service;

import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.entity.Response;
import com.monitoring.entity.ResponseStatus;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class UrlParameterService {

    private ConnectionService connectionService;

    public UrlParameterService() {
        connectionService = new ConnectionService();
    }

    public Message getMessage(URL url) {
        Response response = getResponse(url.getPath());

        if (response.getResponseTime() > url.getCriticalResponseTime()) {
            return new Message(ResponseStatus.Critical, "Response time is too long");
        } else if (response.getResponseTime() >= url.getWarningResponseTime()
                && response.getResponseTime() < url.getCriticalResponseTime()) {
            return new Message(ResponseStatus.Warning, "Response time is too long");
        }

        BoundService boundService = new BoundService();
        String errorText = "";

        if (!boundService.numberIsInRange(response.getResponseSize(), url.getMinResponseSize(), url.getMaxResponseSize())) {
            errorText = "Response size is out of bounds";
        }else if(response.getResponseCode() != url.getExceptedResponseCode()){
            errorText = "Expected code not received";
        }else if(!response.getResponseText().contains(url.getResponseSubstring())){
            errorText = "Response not contains expected substring";
        }

        if (errorText.isEmpty()) {
            return new Message(ResponseStatus.OK, "All is OK");
        }

        return new Message(ResponseStatus.Critical, errorText);
    }

    private Response getResponse(String urlPath) {
        try {
            long startTime = System.currentTimeMillis();
            HttpURLConnection connection = connectionService.openConnection(urlPath);
            InputStream inputStream = connection.getInputStream();

            long time = System.currentTimeMillis() - startTime;
            long responseSize = inputStream.available();
            long responseCode = connection.getResponseCode();

            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            String body = IOUtils.toString(inputStream, encoding);

            connection.disconnect();

            return new Response(time, responseSize, responseCode, body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Response();
    }

}
