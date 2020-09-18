package com.monitoring.controller.url;

import com.monitoring.controller.Controller;
import com.monitoring.domain.URL;
import com.monitoring.service.MonitoringService;
import com.monitoring.storage.UrlStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MonitoringController extends Controller {

    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = Long.parseLong(request.getParameter("id"));
        UrlStorage urlStorage = new UrlStorage();

        URL url = urlStorage.getById(id);
        urlStorage.updateMonitoringStatus(url);

        MonitoringService monitoringService = new MonitoringService();
        List<URL> monitoringUrls = monitoringService.getMonitoringUrls();

        if (monitoringUrls.size() > 0) {
            int urlIndex = monitoringService.getMonitoringUrls().indexOf(url);
            URL monitoringUrl = monitoringService.getMonitoringUrls().get(urlIndex);
            monitoringUrl.setMonitoringStatus(!url.getMonitoringStatus());
        }

        monitoringService.startMonitoring(urlStorage.getAll());

        response.sendRedirect("/");
    }
}
