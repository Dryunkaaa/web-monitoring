package com.monitoring.service;

import com.monitoring.controller.IndexController;
import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.storage.UrlStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitoringService {

    private static List<URL> monitoringUrls = new ArrayList<>();
    private static Map<URL, Thread> threadMap = new HashMap<>();
    private UrlStatusService urlStatusService;

    public MonitoringService() {
        urlStatusService = new UrlStatusService();
    }

    public void startMonitoring(List<URL> allUrls) {
        for (URL url : allUrls) {
            if (!monitoringUrls.contains(url) && url.getMonitoringStatus()) {
                monitoringUrls.add(url);

                Thread thread = new Thread(() -> {
                    UrlStorage urlStorage = new UrlStorage();

                    while (url.getMonitoringStatus() && !Thread.currentThread().isInterrupted()) {
                        Message message = urlStatusService.getStatus(url);
                        IndexController.messageMap.put(url.getId(), message);

                        if (!url.getResponseStatus().equals(message.getResponseStatus())
                                && !Thread.currentThread().isInterrupted()) {
                            urlStorage.updateResponseStatus(url, message.getResponseStatus());
                        }

                        try {
                            Thread.currentThread().sleep(url.getMonitoringPeriod());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    monitoringUrls.remove(url);
                });

                threadMap.put(url, thread);
                thread.start();
            }
        }
    }

    public Map<URL, Thread> getThreadMap() {
        return threadMap;
    }

    public List<URL> getMonitoringUrls() {
        return monitoringUrls;
    }
}
