package com.monitoring.service;

import com.monitoring.controller.IndexController;
import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.storage.UrlStorage;

import java.util.List;

public class MonitoringService {

    private UrlParameterService urlParameterService;

    public MonitoringService() {
        urlParameterService = new UrlParameterService();
    }

    public void startMonitoring(List<URL> urlList){
        for (URL url : urlList){
            if (!IndexController.monitoringUrls.contains(url) && url.enabledMonitoringStatus()){
                IndexController.monitoringUrls.add(url);

                Thread thread = new Thread(getMonitoringTask(url));
                url.setThread(thread);

                thread.start();
            }
        }
    }

    private Runnable getMonitoringTask(URL url) {
        Runnable runnable = () -> {
            UrlStorage urlStorage = new UrlStorage();

            while (url.enabledMonitoringStatus() && !Thread.currentThread().isInterrupted()) {
                Message message = urlParameterService.getMessage(url);
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

            IndexController.monitoringUrls.remove(url);
        };

        return runnable;
    }

}
