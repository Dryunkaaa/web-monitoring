package com.monitoring.service;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;
import com.monitoring.storage.UrlStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonitoringService {

    private Set<URL> monitoringUrls;
    private UrlStatusService urlStatusService;

    public MonitoringService(){
        monitoringUrls = new HashSet<>();
        urlStatusService = new UrlStatusService();
    }

    public void startMonitoring(List<URL> allUrls){
        for (URL url : allUrls) {
            if (!monitoringUrls.contains(url)) {
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

                }).start();
            }
        }
    }
}
