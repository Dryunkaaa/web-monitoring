package com.monitoring.domain;

import com.monitoring.entity.ResponseStatus;

import java.util.Objects;

public class URL {

    private long id;

    private String path;

    private long monitoringPeriod;

    private long okResponseTime;

    private long warningResponseTime;

    private long criticalResponseTime;

    private int exceptedResponseCode;

    private long minResponseSize;

    private long maxResponseSize;

    private String responseSubstring;

    private ResponseStatus responseStatus;

    private boolean monitoringStatus;

    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean enabledMonitoringStatus() {
        return monitoringStatus;
    }

    public void setMonitoringStatus(boolean monitoringStatus) {
        this.monitoringStatus = monitoringStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseSubstring() {
        return responseSubstring;
    }

    public void setResponseSubstring(String responseSubstring) {
        this.responseSubstring = responseSubstring;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getMonitoringPeriod() {
        return monitoringPeriod;
    }

    public void setMonitoringPeriod(long monitoringPeriod) {
        this.monitoringPeriod = monitoringPeriod;
    }

    public long getOkResponseTime() {
        return okResponseTime;
    }

    public void setOkResponseTime(long okResponseTime) {
        this.okResponseTime = okResponseTime;
    }

    public long getWarningResponseTime() {
        return warningResponseTime;
    }

    public void setWarningResponseTime(long warningResponseTime) {
        this.warningResponseTime = warningResponseTime;
    }

    public long getCriticalResponseTime() {
        return criticalResponseTime;
    }

    public void setCriticalResponseTime(long criticalResponseTime) {
        this.criticalResponseTime = criticalResponseTime;
    }

    public int getExceptedResponseCode() {
        return exceptedResponseCode;
    }

    public void setExceptedResponseCode(int exceptedResponseCode) {
        this.exceptedResponseCode = exceptedResponseCode;
    }

    public long getMinResponseSize() {
        return minResponseSize;
    }

    public void setMinResponseSize(long minResponseSize) {
        this.minResponseSize = minResponseSize;
    }

    public long getMaxResponseSize() {
        return maxResponseSize;
    }

    public void setMaxResponseSize(long maxResponseSize) {
        this.maxResponseSize = maxResponseSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return id == url.id &&
                path.equals(url.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path);
    }

    @Override
    public String toString() {
        return "URL{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", monitoringPeriod=" + monitoringPeriod +
                ", okResponseTime=" + okResponseTime +
                ", warningResponseTime=" + warningResponseTime +
                ", criticalResponseTime=" + criticalResponseTime +
                ", exceptedResponseCode=" + exceptedResponseCode +
                ", minResponseSize=" + minResponseSize +
                ", maxResponseSize=" + maxResponseSize +
                ", responseSubstring='" + responseSubstring + '\'' +
                '}';
    }
}
