package com.monitoring.service;

import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.storage.UrlDatabaseRepository;
import com.monitoring.storage.UrlRepository;

import java.util.List;
import java.util.Map;

public class MonitoringService {

    private UrlParameterService urlParameterService;

    private List<URL> monitoringUrls;

    private Map<Long, Message> messageMap;

    public MonitoringService(List<URL> monitoringUrls, Map<Long, Message> messageMap) {
        this.urlParameterService = new UrlParameterService();
        this.monitoringUrls = monitoringUrls;
        this.messageMap = messageMap;
    }

    public void startMonitoring(List<URL> allUrls) {
        for (URL url : allUrls) {
            if (!monitoringUrls.contains(url) && url.enabledMonitoringStatus()) {
                monitoringUrls.add(url);

                Thread thread = new Thread(getMonitoringTask(url));
                url.setThread(thread);

                thread.start();
            }
        }
    }

    private Runnable getMonitoringTask(URL url) {
        return () -> {
            UrlRepository urlRepository = new UrlDatabaseRepository();

            while (url.enabledMonitoringStatus() && !Thread.currentThread().isInterrupted()) {
                Message message = urlParameterService.getMessage(url);
                messageMap.put(url.getId(), message);

                if (!url.getResponseStatus().equals(message.getResponseStatus())
                        && !Thread.currentThread().isInterrupted()) {
                    urlRepository.updateResponseStatus(url, message.getResponseStatus());
                }

                try {
                    Thread.sleep(url.getMonitoringPeriod());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            monitoringUrls.remove(url);
        };
    }
}
