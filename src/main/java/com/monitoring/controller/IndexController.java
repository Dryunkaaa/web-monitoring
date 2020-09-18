package com.monitoring.controller;

import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;
import com.monitoring.service.UrlService;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexController extends Controller {

    private Map<Long, Boolean> statusMap = new HashMap<>();

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setIntHeader("Refresh",5);
        List<URL> urls = new UrlStorage().getAll();

        UrlService urlService = new UrlService();
        request.setAttribute("urls", urls);

        for (URL url : urls) {
            if (!statusMap.containsKey(url.getId())) {
                statusMap.put(url.getId(), true);

                Thread thread = new Thread(() -> {
                    UrlStorage urlStorage = new UrlStorage();

                    while (url.getMonitoringStatus()) {
                        ResponseStatus responseStatus = urlService.checkOptions(url);

                        if (!url.getResponseStatus().equals(responseStatus)) {
                            urlStorage.updateResponseStatus(url, responseStatus);
                        }

                        try {
                            Thread.currentThread().sleep(url.getMonitoringPeriod());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                });

                thread.start();
            }
        }

        request.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
    }

    public Map<Long, Boolean> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Long, Boolean> statusMap) {
        this.statusMap = statusMap;
    }
}
