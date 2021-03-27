package com.monitoring.entity;

public class Response {

    private long responseTime;
    private long responseSize;
    private long responseCode;
    private String responseText;

    public Response() {
    }

    public Response(long responseTime, long responseSize, long responseCode, String responseText) {
        this.responseTime = responseTime;
        this.responseSize = responseSize;
        this.responseCode = responseCode;
        this.responseText = responseText;
    }


    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    public long getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(long responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
