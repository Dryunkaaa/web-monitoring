package com.monitoring.service;

import com.monitoring.controller.IndexController;
import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
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
                        Message message = urlStatusService.getStatus1(url);
                        IndexController.messageMap.put(url.getId(), message);

                        if (!url.getResponseStatus().equals(message.getResponseStatus())) {
                            urlStorage.updateResponseStatus(url, message.getResponseStatus());
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
