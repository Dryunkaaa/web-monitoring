package com.monitoring.entity;

import java.util.List;
import java.util.Map;

public class Response {

    private long responseTime;
    private long responseSize;
    private long responseCode;
    private Map<String, List<String>> headerFields;

    public Response() {

    }

    public Response(long responseTime, long responseSize, long responseCode, Map<String, List<String>> headerFields) {
        this.responseTime = responseTime;
        this.responseSize = responseSize;
        this.responseCode = responseCode;
        this.headerFields = headerFields;
    }

    public boolean containsHeaderSubstring(String substring){
        return headerFields.containsKey(substring);
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

    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }
}
