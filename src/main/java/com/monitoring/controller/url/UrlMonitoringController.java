package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.domain.URL;
import com.monitoring.entity.ResponseStatus;
import com.monitoring.service.UrlService;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlMonitoringController extends Controller {

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("id"));
        UrlStorage urlStorage = new UrlStorage();

        URL url = urlStorage.getById(id);
        urlStorage.updateMonitoringStatus(url);
        url.setMonitoringStatus(!url.getMonitoringStatus());

        if (url.getMonitoringStatus()){

            UrlService urlService = new UrlService();

            new Thread(() -> {

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

            }).start();
        }

        response.sendRedirect("/");
    }
}
