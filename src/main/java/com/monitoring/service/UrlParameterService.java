package com.monitoring.service;

import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.entity.Response;
import com.monitoring.entity.ResponseStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

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
        }else if(!containsHeaderSubstring(response, url.getResponseSubstring())){
            errorText = "Expected header substring not received";
        }

        if (errorText.isEmpty()) {
            return new Message(ResponseStatus.OK, "All is OK");
        }

        return new Message(ResponseStatus.Critical, errorText);
    }

    public boolean containsHeaderSubstring(Response response, String headerSubstring) {
        if (headerSubstring.isEmpty()) {
            return true;
        }

        return response.containsHeaderSubstring(headerSubstring);
    }

    private Response getResponse(String urlPath) {
        try {
            long startTime = System.currentTimeMillis();
            HttpURLConnection connection = connectionService.openConnection(urlPath);

            long time = System.currentTimeMillis() - startTime;
            long responseSize = connection.getInputStream().available();
            long responseCode = connection.getResponseCode();
            Map<String, List<String>> headerFields = connection.getHeaderFields();

            connection.disconnect();

            return new Response(time, responseSize, responseCode, headerFields);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Response();
    }

}
