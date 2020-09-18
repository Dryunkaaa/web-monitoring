package com.monitoring.service;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;
import com.monitoring.storage.UrlStorage;

import java.util.ArrayList;
import java.util.List;

public class MonitoringService {

    private static List<URL> monitoringUrls = new ArrayList<>();
    private UrlStatusService urlStatusService;

    public MonitoringService() {
        urlStatusService = new UrlStatusService();
    }

    public void startMonitoring(List<URL> allUrls) {
        for (URL url : allUrls) {
            if (!monitoringUrls.contains(url) && url.getMonitoringStatus()) {
                monitoringUrls.add(url);

                new Thread(() -> {
                    UrlStorage urlStorage = new UrlStorage();

                    while (url.getMonitoringStatus()) {
                        ResponseStatus responseStatus = urlStatusService.getStatus(url);

                        if (!url.getResponseStatus().equals(responseStatus)) {
                            urlStorage.updateResponseStatus(url, responseStatus);
                        }

                        try {
                            Thread.currentThread().sleep(url.getMonitoringPeriod());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    monitoringUrls.remove(url);
                }).start();
            }
        }
    }

    public List<URL> getMonitoringUrls() {
        return monitoringUrls;
    }
}
