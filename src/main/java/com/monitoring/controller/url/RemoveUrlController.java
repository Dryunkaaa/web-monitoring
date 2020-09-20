package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.domain.URL;
import com.monitoring.service.MonitoringService;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveUrlController extends Controller {

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("urlId"));
        UrlStorage urlStorage = new UrlStorage();

        MonitoringService monitoringService = new MonitoringService();

        URL url = urlStorage.getById(id);
        monitoringService.getMonitoringUrls().remove(url);
        monitoringService.getThreadMap().get(url).interrupt();
        monitoringService.getThreadMap().get(url).interrupt();
        monitoringService.getThreadMap().remove(url);

        urlStorage.remove(id);

        response.sendRedirect("/");
    }
}
